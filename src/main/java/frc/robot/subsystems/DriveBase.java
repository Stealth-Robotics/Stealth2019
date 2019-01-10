
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotMap;
import frc.robot.Constants;


public class DriveBase extends Subsystem
{

    private TalonSRX driveLF;
    private TalonSRX driveLR;
    private TalonSRX driveRF;
    private TalonSRX driveRR;

    public DriveBase()
    {
        super();

        driveLF = new TalonSRX(RobotMap.driveLF);
        driveLR = new TalonSRX(RobotMap.driveLR);
        driveRF = new TalonSRX(RobotMap.driveRF);
        driveRR = new TalonSRX(RobotMap.driveRR);

        SmartDashboard.putBoolean("DriveBase/Initialized", true);
    }

    public void initDefaultCommand()
    {
        //setDefaultCommand();
    }

    public void drive(Joystick joystick)
    {
        double y = joystick.getRawAxis(Constants.joystick_y);
        double x = joystick.getRawAxis(Constants.joystick_x);

    }
}