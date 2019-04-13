
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.OIConstants;
import frc.robot.util.constants.Constants;;

/**
 * Allows manual control of wrist
 */
public class UserDriveTilt extends Command
{

    public UserDriveTilt()
    {
        requires(Robot.grabber);
    }

    /**
     * Manually controls wrist motor
     */
    @Override
    protected void execute() 
    {
        super.execute();

        double wristPower = -Robot.oi.mechJoystick.getRawAxis(OIConstants.WRIST_JOYSTICK_Y);

        if (Math.abs(wristPower) > OIConstants.DEADZONE_GRABBER) //if user sending controls to wrist
        {
            if (Robot.grabber.getPrimaryState()) //if hatch grabber extended, retracts it
            {
                Robot.grabber.togglePrimaryState();
            }

            Robot.grabber.setWristSpeed(wristPower * Constants.WRIST_SPEED); //sends power to wrist
        }
        else //if not being commanded, stop wrist
        {
            Robot.grabber.setWristSpeed(0);
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }
}