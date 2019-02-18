
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves lifter commands to certain level to prepare to drive onto hab
 */
public class LiftToLevel extends Command
{
    private int level;

    public LiftToLevel(int level)
    {
        this.level = level;
    }

    @Override
    protected void initialize() {
        super.initialize();
        if (level == 2)
        {
            Robot.lifter.setTargets(Constants.FRONT_LEGS_LEVEL_2, Constants.BACK_LEG_LEVEL_2);
        }
        else if (level == 3)
        {
            Robot.lifter.setTargets(Constants.FRONT_LEGS_LEVEL_3, Constants.BACK_LEG_LEVEL_3);
        }
        else if (level == 0)
        {
            Robot.lifter.setTargets(0, 0);
        }
        else if (level == -1)
        {
            Robot.lifter.setFrontLTarget(Constants.FRONT_LEGS_LEVEL_0);
            Robot.lifter.setFrontRTarget(Constants.FRONT_LEGS_LEVEL_0);
        }
        else if (level == -2)
        {
            Robot.lifter.setBackTarget(Constants.BACK_LEG_LEVEL_0);
        }
        else
        {
            cancel();
        }
    }


    @Override
    protected boolean isFinished() {
        if (level == 2)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_2) < 5 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_2) < 5 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_2) < 5;
        }
        else if (level == 3)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_3) < 5 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_3) < 5 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_3) < 5;
        }
        else if (level == 0)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_0) < 5 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_0) < 5 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_0) < 5;
        }
        else
        {
            return true;
        }
    }

    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}