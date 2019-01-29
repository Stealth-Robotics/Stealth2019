
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.MPPaths.MPPath;

public class ExecuteMPPath extends Command
{
    private MPPath path;
    private int pCount;

    public ExecuteMPPath(MPPath path)
    {
        super();

        this.path = path;
        pCount = 0;

        requires(Robot.driveBase);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize()
    {
        Robot.driveBase.setPIDon(true);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute()
    {

        pCount++;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return pCount == path.kNumPoints;
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