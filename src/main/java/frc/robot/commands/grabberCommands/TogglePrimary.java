
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

/**
 * Controls state of primary hatch grabber piston, with true being extended and false being retracted
 */
public class TogglePrimary extends Command
{
    public TogglePrimary()
    {
        requires(Robot.grabber);
    }

    /**
     * Sets state of hatch piston
     */
    @Override
    protected void initialize() 
    {
        super.initialize();
        Robot.grabber.togglePrimaryState();
    }

    /**
     * End immediately, nothing more needed to do
     * 
     * @return true
     */
    @Override
    protected boolean isFinished() 
    {
        return true;
    }

    /**
     * Reschedules UserDriveTilt
     */
    @Override
    protected void end()
    {
        Scheduler.getInstance().add(new UserDriveTilt());
    }
}