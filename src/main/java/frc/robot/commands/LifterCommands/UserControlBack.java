
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.*;

public class UserControlBack extends Command
{
    public UserControlBack()
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
            Robot.lifter.setBackTarget(Robot.lifter.getBackPosition() - 10);
            Robot.lifter.setBackTarget(Robot.lifter.getBackPosition() - 10);
        }
        else if (Robot.oi.legDownButton.get())
        {
            Robot.lifter.setBackTarget(Robot.lifter.getBackPosition() + 10);
            Robot.lifter.setBackTarget(Robot.lifter.getBackPosition() + 10);
        }
        Robot.lifter.setWheelSpeed(-Robot.oi.driveJoystick.getRawAxis(OIConstants.DRIVE_JOYSTICK_Y));
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}