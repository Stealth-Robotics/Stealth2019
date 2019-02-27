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

/*
This command is a hybrid baby of these two case studys from Limelight
    - http://docs.limelightvision.io/en/latest/cs_drive_to_goal_2019.html
    - http://docs.limelightvision.io/en/latest/cs_seeking.html

    it uses the update limelight tracking function to convert what the limelight sees into speed and rotation values to pass into the drivebase

    if there isn't a target the robot is told to spin in circles until it finds a target or the command is terminated
*/
//TODO: Make pid things in LimelightVisionExample use PID executor (I would do this but I would like to test some things first)
//TODO: Comeup with a better name for LimelightVisionExample class
public class LimelightVisionExample extends Command {
  
  private boolean m_LimelightHasValidTarget = false;
  private double m_LimelightSpeedCommand = 0.0;
  private double m_LimelightRotationCommand = 0.0;

  private static PIDexecutor SpeedPIDloop;
  
  public LimelightVisionExample() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);

    requires(Robot.driveBase);

    // These numbers must be tuned for your Robot!  Be careful!
    final double DRIVE_K = 0.15;                    // how hard to drive fwd toward the target
    final double DESIRED_TARGET_AREA = 10;          // Area of the target when the robot reaches the wall

    SpeedPIDloop = new PIDexecutor(DRIVE_K, 0.0, 0.5, DESIRED_TARGET_AREA, new DoubleSupplier(){
        
      @Override
      public double getAsDouble() {
          return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      }
    }, true);
    
    //turn the led off on init
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //disable userdrive
    Robot.driveBase.EnableUserDrive = false;
    //turn the led on (3) and make sure it is in vision processor mode (0)
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Update_Limelight_Tracking();

    //if there is a valid target then use the calculated values to drive twords it otherwise turn around aka seek for a valad target
    if (m_LimelightHasValidTarget)
    {
      Robot.driveBase.moveWithoutIMU(m_LimelightSpeedCommand, 0, m_LimelightRotationCommand);
      //m_Drive.arcadeDrive(m_LimelightSpeedCommand,m_LimelightRotationCommand);
    } else {
      Robot.driveBase.moveWithoutIMU(0, 0, 0.3);
    }
  }

  /**
   * This function implements a simple method of generating driving and steering commands
   * based on the tracking data from a limelight camera.
   */
  public void Update_Limelight_Tracking()
  {

        final double MAX_DRIVE = 0.3;                   // Simple speed limit so we don't drive too fast
        final double STEER_K = 0.03;                    // how hard to turn toward the target

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
        double steer_cmd = tx * STEER_K;
        m_LimelightRotationCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = SpeedPIDloop.run();

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE)
        {
          drive_cmd = MAX_DRIVE;
        }
        m_LimelightSpeedCommand = drive_cmd;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
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
  protected void interrupted() {
    end();
  }
}
