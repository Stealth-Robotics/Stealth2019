
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Lifter extends Subsystem
{
    private TalonSRX armL;
    private TalonSRX armR;
    private TalonSRX extension;
    private TalonSRX wheelL;
    private TalonSRX wheelR;

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
}