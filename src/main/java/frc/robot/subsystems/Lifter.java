
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Lifter extends Subsystem
{
    private static WPI_TalonSRX armL;
    private static WPI_TalonSRX armR;
    private static WPI_TalonSRX armBack;
    private static WPI_TalonSRX wheel;

    public Lifter()
    {

    }

    public void initDefaultCommand()
    {
        armL = new WPI_TalonSRX(RobotMap.armL);
        armR = new WPI_TalonSRX(RobotMap.armR);
        armBack = new WPI_TalonSRX(RobotMap.armBack);
        wheel = new WPI_TalonSRX(RobotMap.wheel);
    }

    public void runWheels(double speed)
    {
        wheel.set(speed);
    }

    public void armBackSpeed(double speed)
    {
        armBack.set(speed);
    }
}