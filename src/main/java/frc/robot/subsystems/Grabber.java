
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

// import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
// import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.grabberCommands.UserDriveTilt;
import frc.robot.util.*;
import frc.robot.util.constants.Constants;
import frc.robot.util.constants.OIConstants;

/**
 * This class defines the grabber subsystem
 * 
 * <p> It involves two wheels for the intake, and a motor to tilt the intake up and down </p>
 * 
 * <p> It also contains two pnumatic cylinders, controlled by a solenoid
 */
public class Grabber extends Subsystem
{
    private static Solenoid hatchHolder; // !< The solenoid to control the velcro pistons
    private static Solenoid pusher;

    private static WPI_TalonSRX intake; // !< The talon for the intake wheels
    private static WPI_TalonSRX wrist; // !< The talon for the tilt motor

    private static PIDexecutor wristController; // !< The PID loop executor to maintain position for the tilt motor

    public Grabber()
    {
        hatchHolder = new Solenoid(RobotMap.PCM, RobotMap.hatchHolderChanel);
        pusher = new Solenoid(RobotMap.PCM, RobotMap.hatchPusherChanel);

        intake = new WPI_TalonSRX(RobotMap.intake);

        wrist = new WPI_TalonSRX(RobotMap.wrist);

        wrist.setInverted(true);

        //tilt.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 40);
        //tilt.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 40);

        wristController = new PIDexecutor(Constants.TILT_KP, Constants.TILT_KI, Constants.TILT_KD, wrist.getSelectedSensorPosition(0), new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() 
            {
                return wrist.getSelectedSensorPosition(0);
            }
        });

        

        SmartDashboard.putString("Grabber/Status", Status.Good.toString());
    }

    public void init()
    {
        resetEncoders();
        setTiltPosition(0);
    }

    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new UserDriveTilt());
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Grabber/WristTarget", wristController.getTarget());
        SmartDashboard.putNumber("Grabber/WristPosition", getTiltPosition());
        SmartDashboard.putNumber("Grabber/WristPower", wrist.get());
        SmartDashboard.putBoolean("Grabber/BackLimitSwitch", isBackLimitSwitchClosed());
        SmartDashboard.putBoolean("Grabber/FrontLimitSwitch", isFrontLimitSwitchClosed());
    }

    public void run()
    {
        if(isBackLimitSwitchClosed()){
            wristController.setTarget(getTiltPosition() - 20);
        }

        wrist.set(wristController.run());

        

        double triggerValue = Robot.oi.mechJoystick.getRawAxis(OIConstants.RUN_INTAKE_TRIGGER);
        if (triggerValue > OIConstants.TRIGGER_THRESHOLD)
        {
            intake.set(triggerValue);
        }
        else if ((triggerValue = Robot.oi.mechJoystick.getRawAxis(OIConstants.REVERSE_INTAKE_TRIGGER)) > OIConstants.TRIGGER_THRESHOLD)
        {
            intake.set(-triggerValue);
        }
        else
        {
            intake.set(0.3);
        }
    }

    /**
     * Resets all encoders to zero
     */
    public void resetEncoders()
    {
        wrist.setSelectedSensorPosition(0, 0, 30);
    }

    public boolean isBackLimitSwitchClosed()
    {
        return wrist.getSensorCollection().isRevLimitSwitchClosed();
    }

    public boolean isFrontLimitSwitchClosed()
    {
        return wrist.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public int getTiltTarget()
    {
        return (int)wristController.getTarget();
    }

    /**
     * Sets the position of the tilt motor
     * 
     * @param position the position, in encoder ticks
     */
    public void setTiltPosition(int position)
    {
        wristController.setTarget(position);
    }

    /**
     * Allows the intake wheels to be operated
     * 
     * @param speed the speed of the intake wheels
     */
    public void runIntake(double speed)
    {
        intake.set(speed);
    }

    /**
     * Gets the position of the tilt motot
     * 
     * @return the tilt position
     */
    public int getTiltPosition()
    {
        return wrist.getSelectedSensorPosition(0);
    }

    public void setHolderState(boolean isOn)
    {
        hatchHolder.set(isOn);
    }

    public void setPusherState(boolean isOn)
    {
        pusher.set(isOn);
    }

    @Override
    public String toString()
    {
        return "" + wrist.get() + "," +
                wrist.getSelectedSensorPosition(0) + "," + 
                wristController.getTarget() + "," +

                intake.get() + "," +

                pusher.get() + "," +
                hatchHolder.get();
    }
}