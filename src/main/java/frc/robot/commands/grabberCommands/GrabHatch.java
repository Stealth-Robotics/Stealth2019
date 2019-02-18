
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.*;

public class GrabHatch extends Command
{
    StopWatch stopwatch;

    public GrabHatch()
    {
        requires(Robot.grabber);
        stopwatch = new StopWatch(125);
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
        Robot.grabber.setHolderState(true);
    }

    @Override
    protected boolean isFinished() 
    {
        return stopwatch.isExpired();
    }

    @Override
    protected void end()
    {
        Robot.grabber.setHolderState(false);
    }
}