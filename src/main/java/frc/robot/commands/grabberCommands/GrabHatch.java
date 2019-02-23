
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;

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
}