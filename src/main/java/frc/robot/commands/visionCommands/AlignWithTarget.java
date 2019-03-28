/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.visionCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.robot.Robot;
import frc.robot.util.PIDexecutor;
import frc.robot.util.constants.Constants;
import frc.robot.commands.drivebaseCommands.UserDrive;

/*
This command is a hybrid baby of these two case studys from Limelight
    - http://docs.limelightvision.io/en/latest/cs_drive_to_goal_2019.html
    - http://docs.limelightvision.io/en/latest/cs_seeking.html

    it uses the update limelight tracking function to convert what the limelight sees into speed and rotation values to pass into the drivebase

    if there isn't a target the robot is told to spin in circles until it finds a target or the command is terminated
*/
public class AlignWithTarget extends Command 
{
  
    private boolean hasValidTarget = false;
    private double targetLinearSpeed = 0.0;
    private double targetStrafe = 0.0;
    private double targetRotation = 0.0;

    private static PIDexecutor SpeedPIDloop;
    private static PIDexecutor StrafePIDloop;
    private static PIDexecutor SteerPIDloop;

    NetworkTable limelight;

    public AlignWithTarget() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        requires(Robot.driveBase);

        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        SpeedPIDloop = new PIDexecutor(Constants.SPEEDkP, 0.0, Constants.SPEEDkD, Constants.DESIRED_TARGET_AREA, new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return limelight.getEntry("ta").getDouble(0);
            }
        });

        StrafePIDloop = new PIDexecutor(Constants.STRAFEkP, 0.0, Constants.STRAFEkD, 0.0, new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return limelight.getEntry("tx").getDouble(0);
            }
        });

        SteerPIDloop = new PIDexecutor(Constants.STEER_kP, Constants.STEER_kI, Constants.STEER_kD, Constants.STEER_TARGET_RATIO, new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() 
            {
                int direction = (limelight.getEntry("ts").getDouble(0) < -45) ? 1 : -1;
                return direction * limelight.getEntry("tx").getDouble(0) / limelight.getEntry("ty").getDouble(0);
            }
        });
        
        //turn the led off on init
        limelight.getEntry("ledMode").setNumber(0);
        //limelight.getEntry("camMode").setNumber(1);
    }

    /**
     * Gets limelight, turns leds on, sets camera mode to vision processing
     */
    @Override
    protected void initialize() 
    {
        NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
        //turn the led on (3) and make sure it is in vision processor mode (0)
        limelight.getEntry("ledMode").setNumber(3);
        limelight.getEntry("camMode").setNumber(0);
    }

    /**
     * Moves robot towards target if it has it, otherwise spins in circle
     */
    @Override
    protected void execute() 
    {
        updateLimelightTracking();

        //if there is a valid target then use the calculated values to drive towords it otherwise turn around dejectedly aka seek for a valid target
        if (hasValidTarget)
        {
            double netSpeed = Math.sqrt(targetLinearSpeed * targetLinearSpeed + targetStrafe * targetStrafe); //finds total speed from moving forward and strafing
            double netDirection = Math.atan(targetLinearSpeed / targetStrafe); //finds net direction from combining driving forward and strafing
            netDirection = (netDirection < 0) ? Math.PI + netDirection : netDirection;
            
            Robot.driveBase.moveWithoutIMU(netSpeed, /*(targetStrafe > 0) ? -Math.PI / 2 : Math.PI / 2*/0, targetRotation);
            //m_Drive.arcadeDrive(m_LimelightSpeedCommand,m_LimelightRotationCommand);
        }
        else 
        {
            Robot.driveBase.moveWithoutIMU(0, 0, -0.3);
        }
    }

    /**
     * This function implements a simple method of generating driving and steering commands
     * based on the tracking data from a limelight camera.
     */
    public void updateLimelightTracking()
    {
        double tv = limelight.getEntry("tv").getDouble(0);
        // double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        //double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        //double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        if (tv < 1.0)
        {
            hasValidTarget = false;
            targetLinearSpeed = 0.0;
            targetRotation = 0.0;
            return;
        }

        hasValidTarget = true;

        // Start with proportional steering
        // double steer_cmd = tx * Constants.STEER_kP;
        // m_LimelightRotationCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = -SpeedPIDloop.run();

        // don't let the robot drive too fast into the goal
        if (drive_cmd > Constants.MAX_DRIVE)
        {
            drive_cmd = Constants.MAX_DRIVE;
        }
        targetLinearSpeed = drive_cmd;

        targetStrafe = StrafePIDloop.run();

        targetRotation = SteerPIDloop.run();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return false;
    }

    /**
     * Stops drive base, turns leds off, fixes heading to current heading
     */
    @Override
    protected void end() 
    {
        //set motor power to 0 and reEnable user drive
        Robot.driveBase.moveWithoutIMU(0, 0, 0);
        //turn the led off (1)
        limelight.getEntry("ledMode").setNumber(1);
        //limelight.getEntry("camMode").setNumber(1);

        //set the heading of the idle pid to where we are now
        Robot.driveBase.setTargetHeading(Robot.driveBase.getHeading());

        Scheduler.getInstance().add(new UserDrive());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted()
    {
        end();
    }
}
