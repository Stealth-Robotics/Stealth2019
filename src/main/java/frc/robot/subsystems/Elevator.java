
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.commands.UserDriveElevator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.*;
import frc.robot.util.constants.Constants;
import frc.robot.util.constants.OIConstants;

/**
 * This subsystem defines the elevator that lifts the Grabber subsystem
 * 
 * <p> It contains a single motor that drives it </p>
 */
public class Elevator extends Subsystem
{

    private static WPI_TalonSRX elevator; // !< The elevator motor controller

    private static PIDexecutor loop; // !< The PID loop executor

    public Elevator()
    {
        elevator = new WPI_TalonSRX(RobotMap.elevator);

        elevator.setInverted(true);

        loop = new PIDexecutor(Constants.ELEVATOR_KP, Constants.ELEVATOR_KI, 0, elevator.getSelectedSensorPosition(0), new DoubleSupplier(){
        
            @Override
            public double getAsDouble() {
                return elevator.getSelectedSensorPosition(0);
            }
        });

        SmartDashboard.putString("Elevator/Status", Status.Good.toString());

        reset();
    }

    @Override
    public void periodic()
    {

        SmartDashboard.putNumber("Elevator/Target", loop.getTarget());
        SmartDashboard.putNumber("Elevator/Position", elevator.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Elevator/MotorPower", elevator.get());
        
    }

    /**
     * Sets the elevator's default command
     */
    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new UserDriveElevator());
    }

    /**
     * Moves elevator using joystick
     * 
     * @param joystick the driving joystick
     */
    public void move(Joystick joystick)
    {
        double joystickY = joystick.getRawAxis(OIConstants.ELEVATOR_JOYSTICK_Y);
        if (Math.abs(joystickY) > 0.2)
        {
            loop.setTarget(loop.getTarget() - joystickY * Constants.ELEVATOR_SPEED_NORMAL);
        }
        else
        {
            loop.setTarget(loop.getTarget());
        }
        
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

    public void reset(){
        elevator.setSelectedSensorPosition(0, 0, 30);
        setTarget(0);
    }
}