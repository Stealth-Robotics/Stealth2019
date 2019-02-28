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

import frc.robot.Robot;
import frc.robot.util.PIDexecutor;
import frc.robot.util.constants.Constants;

/*
This command is a hybrid baby of these two case studys from Limelight
    - http://docs.limelightvision.io/en/latest/cs_drive_to_goal_2019.html
    - http://docs.limelightvision.io/en/latest/cs_seeking.html

    it uses the update limelight tracking function to convert what the limelight sees into speed and rotation values to pass into the drivebase

    if there isn't a target the robot is told to spin in circles until it finds a target or the command is terminated
*/
//TODO: Make pid things in LimelightVisionExample use PID executor (I would do this but I would like to test some things first)
//TODO: Comeup with a better name for LimelightVisionExample class
public class LimelightVisionExample extends Command 
{
  
    private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightSpeedCommand = 0.0;
    private double m_limelightStrafeCommand = 0.0;
    private double m_LimelightRotationCommand = 0.0;

    private static PIDexecutor SpeedPIDloop;
    private static PIDexecutor StrafePIDloop;
    
    public LimelightVisionExample() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        requires(Robot.driveBase);

        SpeedPIDloop = new PIDexecutor(Constants.SPEEDkP, 0.0, Constants.SPEEDkD, Constants.DESIRED_TARGET_AREA, new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return -NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
            }
        });

        SpeedPIDloop = new PIDexecutor(Constants.STRAFEkP, 0.0, Constants.STRAFEkD, 0.0, new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
            }
        });
        
        //turn the led off on init
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
        //disable userdrive
        Robot.driveBase.EnableUserDrive = false;
        //turn the led on (3) and make sure it is in vision processor mode (0)
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        Update_Limelight_Tracking();

        //if there is a valid target then use the calculated values to drive twords it otherwise turn around aka seek for a valad target
        if (m_LimelightHasValidTarget)
        {
            Robot.driveBase.moveWithoutIMU(m_LimelightSpeedCommand, m_limelightStrafeCommand, m_LimelightRotationCommand);
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
    public void Update_Limelight_Tracking()
    {
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        //double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        //double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        if (tv < 1.0)
        {
            m_LimelightHasValidTarget = false;
            m_LimelightSpeedCommand = 0.0;
            m_LimelightRotationCommand = 0.0;
            return;
        }

        m_LimelightHasValidTarget = true;

        // Start with proportional steering
        double steer_cmd = tx * Constants.STEER_kP;
        m_LimelightRotationCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = SpeedPIDloop.run();

        // don't let the robot drive too fast into the goal
        if (drive_cmd > Constants.MAX_DRIVE)
        {
            drive_cmd = Constants.MAX_DRIVE;
        }
        m_LimelightSpeedCommand = drive_cmd;

        
        double strafe_cmd = StrafePIDloop.run();

        // don't let the robot drive too fast into the goal
        if (strafe_cmd > Constants.MAX_STRAFE)
        {
            strafe_cmd = Constants.MAX_STRAFE;
        }
        m_limelightStrafeCommand = strafe_cmd;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() 
    {
        //set motor power to 0 and reEnable user drive
        Robot.driveBase.moveWithoutIMU(0, 0, 0);
        Robot.driveBase.EnableUserDrive = true;
        //turn the led off (1)
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

        //set the heading of the idle pid to where we are now
        Robot.driveBase.setTargetHeading(Robot.driveBase.getHeading());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted()
    {
        end();
    }
}
