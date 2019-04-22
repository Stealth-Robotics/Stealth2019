package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves front legs only to certain level
 */
public class LiftBackToLevel extends Command
{
    private int target;

    /**
     * Lifts back legs to level
     * 
     * @param level the level to move to
     */
    public LiftBackToLevel(int level)
    {
        if (level == 0)
        {
            target = Constants.BACK_LEG_LEVEL_0;
        }
        else if (level == 2)
        {
            target = Constants.BACK_LEG_LEVEL_2;
        }
        else if (level == 3)
        {
            target = Constants.BACK_LEG_LEVEL_3;
        }
        else if (level == -1)
        {
            target = Constants.BACK_LEG_LEVEL_GROUND;
        }
        else
        {
            cancel();
            target = Constants.BACK_LEG_LEVEL_0;
        }
    }

    /**
     * Sets the back target
     */
    @Override
    protected void initialize() 
    {
        super.initialize();

        Robot.lifter.setBackTarget(target);
    }

    /**
     * Finishes when the back leg is in position
     */
    @Override
    protected boolean isFinished() 
    {
        return true;//Math.abs(Robot.lifter.getBackPosition() - target) < 200; //true;
    }

    /**
     * If interrupted, sets all legs to current position
     */
    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}