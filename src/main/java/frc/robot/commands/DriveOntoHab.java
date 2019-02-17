
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.lifterCommands.*;
import frc.robot.util.constants.Constants;

/**
 * Command to enable drivers with driving onto hab
 */
public class DriveOntoHab extends CommandGroup
{
    public DriveOntoHab(int level)
    {
        requires(Robot.lifter);
        addSequential(new LiftToLevel(level));
        addSequential(new UserDriveWheel());
        Robot.lifter.setFrontLTarget(Constants.FRONT_LEGS_LEVEL_0);
        Robot.lifter.setFrontRTarget(Constants.FRONT_LEGS_LEVEL_0);
        addSequential(new UserDriveWheel());
        Robot.lifter.setBackTarget(Constants.BACK_LEG_LEVEL_0);
    }
}