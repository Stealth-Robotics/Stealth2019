package frc.robot.commands.elevatorCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves front legs only to certain level
 */
public class ElevatorToLevel extends Command
{
    private int level;

    private int target;

    private boolean isFinished;

    /**
     * Lifts back legs to level
     * 
     * @param level the level to move to
     */
    public ElevatorToLevel(int Level)
    {
        level = Level;
        target = 0;
        isFinished = false;
    }

    /**
     * Sets the back target
     */
    @Override
    protected void initialize() 
    {
        super.initialize();

        if (Robot.oi.elevatorLevelBallModifyer.get()) {
            if (level == 1)
            {
                target = Constants.ELEVATOR_LEVEL1_BALL;
            }
            else if (level == 2)
            {
                target = Constants.ELEVATOR_LEVEL2_BALL;
            }
            else if (level == 3)
            {
                target = Constants.ELEVATOR_LEVEL3_BALL;
            } else {
                isFinished = true;
                return;
            }
        } else {
            if (level == 1)
            {
                target = Constants.ELEVATOR_LEVEL1_HATCH;
            }
            else if (level == 2)
            {
                target = Constants.ELEVATOR_LEVEL2_HATCH;
            }
            else if (level == 3)
            {
                target = Constants.ELEVATOR_LEVEL3_HATCH;
            } else {
                isFinished = true;
                return;
            }
        }

        Robot.elevator.setTarget(target);
    }

    /**
     * Finishes when the elevator is in position
     */
    @Override
    protected boolean isFinished() 
    {
        return (Math.abs(Robot.elevator.getPosition() - target) < 100) || isFinished;
    }

    /**
     * If interrupted, sets all legs to current position
     */
    @Override
    protected void interrupted()
    {
        Robot.elevator.setTarget(Robot.elevator.getPosition());
    }
}