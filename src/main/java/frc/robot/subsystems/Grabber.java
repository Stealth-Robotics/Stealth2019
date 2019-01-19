
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Grabber extends Subsystem
{
    public static Solenoid pistons;
    
    public static TalonSRX intakeL;
    public static TalonSRX intakeR;
    public static TalonSRX tilt;

    public Grabber()
    {
        pistons = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel);

        intakeL = new TalonSRX(RobotMap.intakeL);
        intakeR = new TalonSRX(RobotMap.intakeR);
        tilt = new TalonSRX(RobotMap.tilt);
    }

    @Override
    public void initDefaultCommand()
    {

    }
}