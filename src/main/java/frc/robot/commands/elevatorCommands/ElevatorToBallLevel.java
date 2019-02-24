package frc.robot.commands.elevatorCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.constants.Constants;

/**
 * Moves front legs only to certain level
 */
public class ElevatorToBallLevel extends Command
{
    private int target;

    /**
     * Lifts back legs to level
     * 
     * @param level the level to move to
     */
    public ElevatorToBallLevel(int level)
    {
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
        }
        else
        {
            cancel();
            target = Constants.ELEVATOR_BOTTOM;
        }
    }

    /**
     * Sets the back target
     */
    @Override
    protected void initialize() 
    {
        super.initialize();

        Robot.elevator.setTarget(target);
    }

    /**
     * Finishes when the back leg is in position
     */
    @Override
    protected boolean isFinished() 
    {
        return Math.abs(Robot.elevator.getPosition() - target) < 100;
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