
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class GrabHatch extends Command
{
    boolean state = false;

    public GrabHatch(boolean State)
    {
        requires(Robot.grabber);
        state = State;
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
        Robot.grabber.setHolderState(state);
    }

    @Override
    protected boolean isFinished() 
    {
        return true;
    }

    @Override
    protected void end()
    {
        Scheduler.getInstance().add(new UserDriveTilt());
    }
}