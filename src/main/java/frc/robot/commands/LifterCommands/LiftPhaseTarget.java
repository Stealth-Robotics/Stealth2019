
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves lifter commands to certain level to prepare to drive onto hab
 */
public class LiftPhaseTarget extends Command
{
    private int phase;

    public LiftPhaseTarget(int phase)
    {
        this.phase = phase;
    }

    @Override
    protected void initialize() 
    {
        super.initialize();

        if (phase == 2)
        {
            Robot.lifter.setTargets(Constants.FRONT_LEGS_LEVEL_2, Constants.BACK_LEG_LEVEL_2);
        }
        else if (phase == 3)
        {
            Robot.lifter.setTargets(Constants.FRONT_LEGS_LEVEL_3, Constants.BACK_LEG_LEVEL_3);
        }
        else if (phase == 0)
        {
            Robot.lifter.setTargets(0, 0);
        }
        else if (phase == -1)
        {
            Robot.lifter.setFrontLTarget(Constants.FRONT_LEGS_LEVEL_0);
            Robot.lifter.setFrontRTarget(Constants.FRONT_LEGS_LEVEL_0);
        }
        else if (phase == -2)
        {
            Robot.lifter.setBackTarget(Constants.BACK_LEG_LEVEL_0);
        }
        else if (phase == 1)
        {
            Robot.lifter.setBackTarget(Constants.BACK_LEG_LEVEL_2);
        }
        else
        {
            cancel();
        }
    }


    @Override
    protected boolean isFinished() {
        if (phase == 2)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_2) < 100 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_2) < 100 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_2) < 100;
        }
        else if (phase == 3)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_3) < 100 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_3) < 100 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_3) < 100;
        }
        else if (phase == 0)
        {
            return Math.abs(Robot.lifter.getFrontLPosition() - Constants.FRONT_LEGS_LEVEL_0) < 100 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - Constants.FRONT_LEGS_LEVEL_0) < 100 &&
                    Math.abs(Robot.lifter.getBackPosition() - Constants.BACK_LEG_LEVEL_0) < 100;
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