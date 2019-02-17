
package frc.robot.commands.visionCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.util.PIDexecutor;
import frc.robot.util.constants.Constants;

public class TransAlignWithTarget extends Command
{

    PIDexecutor transAlignment;

    public TransAlignWithTarget()
    {
        requires(Robot.driveBase);

        transAlignment = new PIDexecutor(Constants.TRANS_ALIGN_KP, Constants.TRANS_ALIGN_KI, Constants.TRANS_ALIGN_KD, 0, new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() {
                return SmartDashboard.getNumber("limelight/tx", 0);
            }
        });
    }

    @Override
    protected void execute() 
    {
        double correction = transAlignment.run();
        Robot.driveBase.move(correction, (correction < 0) ? Math.PI / 2 : -Math.PI / 2, 0, true, false);
    }

    @Override
    protected boolean isFinished()
    {
        return SmartDashboard.getNumber("limelight/tx", 0) < Constants.ROT_ALIGN_END;
    }
}