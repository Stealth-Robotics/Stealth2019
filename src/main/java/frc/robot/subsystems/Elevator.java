
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Elevator extends Subsystem
{
    private static int targetPos;

    private static WPI_TalonSRX elevator;

    public Elevator()
    {
        elevator = new WPI_TalonSRX(RobotMap.elevator);
    }

    /**
     * Sets the elevator's default command
     */
    @Override
    public void initDefaultCommand()
    {
        //setDefaultCommand(command);
    }

    /**
     * Sets the target encoder postiton for the elevator
     * 
     * @param target the target position
     */
    public void setTarget(int target)
    {
        targetPos = target;
    }

    /**
     * Gets the error between the current position and the target postion
     */
    public int getError()
    {
        return targetPos - elevator.getSelectedSensorPosition(0);
    }

    /**
     * Directly sets the elevator motor's speed
     */
    public void setSpeed(double speed)
    {
        elevator.set(speed);
    }
}