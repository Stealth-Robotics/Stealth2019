 package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

//Drives the robot onto the hab using the legs
public class DriveWithLegs extends Command
 {
     public DriveWithLegs()
     {

     }

     @Override
     protected void execute() {
        Robot.driveBase.move(Robot.oi.driveJoystick, true, true);

     }

     @Override
     protected boolean isFinished()
     {
         return false;
     }
 }