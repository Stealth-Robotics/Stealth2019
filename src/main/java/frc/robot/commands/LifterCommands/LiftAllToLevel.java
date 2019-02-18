
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.StopWatch;
import frc.robot.util.constants.Constants;

/**
 * Moves lifter commands to certain level to prepare to drive onto hab
 */
public class LiftAllToLevel extends Command
{
    private int frontTarget;
    private int backTarget;

    private StopWatch stopWatch;

    public LiftAllToLevel(int level)
    {
        if (level == 0)
        {
            frontTarget = Constants.FRONT_LEGS_LEVEL_0;
            backTarget = Constants.BACK_LEG_LEVEL_0;
        }
        else if (level == 2)
        {
            frontTarget = Constants.FRONT_LEGS_LEVEL_2;
            backTarget = Constants.BACK_LEG_LEVEL_2;
        }
        else if (level == 3)
        {
            frontTarget = Constants.FRONT_LEGS_LEVEL_3;
            backTarget = Constants.BACK_LEG_LEVEL_3;
        }
        else
        {
            cancel();
            frontTarget = Constants.FRONT_LEGS_LEVEL_0;
            backTarget = Constants.BACK_LEG_LEVEL_0;
        }

        stopWatch = new StopWatch(500);
    }

    @Override
    protected void initialize() 
    {
        super.initialize();

        Robot.lifter.setTargets(frontTarget, Constants.BACK_LEG_LEVEL_0);
    }

    @Override
    protected void execute() 
    {
        super.execute();
        if (stopWatch.isExpired())
        {
            Robot.lifter.setBackTarget(backTarget);
        }
    }


    @Override
    protected boolean isFinished() 
    {
        return Math.abs(Robot.lifter.getFrontLPosition() - frontTarget) < 100 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - frontTarget) < 100 &&
                    Math.abs(Robot.lifter.getBackPosition() - backTarget) < 100;
    }

    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}