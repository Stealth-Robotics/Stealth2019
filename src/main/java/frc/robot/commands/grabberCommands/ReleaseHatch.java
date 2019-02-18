
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.*;

public class ReleaseHatch extends Command
{
    StopWatch stopWatch;
    StopWatch stopWatch2;

    public ReleaseHatch()
    {
        requires(Robot.grabber);
        stopWatch = new StopWatch(250);
        stopWatch2 = new StopWatch(750);
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
        Robot.grabber.setHolderState(true);
        Robot.grabber.setPusherState(true);
    }

    @Override
    protected void execute() 
    {
        super.execute();
        if (stopWatch.isExpired())
        {
            Robot.grabber.setPusherState(false);
        }
    }

    @Override
    protected boolean isFinished() 
    {
        return stopWatch2.isExpired();
    }

    @Override
    protected void end()
    {
        Robot.grabber.setHolderState(false);
    }


}