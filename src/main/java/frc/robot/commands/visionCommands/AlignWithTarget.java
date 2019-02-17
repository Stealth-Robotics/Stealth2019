
package frc.robot.commands.visionCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.util.PIDexecutor;
import frc.robot.util.constants.Constants;

public class AlignWithTarget extends Command
{
    PIDexecutor alignment;

    public AlignWithTarget(int target)
    {
        requires(Robot.driveBase);

        SmartDashboard.getEntry("limelight/pipeline").setNumber(target);
        alignment = new PIDexecutor(Constants.FIND_KD, Constants.FIND_KI, Constants.FIND_KD, 0, new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return SmartDashboard.getNumber("limelight/tx", 0);
            }
        });
    }

    @Override
    protected void execute() 
    {
        Robot.driveBase.move(0, 0, alignment.run(), true, true);
    }

    @Override
    protected boolean isFinished()
    {
        return SmartDashboard.getNumber("limelight/tx", 0) < 3;
    }
}