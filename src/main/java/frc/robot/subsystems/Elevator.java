
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Elevator extends Subsystem
{
    private static TalonSRX elevator;

    public Elevator()
    {
        elevator = new TalonSRX(RobotMap.elevator);
    }

    @Override
    public void initDefaultCommand()
    {
        //setDefaultCommand(command);
    }
}