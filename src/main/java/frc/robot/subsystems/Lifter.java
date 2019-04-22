
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.*;
import frc.robot.util.constants.Constants;
import frc.robot.RobotMap;
import frc.robot.*;

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

    private static boolean PID_Enabled = true;

    private static PIDexecutor backLoop;
    private static PIDexecutor leftLoop;
    private static PIDexecutor rightLoop;
    // private static PIDexecutor stablilization;

    private static boolean backLegManualControl;

    public Lifter()
    {
        legL = new WPI_TalonSRX(RobotMap.legL);
        legR = new WPI_TalonSRX(RobotMap.legR);
        legBack = new WPI_TalonSRX(RobotMap.legBack);
        wheel = new WPI_TalonSRX(RobotMap.wheel);

        legL.setInverted(true);
        wheel.setInverted(true);

        resetEncoders();

        backLoop = new PIDexecutor(Constants.BACK_LEG_KP, Constants.BACK_LEG_KI, Constants.BACK_LEG_KD, legBack.getSelectedSensorPosition(0), new DoubleSupplier()
        {
            @Override
            public double getAsDouble()
            {
                return -legBack.getSelectedSensorPosition(0);
            }
        });

        leftLoop = new PIDexecutor(Constants.FRONT_LEG_KP, Constants.FRONT_LEG_KI, Constants.FRONT_LEG_KD, legL.getSelectedSensorPosition(0), new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return legL.getSelectedSensorPosition(0);
            }
        });

        rightLoop = new PIDexecutor(Constants.FRONT_LEG_KP, Constants.FRONT_LEG_KI, Constants.FRONT_LEG_KD, legR.getSelectedSensorPosition(0), new DoubleSupplier()
        {
            @Override
            public double getAsDouble() 
            {
                return legR.getSelectedSensorPosition(0);
            }
        });

        // stablilization = new PIDexecutor(Constants.STABLILIZATION_KP, Constants.STABLILIZATION_KI, Constants.STABLILIZATION_KD, 0, new DoubleSupplier()
        // {
        //     @Override
        //     public double getAsDouble() 
        //     {
        //         return legL.getSelectedSensorPosition(0) - legR.getSelectedSensorPosition(0);
        //     }
        // });

        // legL.configPeakCurrentLimit(250);
        // legL.configPeakCurrentDuration(5000);
        // legL.configContinuousCurrentLimit(0);
        // legL.enableCurrentLimit(true);

        // legR.configPeakCurrentLimit(250);
        // legR.configPeakCurrentDuration(5000);
        // legR.configContinuousCurrentLimit(0);
        // legR.enableCurrentLimit(true);

        // legBack.configPeakCurrentLimit(250);
        // legBack.configPeakCurrentDuration(5000);
        // legBack.configContinuousCurrentLimit(0);
        // legBack.enableCurrentLimit(true);

        backLegManualControl = false;

        SmartDashboard.putString("Lifter/Status", Status.Good.toString());
    }

    /**
     * Where the default command is set
     */
    @Override
    public void initDefaultCommand()
    {
    }

    /**
     * Called when the run method of the Scheduler is called 
     */
    @Override
    public void periodic()
    {
        if (Robot.oi.wheelForwardButton.get())
        {
            setWheelSpeed(1);
        }
        else if (Robot.oi.wheelBackwardButton.get())
        {
            setWheelSpeed(-1);
        }
        else
        {
            setWheelSpeed(0);
        }
        
        
        if (Robot.oi.backLegDown.get())
        {
            setBackTarget(getBackTarget() + 25);
            backLegManualControl = true;
        }
        else if (Robot.oi.backLegUp.get())
        {
            setBackTarget(getBackTarget() - 35);
            backLegManualControl = true;
        }
        else if (backLegManualControl)
        {
            setBackTarget(getBackTarget());
            backLegManualControl = false;
        }

        runLoops();
    }

    /**
     * Checks if the current position is outside the range of allowable positions
     * 
     * Used in setting targets
     * 
     * @param currentPosition the current position of the mechanism
     * @param maxPosition the maximum allowable position
     * @param minPosition the minimun allowable position
     * @return the target position inside the allowable range
     */
    public int safetyChecks(int currentPosition, int maxPosition, int minPosition)
    {
        return (currentPosition > maxPosition) ? maxPosition : ((currentPosition < minPosition) ? minPosition : currentPosition);
    }

    /**
     * Runs PID loops for legs
     */
    public void runLoops()
    {
        if (!PID_Enabled)
        {
            return;
        }

        //back PID
        double backPower = backLoop.run();
        legBack.set(backPower);

        // double correction = stablilization.run();

        //Front Left PID
        double leftPower = leftLoop.run();// - correction;
        legL.set(leftPower);

        //Front Right PID
        double rightPower = rightLoop.run();// + correction;
        legR.set(rightPower);
    }
    
    /**
     * Sets if the PID loop is enabled
     * 
     * @param enabled if the loop is enabled or not
     */
    public void setPIDEnabled(boolean enabled)
    {
        PID_Enabled = enabled;
    }
    
    /**
     * Resets all encoders to zero
     */
    public void resetEncoders()
    {
        legL.setSelectedSensorPosition(0, 0, 30);
        legR.setSelectedSensorPosition(0, 0, 30);
        legBack.setSelectedSensorPosition(0, 0, 30);
    }

    /**
     * Gets the position of the left front leg
     * 
     * @return the position, in ticks
     */
    public int getFrontLPosition()
    {
        return legL.getSelectedSensorPosition(0);
    }

    /**
     * Gets the position of the right front leg
     * 
     * @return the position, in ticks
     */
    public int getFrontRPosition()
    {
        return legR.getSelectedSensorPosition(0);
    }

    /**
     * Gets the position of the back leg
     * 
     * @return the position, in ticks
     */
    public int getBackPosition()
    {
        return legBack.getSelectedSensorPosition(0);
    }

    /**
     * Sets the targets for the climb motors
     * 
     * @param backTarget the target for the back
     * @param frontLTarget the target for the frontL
     * @param frontRTarget the target for the frontR
     */
    public void setTargets(int frontLTarget, int frontRTarget, int backTarget)
    {
        setBackTarget(safetyChecks(backTarget, Constants.BACK_LEG_MAX, Constants.BACK_LEG_MIN));
        setFrontLTarget(safetyChecks(frontLTarget, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
        setFrontRTarget(safetyChecks(frontRTarget, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
    }

    /**
     * Sets the targets for the climb motors
     * 
     * @param backTarget the target for the back
     * @param frontTarget the target fot the front motors
     */
    public void setTargets(int frontTarget, int backTarget)
    {
        setBackTarget(safetyChecks(backTarget, Constants.BACK_LEG_MAX, Constants.BACK_LEG_MIN));
        setFrontLTarget(safetyChecks(frontTarget, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
        setFrontRTarget(safetyChecks(frontTarget, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
    }

    /**
     * Sets the targets for both front motors
     * 
     * @param target the target for the front motors
     */
    public void setFrontTargets(int target)
    {
        setFrontLTarget(target);
        setFrontRTarget(target);
    }

    /**
     * Sets the target for the back motor
     * 
     * @param target the target position
     */
    public void setBackTarget(int target)
    {
        backLoop.setTarget(safetyChecks(target, Constants.BACK_LEG_MAX, Constants.BACK_LEG_MIN));
    }

    /**
     * Sets the target for the front left motor
     * 
     * @param target the target position
     */
    public void setFrontLTarget(int target)
    {
        leftLoop.setTarget(safetyChecks(target, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
    }

    /**
     * Sets the target for the front right motor
     * 
     * @param target the target position
     */
    public void setFrontRTarget(int target)
    {
        rightLoop.setTarget(safetyChecks(target, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
    }

    /**
     * Operates the wheel mounted on the leg
     * 
     * @param speed the target speed
     */
    public void setWheelSpeed(double speed)
    {
        wheel.set(speed);
    }

    /**
     * Sets the speed of the rear leg motor
     * 
     * @param speed the target speed
     */
    public void setLegBackSpeed(double speed)
    {
        legBack.set(speed);
    }

    /**
     * Sets the speed of the front leg motors
     * 
     * @param speed the target speed
     */
    public void setLegsFrontSpeed(double speed)
    {
        legL.set(speed);
        legR.set(speed);
    }

    /**
     * Gets the target for the back leg
     * 
     * @return the back target
     */
    public int getBackTarget()
    {
        return (int)backLoop.getTarget();
    }

    /**
     * Gets the target for the front left leg
     * 
     * @return the front left target
     */
    public int getFrontLTarget()
    {
        return (int)leftLoop.getTarget();
    }

    /**
     * Gets the target for the front right leg
     * 
     * @return the front right target
     */
    public int getFrontRTarget()
    {
        return (int)rightLoop.getTarget();
    }

    @Override
    public String toString()
    {
        return "" + legR.get() + "," +
                legL.get() + "," +
                legBack.get() + "," +

                getFrontRPosition() + "," +
                getFrontLPosition() + "," +
                getBackPosition() + "," +

                getFrontRTarget() + "," +
                getFrontLTarget() + "," +
                getBackTarget() + "," +

                wheel.get();
    }
}