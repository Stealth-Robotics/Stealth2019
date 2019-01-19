
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Grabber extends Subsystem
{
    public Solenoid pistons;

    public Grabber()
    {
        pistons = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel);
    }

    @Override
    public void initDefaultCommand()
    {

    }
}