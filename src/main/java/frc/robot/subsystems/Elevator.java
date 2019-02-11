
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.*;

/**
 * This subsystem defines the elevator that lifts the Grabber subsystem
 * 
 * <p> It contains a single motor that drives it </p>
 */
public class Elevator extends Subsystem
{

    private static WPI_TalonSRX elevator; // !< The elevator motor controller

    private PIDexecutor loop; // !< The PID loop executor

    public Elevator()
    {
        elevator = new WPI_TalonSRX(RobotMap.elevator);

        loop = new PIDexecutor(Constants.EKP, Constants.EKI, 0, elevator.getSelectedSensorPosition(0), new DoubleSupplier(){
        
            @Override
            public double getAsDouble() {
                return elevator.getSelectedSensorPosition(0);
            }
        });

        SmartDashboard.putString("Elevator/Status", Status.Good.toString());
    }

    @Override
    public void periodic()
    {

    }

    /**
     * Sets the elevator's default command
     */
    @Override
    public void initDefaultCommand()
    {
        //setDefaultCommand(command);
    }

    public void move()
    {
        setSpeed(loop.run());
    }

    /**
     * Sets the target encoder postiton for the elevator
     * 
     * @param target the target position
     */
    public void setTarget(int target)
    {
        loop.setTarget(target);
    }

    /**
     * Directly sets the elevator motor's speed
     * 
     * @param speed the target speed
     */
    public void setSpeed(double speed)
    {
        elevator.set(speed);
    }

    /**
     * Resets the accumulated error for the PID loop
     */
    public void resetAccumError()
    {
        loop.reset();
    }
}