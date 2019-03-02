
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class Continue extends Command
{
    public Continue()
    {
        requires(Robot.lifter);
    }

    @Override
    protected void execute() 
    {
        
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}