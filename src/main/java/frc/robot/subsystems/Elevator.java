
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
 * <p> It contains a single motor that drives it and a limit switch on the bottom</p>
 */
public class Elevator extends Subsystem
{

    private static WPI_TalonSRX elevator; // !< The elevator motor controller

    private static PIDexecutor loop; // !< The PID loop executor

    private static DigitalInput lowerLimit; // !< The Lower Limit Switch
    private static DigitalInput level1Limit; // !< The Level 1 Magnetic Limit Switch

    public Elevator()
    {
        elevator = new WPI_TalonSRX(RobotMap.elevator);

        elevator.setInverted(true);

        //NORMAL PID LOOP
        loop = new PIDexecutor(Constants.ELEVATOR_KP, Constants.ELEVATOR_KI, Constants.ELEVATOR_KD, elevator.getSelectedSensorPosition(0), new DoubleSupplier(){

             @Override
             public double getAsDouble() 
             {
                 return -elevator.getSelectedSensorPosition(0);
             }
        });

        lowerLimit = new DigitalInput(RobotMap.elevatorLimit);
        level1Limit = new DigitalInput(RobotMap.elevatorLevel1Limit);

        SmartDashboard.putString("Elevator/Status", Status.Good.toString());
        SmartDashboard.putBoolean("Elevator/OverridePID", false);

        reset();

        //TODO: Remove Override PID from Init of elevator
        overridePID();
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Elevator/Target", loop.getTarget());
        SmartDashboard.putNumber("Elevator/Position", elevator.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Elevator/MotorPower", elevator.get());

        double targetSpeed = loop.run();
        setSpeed((targetSpeed < 0 && lowerLimit.get() && !Robot.oi.overrideElevatorLimitSwitch.get()) ? 0 : targetSpeed);

        // if the limit switch for level 1 is hit then rumble the controller
        // if (!level1Limit.get()) //moved to UserDriveElevator
        // {
        //     Robot.oi.mechJoystick.setRumble(RumbleType.kLeftRumble, 0.5);
        //     Robot.oi.mechJoystick.setRumble(RumbleType.kRightRumble, 0.5);
        // }
        // else
        // {
        //     Robot.oi.mechJoystick.setRumble(RumbleType.kLeftRumble, 0);
        //     Robot.oi.mechJoystick.setRumble(RumbleType.kRightRumble, 0);
        // }
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
     * Overrides the default PID executor for the elevator to take input directly from the joystick
     */
    public void overridePID()
    {
        //OVERRIDE PID LOOP
        loop = new PIDexecutor(Constants.ELEVATOR_KP, Constants.ELEVATOR_KI, Constants.ELEVATOR_KD, elevator.getSelectedSensorPosition(0), new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                double joystickY = Robot.oi.mechJoystick.getRawAxis(OIConstants.ELEVATOR_JOYSTICK_Y);

                if (Math.abs(joystickY) < OIConstants.ELEVATOR_JOYSTICK_DEADZONE)
                {
                    joystickY = 0;
                }
                
                return -joystickY;
            }
        }, true);

        SmartDashboard.putBoolean("Elevator/OverridePID", true);
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
    public int getPosition()
    {
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

    /**
     * Returns the lower limit switch state
     * 
     * True if closed
     * False if open
     * 
     * @return the lower limit switch state
     */
    public boolean isLimitClosed()
    {
        return lowerLimit.get();
    }

    /**
     * Returns the limit switch for level 1s state
     * 
     * True if open
     * False if closed
     * 
     * @return the level 1 limit switch switch state
     */
    public boolean isLevel1LimitClosed()
    {
        return level1Limit.get();
    }

    @Override
    public String toString()
    {
        return "" + elevator.get() + "," +
                elevator.getSelectedSensorPosition(0) + "," +
                loop.getTarget();
    }
}