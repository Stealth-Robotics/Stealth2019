
package frc.robot.commands.drivebaseCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An autonomous command that just drives forward for 5 seconds
 */
public class AutoDriveForward extends Command
{
    private long startTime;

    public AutoDriveForward() 
    {
        super("AutoDriveForward");
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveBase);
    }
  
    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
        startTime = System.currentTimeMillis();
    }
  
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        Robot.driveBase.move(0.5, 0, 0, true, false);
    }
  
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return startTime + 5000 < System.currentTimeMillis();
    }
  
    // Called once after isFinished returns true
    @Override
    protected void end()
    {
        
    }
  
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        end();
    }
}