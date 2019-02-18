
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
        if (Robot.oi.mechJoystick.getPOV() != -1)
            Robot.lifter.setWheelSpeed((Robot.oi.mechJoystick.getPOV() == 0) ? 1 : -1);
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}