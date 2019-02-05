
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//import edu.wpi.first.wpilibj.Solenoid; TODO uncomment this when ready to use
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.*;

public class Grabber extends Subsystem
{
    //public static Solenoid pistons;
    
    public static WPI_TalonSRX intakeL;
    public static WPI_TalonSRX intakeR;
    public static WPI_TalonSRX tilt;

    public Grabber()
    {
        //pistons = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel); TODO uncomment this when ready to use

        intakeL = new WPI_TalonSRX(RobotMap.intakeL);
        intakeR = new WPI_TalonSRX(RobotMap.intakeR);
        tilt = new WPI_TalonSRX(RobotMap.tilt);

        SmartDashboard.putString("Grabber/Status", Status.Good.toString());
    }

    @Override
    public void initDefaultCommand()
    {

    }
}