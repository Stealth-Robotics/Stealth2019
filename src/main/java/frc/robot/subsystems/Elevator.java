
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.elevatorCommands.UserDriveElevator;
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

        //NORMAL PID LOOP
        // loop = new PIDexecutor(Constants.ELEVATOR_KP, Constants.ELEVATOR_KI, Constants.ELEVATOR_KD, elevator.getSelectedSensorPosition(0), new DoubleSupplier(){
        
        //     @Override
        //     public double getAsDouble() 
        //     {
        //         return -elevator.getSelectedSensorPosition(0);
        //     }
        // });

        //OVERRIDE PID LOOP
        loop = new PIDexecutor(Constants.ELEVATOR_KP, Constants.ELEVATOR_KI, Constants.ELEVATOR_KD, elevator.getSelectedSensorPosition(0), new DoubleSupplier(){
        
            @Override
            public double getAsDouble() 
            {
                double joystickY = Robot.oi.mechJoystick.getRawAxis(OIConstants.ELEVATOR_JOYSTICK_Y);

                if (!(Math.abs(joystickY) > OIConstants.ELEVATOR_JOYSTICK_DEADZONE))
                {
                    joystickY = 0;
                }
                
                return joystickY;
            }
        }, true);

        SmartDashboard.putString("Elevator/Status", Status.Good.toString());

        reset();
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Elevator/Target", loop.getTarget());
        SmartDashboard.putNumber("Elevator/Position", elevator.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Elevator/MotorPower", elevator.get());

        setSpeed(loop.run());
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
     * Sets the target encoder postiton for the elevator
     * 
     * @param target the target position
     */
    public void setTarget(int target)
    {
        loop.setTarget(target);
    }

    /**
     * Gets the target encoder postiton for the elevator
     * 
     * @return Returns the target of the PID loop
     */
    public int getTarget()
    {
        return (int)loop.getTarget();
    }

    /**
     * Get the Encoder value for the elevator
     * 
     * @return Returns the Encoder value for the elevator
     */
    public int getPosition(){
        return elevator.getSelectedSensorPosition(0);
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

    /**
     * Resets the encoder to 0 and the target to 0
     */
    public void reset()
    {
        elevator.setSelectedSensorPosition(0, 0, 30);
        setTarget(0);
    }

    @Override
    public String toString()
    {
        return "" + elevator.get() + "," +
                elevator.getSelectedSensorPosition(0) + "," +
                loop.getTarget();
    }
}