
package frc.robot.commands.elevatorCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Allows the user to drive the elevator using a joystick
 */
public class UserDriveElevator extends Command 
{
    public UserDriveElevator() 
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }
  
    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
        
    }
  
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
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
