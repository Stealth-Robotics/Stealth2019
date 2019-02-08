
package frc.robot.subsystems;

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

    private boolean PID_Enabled = true;

    private int targetBack; // !< The target position for the back leg
    private Reference<Double> integralBack = new Reference<Double>(0.0); // !< Uses the Refrence class to store a pointer to the var
    private Reference<Integer> previousErrorBack = new Reference<Integer>(0);
    private int targetFrontL; // !< The target position for the front left leg
    private Reference<Double> integralFrontL = new Reference<Double>(0.0);
    private Reference<Integer> previousErrorFrontL = new Reference<Integer>(0);
    private int targetFrontR; // !< The target position for the front right leg
    private Reference<Double> integralFrontR = new Reference<Double>(0.0);
    private Reference<Integer> previousErrorFrontR = new Reference<Integer>(0);

    private StopWatch timer = new StopWatch();

    public Lifter()
    {
        legL = new WPI_TalonSRX(RobotMap.legL);
        legR = new WPI_TalonSRX(RobotMap.legR);
        legBack = new WPI_TalonSRX(RobotMap.legBack);
        wheel = new WPI_TalonSRX(RobotMap.wheel);

        targetBack = legBack.getSelectedSensorPosition(0);
        targetFrontL = legL.getSelectedSensorPosition(0);
        targetFrontR = legR.getSelectedSensorPosition(0);

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
        if(PID_Enabled){
            PIDLoop(timer.deltaTime());
        }
    }


    public int SafteyChecks(int curentPosition, int maxPosition, int minPosition){
        int outputPosition = curentPosition;

        if (curentPosition > maxPosition){
            outputPosition = maxPosition;
        }

        if (curentPosition < minPosition){
            outputPosition = minPosition;
        }

        return outputPosition;
    }

    public void PIDLoop(double dt){
        //back PID
        targetBack = SafteyChecks(legBack.getSelectedSensorPosition(0), Constants.BACK_LEG_MAX, Constants.BACK_LEG_MIN);
        double BackPower = PIDHelper.PID_MATH(dt, targetBack, legBack.getSelectedSensorPosition(0), previousErrorBack, integralBack, Constants.BACK_LEG_Kp, Constants.BACK_LEG_Ki, Constants.BACK_LEG_Kd);
        legBack.set(BackPower);

        //Front Left PID
        targetFrontL = SafteyChecks(legL.getSelectedSensorPosition(0), Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN);
        double LeftPower = PIDHelper.PID_MATH(dt, targetFrontL, legL.getSelectedSensorPosition(0), previousErrorFrontL, integralFrontL, Constants.FRONT_LEG_Kp, Constants.FRONT_LEG_Ki, Constants.FRONT_LEG_Kd);
        legL.set(LeftPower);

        //Front Right PID
        targetFrontR = SafteyChecks(legR.getSelectedSensorPosition(0), Constants.FRONT_LEG_MAX, Constants.FRONT_LEG_MIN);
        double RightPower = PIDHelper.PID_MATH(dt, targetFrontR, legR.getSelectedSensorPosition(0), previousErrorFrontR, integralFrontR, Constants.FRONT_LEG_Kp, Constants.FRONT_LEG_Ki, Constants.FRONT_LEG_Kd);
        legR.set(RightPower);
    }
    

    public void setPIDEnabled(boolean enabled){
        PID_Enabled = enabled;
    }
    

    //#region Set Targets
    
    /**
     * Sets the targets for the climb motors
     * 
     * @param backTarget
     * the target for the back
     * @param frontLTarget
     * the target for the frontL
     * @param frontRTarget
     * the target for the frontR
     */
    public void setTargets(int backTarget, int frontLTarget, int frontRTarget){
        setBackTarget(backTarget);
        setFrontLTarget(frontLTarget);
        setFrontRTarget(frontRTarget);
    }

    /**
     * Sets the targets for the climb motors
     * 
     * @param backTarget
     * the target for the back
     * @param frontTarget
     * the target fot the front motors
     */
    public void setTargets(int backTarget, int frontTarget){
        setBackTarget(backTarget);
        setFrontLTarget(frontTarget);
        setFrontRTarget(frontTarget);
    }

    /**
     * Sets the target for the back motor
     * 
     * @param target
     */
    public void setBackTarget(int target)
    {
        targetBack = target;
    }

    /**
     * Sets the target for the front motors
     * 
     * @param target
     */
    public void setFrontLTarget(int target){
        targetFrontL = target;
    }

    /**
     * Sets the target for the front motors
     * 
     * @param target
     */
    public void setFrontRTarget(int target){
        targetFrontR = target;
    }

    //#endregion

    //#region Set Speeds

    /**
     * Operates the wheel mounted on the leg
     * 
     * @param speed
     */
    public void setWheelSpeed(double speed)
    {
        wheel.set(speed);
    }

    /**
     * Sets the speed of the rear leg motor
     * 
     * @param speed
     */
    public void setLegBackSpeed(double speed)
    {
        legBack.set(speed);
    }

    /**
     * Sets the speed of the front leg motors
     * 
     * @param speed
     */
    public void setLegsFrontSpeed(double speed)
    {
        legL.set(speed);
        legR.set(speed);
    }

    //#endregion Set Speeds
}