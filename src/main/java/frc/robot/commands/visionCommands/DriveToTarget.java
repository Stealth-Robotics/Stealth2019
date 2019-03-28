
package frc.robot.commands.visionCommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.util.PIDexecutor;
import frc.robot.util.constants.Constants;

public class DriveToTarget extends Command
{
    private PIDexecutor rotAlign;
    private PIDexecutor driveTowards;


    public DriveToTarget()
    {
        requires(Robot.driveBase);

        rotAlign = new PIDexecutor(Constants.ROT_ALIGN_KP, Constants.ROT_ALIGN_KI, Constants.ROT_ALIGN_KD, 0, new DoubleSupplier()
        {
            @Override
            public double getAsDouble()
            {
                return SmartDashboard.getNumber("limelight/tx", 0);
            }
        });

        driveTowards = new PIDexecutor(Constants.DRIVE_TOWARDS_KP, Constants.DRIVE_TOWARDS_KI, Constants.DRIVE_TOWARDS_KD, 0, new DoubleSupplier(){
        
            @Override
            public double getAsDouble() 
            {
                return SmartDashboard.getNumber("limelight/ta", 0);
            }
        });
    }

    @Override
    protected void execute() 
    {
        Robot.driveBase.move(driveTowards.run(), 0, rotAlign.run(), true, false);
    }

    @Override
    protected boolean isFinished()
    {
        return SmartDashboard.getNumber("limelight/ta", 0) > Constants.DRIVE_TOWARDS_END;
    }
}