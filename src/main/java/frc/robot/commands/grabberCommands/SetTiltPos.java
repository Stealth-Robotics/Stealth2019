
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class SetTiltPos extends Command
{

    int pos;

    public SetTiltPos(int pos)
    {
        requires(Robot.grabber);
        this.pos = pos;
    }

    @Override
    protected void initialize() 
    {
        super.initialize();
        //Robot.grabber.setTiltPosition(pos);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        end();
    }

    @Override
    protected void end()
    {
        Scheduler.getInstance().add(new UserDriveTilt());
    }

    @Override
    protected boolean isFinished()
    {
        return true;//Math.abs(Robot.grabber.getTiltPosition() - pos) < 50;
    }
}