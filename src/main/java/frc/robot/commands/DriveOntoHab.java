
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.commands.lifterCommands.*;

/**
 * Command to enable drivers with driving onto hab
 */
public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        addSequential(new LiftToLevel(level));
        addSequential(new UserControlFront());
        addSequential(new UserControlBack());
    }
}