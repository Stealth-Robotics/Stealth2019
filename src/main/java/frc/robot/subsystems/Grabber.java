
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotMap;
import frc.robot.util.*;
import frc.robot.util.constants.Constants;

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

    private static WPI_TalonSRX intakeL; // !< The talon for the left intake wheel
    private static WPI_TalonSRX intakeR; // !< The talon for the right intake wheel
    private static WPI_TalonSRX tilt; // !< The talon for the tilt motor

    private static PIDexecutor tiltController; // !< The PID loop executor to maintain position for the tilt motor

    public Grabber()
    {
        hatchHolder = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel);
        pusher = new Solenoid(RobotMap.pickerModule, RobotMap.pickerChannel);

        intakeL = new WPI_TalonSRX(RobotMap.intakeL);
        intakeR = new WPI_TalonSRX(RobotMap.intakeR);
        tilt = new WPI_TalonSRX(RobotMap.tilt);

        tiltController = new PIDexecutor(Constants.TILT_KP, Constants.TILT_KI, Constants.TILT_KD, tilt.getSelectedSensorPosition(0), new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() 
            {
                return tilt.getSelectedSensorPosition(0);
            }
        });

        SmartDashboard.putString("Grabber/Status", Status.Good.toString());
    }

    @Override
    public void initDefaultCommand()
    {

    }

    public void runLoop()
    {
        tilt.set(tiltController.run());
    }

    /**
     * Sets the position of the tilt motor
     * 
     * @param position the position, in encoder ticks
     */
    public void setTiltPosition(int position)
    {
        tiltController.setTarget(position);
    }

    /**
     * Allows the intake wheels to be operated
     * 
     * @param speed the speed of the intake wheels
     */
    public void runIntake(double speed)
    {
        intakeL.set(speed);
        intakeR.set(speed);
    }

    /**
     * Gets the position of the tilt motot
     * 
     * @return the tilt position
     */
    public int getTiltPosition()
    {
        return tilt.getSelectedSensorPosition(0);
    }

    public void setHolderState(boolean isOn)
    {
        hatchHolder.set(isOn);
    }

    public void setPusherState(boolean isOn)
    {
        pusher.set(isOn);
    }
}