
package frc.robot.subsystems;

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
import frc.robot.util.constants.OIConstants;

/**
 * This class defines the grabber subsystem
 * 
 * <p> It involves two wheels for the intake, and a motor to tilt the intake up and down </p>
 * 
 * <p> It also contains a pnumatic cylinder, controlled by a solenoid </p>
 */
public class Grabber extends Subsystem
{
    private static Solenoid hatchGrabberPrimary; //The solenoid to control the position of the entire hatch grabber
    private static Solenoid hatchGrabberSecondary; // !< The solenoid to control the hatch grabber piston

    private static WPI_TalonSRX intake; // !< The talon for the intake wheels
    private static WPI_TalonSRX wrist; // !< The talon for the tilt motor

    // private static PIDexecutor wristController; // !< The PID loop executor to maintain position for the tilt motor

    public Grabber()
    {
        hatchGrabberPrimary = new Solenoid(RobotMap.PCM, RobotMap.hatchGrabberPrimaryChannel);
        hatchGrabberSecondary = new Solenoid(RobotMap.PCM, RobotMap.hatchGrabberSecondaryChannel);

        intake = new WPI_TalonSRX(RobotMap.intake);

        wrist = new WPI_TalonSRX(RobotMap.wrist);

        wrist.setInverted(true);

        //tilt.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 40);
        //tilt.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 40);

        //PID WITH ENCODER
        // wristController = new PIDexecutor(Constants.TILT_KP, Constants.TILT_KI, Constants.TILT_KD, wrist.getSelectedSensorPosition(0), new DoubleSupplier()
        // {
        
        //     @Override
        //     public double getAsDouble() 
        //     {
        //         return wrist.getSelectedSensorPosition(0);
        //     }
        // });

        SmartDashboard.putString("Grabber/Status", Status.Good.toString());
    }

    public void init()
    {
        //resetEncoders();
        //setTiltPosition(0);
    }

    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new UserDriveTilt());
    }

    @Override
    public void periodic()
    {
        //SmartDashboard.putNumber("Grabber/WristTarget", wristController.getTarget());
        //SmartDashboard.putNumber("Grabber/WristPosition", getTiltPosition());
        //SmartDashboard.putNumber("Grabber/WristPower", wrist.get());
        //SmartDashboard.putBoolean("Grabber/BackLimitSwitch", isBackLimitSwitchClosed());
        //SmartDashboard.putBoolean("Grabber/FrontLimitSwitch", isFrontLimitSwitchClosed());

        // if(isBackLimitSwitchClosed()){
        //     wristController.setTarget(getTiltPosition() - 20);
        // }

        //WristSafteyChecks();

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

    //#region limit switch / encoder based functions, commented right now

    // /**
    //  * Checks that the limitswitches arent closed and makes the motor stop
    //  * 
    //  * @param input power input to check
    //  * 
    //  * @return The power after checkes have been made
    //  */
    // public double WristSafteyChecks(double input)
    // {
    //     double output = input;
    //     if (isBackLimitSwitchClosed() && output <= 0) {
    //         output = 0;
    //     }

    //     return output;
    // }

    // /**
    //  * Resets all encoders to zero
    //  */
    // public void resetEncoders()
    // {
    //     wrist.setSelectedSensorPosition(0, 0, 30);
    // }

    // /**
    //  * Returns if the back limit switch is closed
    //  * 
    //  * @return A boolean that is true if it is closed
    //  */
    // public boolean isBackLimitSwitchClosed()
    // {
    //     return wrist.getSensorCollection().isRevLimitSwitchClosed();
    // }

    // /**
    //  * Returns if the front limit switch is closed
    //  * 
    //  * @return a bool that is true if the switch is closed
    //  */
    // public boolean isFrontLimitSwitchClosed()
    // {
    //     return wrist.getSensorCollection().isFwdLimitSwitchClosed();
    // }

    // /**
    //  * Returns the target that the wrist is trying to get to
    //  * 
    //  * @return Returns the tilt target
    //  */
    // public int getTiltTarget()
    // {
    //     return (int)wristController.getTarget();
    // }

    // /**
    //  * Sets the position of the tilt motor
    //  * 
    //  * @param position the position, in encoder ticks
    //  */
    // public void setTiltPosition(int position)
    // {
    //     wristController.setTarget(position);
    // }

    //#endregion

    /**
     * Allows the intake wheels to be operated
     * 
     * @param speed the speed of the intake wheels
     */
    public void runIntake(double speed)
    {
        intake.set(speed);
    }

    // /**
    //  * Gets the position of the tilt motot
    //  * 
    //  * @return the tilt position
    //  */
    // public int getTiltPosition()
    // {
    //     return wrist.getSelectedSensorPosition(0);
    // }

    /**
     * Sets the state of the secondary piston
     */
    public void setSecondaryState(boolean isOn)
    {
        if (hatchGrabberPrimary.get()) //checks if primary piston is out
        {
            hatchGrabberSecondary.set(isOn);
        }
    }

    /**
     * Toggles the state of the primary piston
     */
    public void togglePrimaryState()
    {
        boolean state = hatchGrabberPrimary.get();
        hatchGrabberPrimary.set(!state);
        hatchGrabberSecondary.set(!state); //retracts the secondary piston if primary is retracted
    }

    /**
     * Gets state of primary piston
     * 
     * @return the state of the piston
     */
    public boolean getPrimaryState()
    {
        return hatchGrabberPrimary.get();
    }

    /**
     * Sets the speed for the wrist motor
     * 
     * @param speed
     */
    public void setWristSpeed(double speed)
    {
        wrist.set(speed);
    }

    @Override
    public String toString()
    {
        return "" + //wrist.get() + "," +
                //wrist.getSelectedSensorPosition(0) + "," + 
                //wristController.getTarget() + "," +

                intake.get() + "," +

                hatchGrabberPrimary.get() + "," +
                hatchGrabberSecondary.get();
    }
}