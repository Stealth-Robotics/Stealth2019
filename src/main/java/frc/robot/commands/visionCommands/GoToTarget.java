
package frc.robot.commands.visionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GoToTarget extends CommandGroup
{
    public GoToTarget(int target)
    {
        addSequential(new FindTarget(target));
        addSequential(new RotAlignWithTarget());
        addSequential(new DriveToTarget());
        addSequential(new TransAlignWithTarget());
    }
}