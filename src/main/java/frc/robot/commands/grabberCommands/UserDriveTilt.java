
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

        int target = Robot.grabber.getTiltTarget();

        double axis = Robot.oi.mechJoystick.getRawAxis(OIConstants.WRIST_JOYSTICK_Y);
        if(Math.abs(axis) > 0.2)
        {
            target += axis * 10;
        }

        Robot.grabber.setTiltPosition(target);
    }

    @Override
    protected boolean isFinished()
    {
        return false;//Math.abs(Robot.grabber.getTiltPosition() - pos) < 50;
    }
}