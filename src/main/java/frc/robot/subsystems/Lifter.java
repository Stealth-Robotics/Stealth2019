
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.util.*;
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

    private static boolean PID_Enabled = true;

    private static PIDexecutor backLoop;
    private static PIDexecutor leftLoop;
    private static PIDexecutor rightLoop;

    public Lifter()
    {
        legL = new WPI_TalonSRX(RobotMap.legL);
        legR = new WPI_TalonSRX(RobotMap.legR);
        legBack = new WPI_TalonSRX(RobotMap.legBack);
        wheel = new WPI_TalonSRX(RobotMap.wheel);

        backLoop = new PIDexecutor(Constants.BACK_LEG_KP, Constants.BACK_LEG_KI, Constants.BACK_LEG_KD, legBack.getSelectedSensorPosition(0), new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble()
            {
                return legBack.getSelectedSensorPosition(0);
            }
        });

        leftLoop = new PIDexecutor(Constants.FRONT_LEG_KP, Constants.FRONT_LEG_KI, Constants.FRONT_LEG_KD, legL.getSelectedSensorPosition(0), new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() 
            {
                return legR.getSelectedSensorPosition(0);
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

        SmartDashboard.putString("Lifter/Status", Status.Good.toString());
    }

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
        if(PID_Enabled)
        {
            PIDLoops();
        }
    }

    /**
     * Checks if the current position is outside the range of allowable positions
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
    public void PIDLoops()
    {
        //back PID
        double BackPower = backLoop.run();
        legBack.set(BackPower);

        //Front Left PID
        double LeftPower = leftLoop.run();
        legL.set(LeftPower);

        //Front Right PID
        double RightPower = rightLoop.run();
        legR.set(RightPower);
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

    public int getFrontLPosition()
    {
        return legL.getSelectedSensorPosition(0);
    }

    public int getFrontRPosition()
    {
        return legR.getSelectedSensorPosition(0);
    }

    public int getBackPosition()
    {
        return legR.getSelectedSensorPosition(0);
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
     * Sets the target for the back motor
     * 
     * @param target the target position
     */
    public void setBackTarget(int target)
    {
        backLoop.setTarget(safetyChecks(target, Constants.BACK_LEG_MAX, Constants.BACK_LEG_MIN));
    }

    /**
     * Sets the target for the front motors
     * 
     * @param target the target position
     */
    public void setFrontLTarget(int target)
    {
        leftLoop.setTarget(safetyChecks(target, Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN));
    }

    /**
     * Sets the target for the front motors
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
}