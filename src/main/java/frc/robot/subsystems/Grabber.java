
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Grabber extends Subsystem
{
    //public static Solenoid pistons;
    
    public static WPI_TalonSRX intakeL;
    public static WPI_TalonSRX intakeR;
    public static WPI_TalonSRX tilt;

    public Grabber()
    {
        //pistons = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel);

        intakeL = new WPI_TalonSRX(RobotMap.intakeL);
        intakeR = new WPI_TalonSRX(RobotMap.intakeR);
        tilt = new WPI_TalonSRX(RobotMap.tilt);
    }

    @Override
    public void initDefaultCommand()
    {

    }
}