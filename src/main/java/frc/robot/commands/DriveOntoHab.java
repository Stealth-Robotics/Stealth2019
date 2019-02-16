
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.Robot;
import frc.robot.commands.lifterCommands.*;
import frc.robot.commands.drivebaseCommands.*;

public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        addSequential(new LiftToLevel(level));
        addSequential(new UserControlFront());
        addSequential(new UserControlBack());
    }
}