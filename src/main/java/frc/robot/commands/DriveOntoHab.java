
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;
import frc.robot.commands.lifterCommands.*;
import frc.robot.commands.drivebaseCommands.*;

public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        requires(Robot.driveBase);
        addSequential(new LiftToLevel(level));
        addParallel(new UserDrive());
        addSequential(new UserControlFront());
    }
}