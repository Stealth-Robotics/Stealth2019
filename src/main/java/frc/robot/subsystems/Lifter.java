
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * This subsystem defines the lifter for the robot
 * 
 * <p> It is designed to lift the robot onto the hab </p>
 * 
 * <p> It consists of three legs that push downwards, lifting the robot </p>
 * 
 * <p> The rear leg has a wheel on its end, pushing the robot when it is off the ground </p>
 */
public class Lifter extends Subsystem
{
    private static WPI_TalonSRX legL; // !< Left lift leg
    private static WPI_TalonSRX legR; // !< Right lift leg
    private static WPI_TalonSRX legBack; // !< Rear lift leg
    private static WPI_TalonSRX wheel; // !< Wheel mounted on back leg

    private int targetBack; // !< The target position for the back leg
    private int targetFront; // !< The target position for the front leg

    public Lifter()
    {

    }

    public void initDefaultCommand()
    {
        legL = new WPI_TalonSRX(RobotMap.legL);
        legR = new WPI_TalonSRX(RobotMap.legR);
        legBack = new WPI_TalonSRX(RobotMap.legBack);
        wheel = new WPI_TalonSRX(RobotMap.wheel);

        target = legBack.getSelectedSensorPosition(0);
    }
    
    public void setTarget(int target)
    {

    }

    /**
     * Operates the wheel mounted on the leg
     * 
     * @param speed
     */
    public void runWheel(double speed)
    {
        wheel.set(speed);
    }

    /**
     * Sets the speed of the rear leg motor
     * 
     * @param speed
     */
    public void legBackSpeed(double speed)
    {
        legBack.set(speed);
    }

    /**
     * Sets the speed of the front leg motors
     * 
     * @param speed
     */
    public void legsFrontSpeed(double speed)
    {
        legL.set(speed);
        legR.set(speed);
    }
}