
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * This subsystem defines the lifter for the robot
 * 
 * <p> It is designed to lift the robot onto the hab </p>
 * 
 * <p> It consists of three arms that push downwards, lifting the robot </p>
 * 
 * <p> The rear arm has a wheel on its end, pushing the robot when it is off the ground </p>
 */
public class Lifter extends Subsystem
{
    private static WPI_TalonSRX armL; // !< Left lift arm
    private static WPI_TalonSRX armR; // !< Right lift arm
    private static WPI_TalonSRX armBack; // !< Rear lift arm
    private static WPI_TalonSRX wheel; // !< Wheel mounted on back arm

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

    /**
     * Operates the wheel mounted on the arm
     * 
     * @param speed
     */
    public void runWheel(double speed)
    {
        wheel.set(speed);
    }

    /**
     * Sets the speed of the rear arm motor
     * 
     * @param speed
     */
    public void armBackSpeed(double speed)
    {
        armBack.set(speed);
    }

    /**
     * Sets the speed of the front arm motors
     * 
     * @param speed
     */
    public void armsFrontSpeed(double speed)
    {
        armL.set(speed);
        armR.set(speed);
    }
}