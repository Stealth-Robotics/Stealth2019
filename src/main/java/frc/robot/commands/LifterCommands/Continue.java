
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Moves robot to next stage during the climb
 */
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

    /**
     * User presses button when robot is in position
     */
    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }

    @Override
    protected void end() 
    {
        super.end();
    }
}