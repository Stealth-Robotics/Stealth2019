/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lifterCommands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Disables PID loop for lifter
 */
public class OverrideLiftPID extends Command 
{
    public OverrideLiftPID() 
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lifter);
    }

    /**
     * Disables PID loop
     */
    @Override
    protected void initialize() 
    {
        Robot.lifter.setPIDEnabled(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return true;
    }
}
