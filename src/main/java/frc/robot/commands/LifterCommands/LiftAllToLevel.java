
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.StopWatch;
import frc.robot.util.constants.Constants;

/**
 * Moves all legs to prepare to drive onto hab
 * 
 * The front legs begin movement first, with a 500 ms delay
 */
public class LiftAllToLevel extends Command
{
    private int frontTarget;
    private int backTarget;

    private StopWatch stopWatch;

    /**
     * Creates new command using specific leve
     * 
     * @param level the level to use
     */
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

        stopWatch = new StopWatch(1500);
    }

    /**
     * initializes the front targets
     */
    @Override
    protected void initialize() 
    {
        super.initialize();

        Robot.lifter.setFrontTargets(frontTarget);
    }

    /**
     * if 500 ms have passed, set back target
     */
    @Override
    protected void execute() 
    {
        super.execute();
        if (stopWatch.isExpired())
        {
            Robot.lifter.setBackTarget(backTarget);
        }
    }

    /**
     * command is finished if all legs are in position
     */
    @Override
    protected boolean isFinished() 
    {
        return Math.abs(Robot.lifter.getFrontLPosition() - frontTarget) < 200 &&
                    Math.abs(Robot.lifter.getFrontRPosition() - frontTarget) < 200 &&
                    Math.abs(Robot.lifter.getBackPosition() - backTarget) < 200
                    || Robot.oi.nextStageButton.get();
    }

    /**
     * if interrupted, sets legs to current position
     */
    @Override
    protected void interrupted()
    {
        Robot.lifter.setTargets(Robot.lifter.getFrontLPosition(), Robot.lifter.getFrontRPosition(), Robot.lifter.getBackPosition());
    }
}