
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.constants.Constants;
import frc.robot.util.constants.OIConstants;

public class UserDriveTilt extends Command
{

    public UserDriveTilt()
    {
        requires(Robot.grabber);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        super.execute();

        // int target = Robot.grabber.getTiltTarget();

        // double axis = Robot.oi.mechJoystick.getRawAxis(OIConstants.WRIST_JOYSTICK_Y);
        // if(Math.abs(axis) > OIConstants.DEADZONE_GRABBER)
        // {
        //     target += axis * Constants.TILT_SPEED_NORMAL;
        // }

        // Robot.grabber.setTiltPosition(target);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }
}