
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.constants.Constants;

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
        else
        {
            return true;
        }
    }
}