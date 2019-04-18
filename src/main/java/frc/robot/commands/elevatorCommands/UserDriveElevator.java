
package frc.robot.commands.elevatorCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;
import frc.robot.util.constants.OIConstants;

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
  
    /**
     * Sends joystick to move()
     */
    @Override
    protected void execute() 
    {
        move(Robot.oi.mechJoystick);
    }

    /**
     * Moves elevator using joystick
     * 
     * @param joystick the driving joystick
     */
    public void move(Joystick joystick)
    {
        double joystickY = joystick.getRawAxis(OIConstants.ELEVATOR_JOYSTICK_Y);

        if (Math.abs(joystickY) > OIConstants.ELEVATOR_JOYSTICK_DEADZONE)
        {
            Robot.elevator.setTarget((int)(Robot.elevator.getTarget() + joystickY * Constants.ELEVATOR_SPEED_NORMAL));
        }
        else
        {
            Robot.elevator.setTarget(Robot.elevator.getTarget());
        }

        // if the limit switch for level 1 is hit then rumble the controller
        if (!Robot.elevator.isLevel1LimitClosed())
        {
            Robot.oi.mechJoystick.setRumble(RumbleType.kLeftRumble, 0.5);
            Robot.oi.mechJoystick.setRumble(RumbleType.kRightRumble, 0.5);
        }
        else
        {
            Robot.oi.mechJoystick.setRumble(RumbleType.kLeftRumble, 0);
            Robot.oi.mechJoystick.setRumble(RumbleType.kRightRumble, 0);
        }
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
        Robot.oi.mechJoystick.setRumble(RumbleType.kLeftRumble, 0);
        Robot.oi.mechJoystick.setRumble(RumbleType.kRightRumble, 0);
    }
  
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        end();
    }
}
