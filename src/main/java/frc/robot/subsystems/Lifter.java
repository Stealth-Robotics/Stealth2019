
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Lifter extends Subsystem
{
    private static TalonSRX armL;
    private static TalonSRX armR;
    private static TalonSRX armBack;
    private static TalonSRX wheel;

    public Lifter()
    {

    }

    public void initDefaultCommand()
    {
        armL = new TalonSRX(RobotMap.armL);
        armR = new TalonSRX(RobotMap.armR);
        armBack = new TalonSRX(RobotMap.armBack);
        wheel = new TalonSRX(RobotMap.wheel);
    }

    public void runWheels(double speed)
    {
        wheel.set(ControlMode.Velocity, speed);
    }

    public void armBackSpeed(double speed)
    {
        armBack.set(ControlMode.Velocity, speed);
    }
}