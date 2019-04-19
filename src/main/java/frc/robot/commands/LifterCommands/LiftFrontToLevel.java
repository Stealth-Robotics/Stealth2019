
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves back legs only to certain level
 */
public class LiftFrontToLevel extends Command
{
    private int target;

    /**
     * Creates a new command with the specific level to move to
     * 
     * @param level the level to move the legs to
     */
    public LiftFrontToLevel(int level)
    {
        if (level == 0)
        {
            target = Constants.FRONT_LEGS_LEVEL_0;
        }
        else if (level == 2)
        {
            target = Constants.FRONT_LEGS_LEVEL_2;
        }
        else if (level == 3)
        {
            target = Constants.FRONT_LEGS_LEVEL_3;
        }
        else if (level == -1)
        {
            target = Constants.FRONT_LEGS_LEVEL_GROUND;
        }
        else
        {
            cancel();
            target = Constants.FRONT_LEGS_LEVEL_0;
        }
    }

    @Override
    /**
     * Sets the targets for the front legs
     */
    protected void initialize() 
    {
        super.initialize();

        Robot.lifter.setFrontTargets(target);
    }

    /**
     * Finishes when the legs are in position
     */
    @Override
    protected boolean isFinished() 
    {
        return Math.abs(Robot.lifter.getFrontLPosition() - target) < 200 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - target) < 200;
    }

    /**
     * When interrupted, sets all leg positions to where they are currently
     */
    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}