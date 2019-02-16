
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FindTarget extends Command
{
    public FindTarget(int target)
    {
        SmartDashboard.getEntry("limelight/").setNumber(target);
    }

    @Override
    protected boolean isFinished()
    {
        return true;
    }
}