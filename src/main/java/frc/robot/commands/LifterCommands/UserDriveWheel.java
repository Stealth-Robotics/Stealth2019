
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class UserDriveWheel extends Command
{
    public UserDriveWheel()
    {
        requires(Robot.lifter);
    }

    @Override
    protected void execute() 
    {
        int pov = Robot.oi.mechJoystick.getPOV();
        if (pov == 0)
        {
            Robot.lifter.setWheelSpeed(1);
        }
        else if (pov == 180)
        {
            Robot.lifter.setWheelSpeed(-1);
        }
        else if (pov == 90)
        {
            Robot.lifter.setBackTarget(Robot.lifter.getBackTarget() + 10);
        }
        else if (pov == 270)
        {
            Robot.lifter.setBackTarget(Robot.lifter.getBackTarget() - 10);
        }
        else
        {
            Robot.lifter.setWheelSpeed(0);
        }
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}