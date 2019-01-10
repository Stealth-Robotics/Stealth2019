
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotMap;
import frc.robot.Constants;

import java.lang.Math;

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

    public void move(Joystick joystick)
    {
        double speed = joystick.getMagnitude();
        double direction = joystick.getDirectionRadians();

        move(speed, direction);
    }

    public void move(double speed, double direction)
    {
        double speed1 = Math.sqrt(2) * Math.sin(direction + Math.PI / 4) * speed;
        double speed2 = Math.sqrt(2) * Math.sin(direction - Math.PI / 4) * speed;

        driveLF.set(ControlMode.Velocity, speed1);
        driveLR.set(ControlMode.Velocity, speed2);
        driveRF.set(ControlMode.Velocity, speed1);
        driveRR.set(ControlMode.Velocity, speed2);
    }
}