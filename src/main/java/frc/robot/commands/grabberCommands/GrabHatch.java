
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

/**
 * Controls state of hatch grabber, with true being extended and false being retracted
 */
public class GrabHatch extends Command
{
    boolean state = false;

    public GrabHatch(boolean State)
    {
        requires(Robot.grabber);
        state = State;
    }

    /**
     * Sets state of hatch piston
     */
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