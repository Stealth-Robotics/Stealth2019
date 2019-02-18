
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves lifter commands to certain level to prepare to drive onto hab
 */
public class LiftFrontToLevel extends Command
{
    private int target;

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
        else
        {
            cancel();
            target = Constants.FRONT_LEGS_LEVEL_0;
        }
    }

    @Override
    protected void initialize() 
    {
        super.initialize();

        Robot.lifter.setFrontLTarget(target);
        Robot.lifter.setFrontRTarget(target);
    }


    @Override
    protected boolean isFinished() 
    {
        return Math.abs(Robot.lifter.getFrontLPosition() - target) < 100 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - target) < 100;
    }

    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}