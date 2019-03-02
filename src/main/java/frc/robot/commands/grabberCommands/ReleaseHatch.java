
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class ReleaseHatch extends Command
{
    boolean state = false;

    public ReleaseHatch(boolean State)
    {
        requires(Robot.grabber);
        state = State;
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
        Robot.grabber.setPusherState(!state);
    }

    @Override
    protected boolean isFinished() 
    {
        return true;
    }


}