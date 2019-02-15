
package frc.robot.commands.DrivebaseCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Allows the user to drive the robot using a joystick
 */
public class UserDrive extends Command 
{
    public UserDrive() 
    {
        super("UserDrive");
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveBase);
    }
  
    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
        Robot.driveBase.resetHeadingAccumError();
    }
  
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        Robot.driveBase.move(Robot.oi.driveJoystick, false, true); //withPID, then withHeadless
        Robot.elevator.move(Robot.oi.mechJoystick);
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
        
    }
  
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        end();
    }
}
