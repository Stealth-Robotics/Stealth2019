
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.commands.LifterCommands.*;
import frc.robot.commands.DrivebaseCommands.*;

public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        addSequential(new LiftToLevel(level));
        addParallel(new UserDrive());
    }
}