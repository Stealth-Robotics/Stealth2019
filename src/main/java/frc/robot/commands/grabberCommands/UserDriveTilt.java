
package frc.robot.commands.grabberCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.constants.OIConstants;

public class UserDriveTilt extends Command
{

    public UserDriveTilt()
    {
        requires(Robot.grabber);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        super.execute();

        double wristPower = -Robot.oi.mechJoystick.getRawAxis(OIConstants.WRIST_JOYSTICK_Y);
        Robot.grabber.setWristSpeed((Math.abs(wristPower) < OIConstants.DEADZONE_GRABBER) ? 0 : wristPower);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }
}