
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;


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

        SmartDashboard.putBoolean("DriveBase/Inited", true);
    }

    public void initDefaultCommand()
    {
        //setDefaultCommand();
    }
}