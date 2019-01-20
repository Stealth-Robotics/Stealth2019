
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Elevator extends Subsystem
{
    private static WPI_TalonSRX elevator;

    public Elevator()
    {
        elevator = new WPI_TalonSRX(RobotMap.elevator);
    }

    @Override
    public void initDefaultCommand()
    {
        //setDefaultCommand(command);
    }
}