
package frc.robot.commands.LifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

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
        Robot.lifter.setFrontLTarget(Robot.lifter.getFrontLPosition() + Robot.oi.mechJoystick);
    }
}