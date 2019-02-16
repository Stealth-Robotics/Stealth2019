
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;
import frc.robot.commands.LifterCommands.*;
import frc.robot.commands.DrivebaseCommands.*;

public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        requires(Robot.driveBase);
        addSequential(new LiftToLevel(level));
        addParallel(new UserDrive());
    }
}