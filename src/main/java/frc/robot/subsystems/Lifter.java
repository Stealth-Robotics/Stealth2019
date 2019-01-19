
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Lifter extends Subsystem
{
    private static TalonSRX armL;
    private static TalonSRX armR;
    private static TalonSRX extension;
    private static TalonSRX wheelL;
    private static TalonSRX wheelR;

    public Lifter()
    {

    }

    public void initDefaultCommand()
    {
        armL = new TalonSRX(RobotMap.armL);
        armR = new TalonSRX(RobotMap.armR);
        extension = new TalonSRX(RobotMap.armBack);
        wheelL = new TalonSRX(RobotMap.wheelL);
        wheelR = new TalonSRX(RobotMap.wheelR);
    }

    public void runWheels(double L, double R)
    {
        wheelL.set(ControlMode.Velocity, L);
        wheelR.set(ControlMode.Velocity, R);
    }
}