
package frc.robot.commands.visionCommands;

import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.util.*;
import frc.robot.util.constants.Constants;

public class FindTarget extends Command
{
    int target;

    StopWatch stopwatch;

    public FindTarget(int target)
    {
        requires(Robot.driveBase);

        this.target = target;

        stopwatch = new StopWatch(10000);
    }

    @Override
    protected void initialize()
    {
        SmartDashboard.getEntry("limelight/pipeline").setNumber(target);
    }

    @Override
    protected void execute()
    {
        Robot.driveBase.move(0, 0, Constants.FIND_ROT_SPEED, true, false);
    }

    @Override
    public boolean isFinished()
    {
        return SmartDashboard.getNumber("limelight/tv", 0) == 1 || stopwatch.isExpired();
    }

    @Override
    public void end()
    {
        SmartDashboard.putBoolean("Commands/TargetFound", stopwatch.isExpired());
    }
}