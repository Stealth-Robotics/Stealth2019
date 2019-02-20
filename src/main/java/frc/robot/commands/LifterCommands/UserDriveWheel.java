
package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

public class UserDriveWheel extends Command
{
    public UserDriveWheel()
    {
        requires(Robot.lifter);
    }

    @Override
    protected void execute() 
    {
        if (Robot.oi.wheelForwardButton.get())
        {
            Robot.lifter.setWheelSpeed(1);
        }
        else if (Robot.oi.wheelBackwardButton.get())
        {
            Robot.lifter.setWheelSpeed(-1);
        }
        else
        {
            Robot.lifter.setWheelSpeed(0);
        }
        
        
        /*if (Robot.oi.backLegDown.get())
        {
            Robot.lifter.setBackTarget(Robot.lifter.getBackTarget() + 10);
        }
        else if (Robot.oi.backLegUp.get())
        {
            Robot.lifter.setBackTarget(Robot.lifter.getBackTarget() - 10);
        }*/
        
    }

    @Override
    protected boolean isFinished()
    {
        return Robot.oi.nextStageButton.get();
    }
}