
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.*;

/**
 * Command to control front leg while driving onto hab
 */
public class UserControlFront extends Command
{
    public UserControlFront()
    {
        requires(Robot.lifter);
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
    }

    @Override
    protected void execute()
    {
        if (Robot.oi.legUpButton.get())
        {
            Robot.lifter.setFrontLTarget(Robot.lifter.getFrontLPosition() - 10);
            Robot.lifter.setFrontRTarget(Robot.lifter.getFrontRPosition() - 10);
        }
        else if (Robot.oi.legDownButton.get())
        {
            Robot.lifter.setFrontLTarget(Robot.lifter.getFrontLPosition() + 10);
            Robot.lifter.setFrontRTarget(Robot.lifter.getFrontRPosition() + 10);
        }
        Robot.lifter.setWheelSpeed(-Robot.oi.driveJoystick.getRawAxis(OIConstants.DRIVE_JOYSTICK_Y));
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}