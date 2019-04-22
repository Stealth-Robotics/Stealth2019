
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Command to enable drivers to drive onto hab
 */
public class DriveOntoHab extends CommandGroup
{
    /**
     * Creates a new command with specific level to target
     * 
     * @param level the level to use
     */
    public DriveOntoHab(int level)
    {
        requires(Robot.lifter);
        
        
        addSequential(new LiftFrontToLevel(level));
        addSequential(new LiftBackToLevel(level));
        addSequential(new Continue());
        addSequential(new LiftFrontToLevel(0));
        addSequential(new Continue());
        addSequential(new LiftFrontToLevel(-1));
        //addSequential(new Continue());
        //addSequential(new LiftBackToLevel(0));
    }

    /**
     * Command can be finished with pressing cancel button
     */
    @Override
    protected boolean isFinished() 
    {
        return Robot.oi.cancelClimbButton.get();
    }

    /**
     * When finishing, brings all legs back up
     */
    @Override
    protected void end()
    {
        System.out.println("SETTING LEGS BACK TO 0");
        Robot.lifter.setFrontLTarget(Constants.FRONT_LEGS_LEVEL_0);
        Robot.lifter.setFrontRTarget(Constants.FRONT_LEGS_LEVEL_0);
        Robot.lifter.setBackTarget(Constants.BACK_LEG_LEVEL_0);
    }
}