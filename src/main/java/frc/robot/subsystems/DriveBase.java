
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.*;

import java.lang.Math;

/**
 * This subsystem defines the drivebase subsystem of the robot
 * 
 * <p> The drivebase is holonomic, with four mechanum wheels </p>
 * 
 * <p> Each wheel is powered by one motor, connected to a TalonSRX </p>
 */
public class DriveBase extends Subsystem
{

    private static WPI_TalonSRX driveLF; //Left front wheel
    private static WPI_TalonSRX driveLR; //Left rear wheel
    private static WPI_TalonSRX driveRF; //Right front wheel
    private static WPI_TalonSRX driveRR; //Right rear wheel

    private static PigeonIMU imu; //The IMU

    private static double speedCoef; //Is used by the joystick version of move to lower max speed

    private static double targetHeading; //The heading the move functions attempt to maintain

    private static boolean PIDon;
    private static double headingAccumError; //The accumated heading error for PID
    private static double headingLastErrors[]; //The last heading error for PID
    private static double headingCurrDeriv;

    public DriveBase()
    {
        super();

        //Creates motor controllers as specified by the RobotMap
        driveLF = new WPI_TalonSRX(RobotMap.driveLF);
        driveLR = new WPI_TalonSRX(RobotMap.driveLR);
        driveRF = new WPI_TalonSRX(RobotMap.driveRF);
        driveRF.setInverted(true);
        driveRR = new WPI_TalonSRX(RobotMap.driveRR);
        driveRR.setInverted(true);

        //Creates IMU with motor controller using RobotMap
        switch (RobotMap.pigeonIMU)
        {
        case RobotMap.driveLF:
            imu = new PigeonIMU(driveLF);
            break;
        case RobotMap.driveLR:
            imu = new PigeonIMU(driveLR);
            break;
        case RobotMap.driveRF:
            imu = new PigeonIMU(driveRF);
            break;
        case RobotMap.driveRR:
            imu = new PigeonIMU(driveRR);
        }

        speedCoef = Constants.SPEED_NORMAL; //Drive speed for UserDrive

        targetHeading = getHeading();

        PIDon = false;
        headingAccumError = 0;
        headingLastErrors = new double[2];
        headingCurrDeriv = 0;

        SmartDashboard.putBoolean("DriveBase/Initialized", true);
    }

    /**
     * Sets the default command, to run if no other command requires the DriveBase
     */
    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new UserDrive());
    }
    
    /**
     * Called when the run method of the Scheduler is called 
     */
    @Override
    public void periodic()
    {
        Command command = getCurrentCommand();
        if (command != null && command.getName().equals("UserDrive"))
        {
            if (Robot.oi.slowButton.get())
            {
                speedCoef = Constants.SPEED_SLOW;
            }
            else if (Robot.oi.fastButton.get())
            {
                speedCoef = Constants.SPEED_FAST;
            }
            else 
            {
                speedCoef = Constants.SPEED_NORMAL;
            }
        }

        double currentHeading = getHeading();
        headingAccumError += targetHeading - currentHeading;
        headingLastErrors[1] = headingLastErrors[0];
        headingLastErrors[0] = currentHeading;
        headingCurrDeriv = headingLastErrors[0] - headingLastErrors[1];
    }

    /**
     * Moves robot using joystick
     * 
     * @param joystick joystick used to control robot
     * @param withHeadingPID if true, IMU input will be used to correct heading
     * @param headless if true, will interpret directions as relative to field
     */
    public void move(Joystick joystick, boolean withHeadingPID, boolean headless)
    {
        double speed = joystick.getMagnitude() * speedCoef;
        speed = (speed > Constants.DEADZONE_MOVE) ? speed : 0;
        double direction = joystick.getDirectionRadians();
        double rotation = joystick.getTwist() * speedCoef;
        rotation = (Math.abs(rotation) > Constants.DEADZONE_TWIST) ? rotation : 0;

        if (withHeadingPID || headless)
        {
            move(speed, direction, rotation, withHeadingPID, headless);
        }
        else
        {
            moveWithoutIMU(speed, direction, rotation);
        }
    }

    /**
     * Moves robot using movement values with IMU input
     * 
     * @param speed target speed
     * @param direction target direction
     * @param rotation target rotation speed
     * @param withHeadingPID if true, will use IMU input to correct heading
     * @param headless if true, will interpret directions as relative to field
     */
    public void move(double speed, double direction, double rotation, boolean withHeadingPID, boolean headless)
    {
        double currentHeading = getHeading();

        if (headless)
        {
            direction -= currentHeading;
        }

        if (withHeadingPID)
        {
            double errorHeading = targetHeading - currentHeading;
            if (PIDon && rotation != 0)
            {
                rotation += Constants.DKP * errorHeading + Constants.DKI * headingAccumError + Constants.DKD * headingCurrDeriv;       
            }
            else
            {
                targetHeading = currentHeading;
            }
        }

        moveWithoutIMU(speed, direction, rotation);
    }

    /**
     * Moves robot using movement values without IMU input
     * 
     * @param speed target speed
     * @param direction target direction
     * @param rotation target rotation speed
     */
    public void moveWithoutIMU(double speed, double direction, double rotation)
    {
        if (rotation == 0)
        {
            targetHeading = getHeading();
        }

        double speed1 = Math.sqrt(2) * Math.sin(-direction + 3 * Math.PI / 4) * speed;
        double speed2 = Math.sqrt(2) * Math.sin(-direction + Math.PI / 4) * speed;

        double maxValue = Math.sqrt(2) * Math.abs(speed) + Math.abs(rotation);

        if (maxValue > 1)
        {
            rawMove((speed1 + rotation) / maxValue,
                    (speed2 + rotation) / maxValue,
                    (speed2 - rotation) / maxValue,
                    (speed1 - rotation) / maxValue);
        }
        else
        {
            rawMove((speed1 + rotation),
                    (speed2 + rotation),
                    (speed2 - rotation),
                    (speed1 - rotation));
        }
    }

    /**
     * Moves robot using raw motor values
     * 
     * @param LF power to left front wheel
     * @param LR power to left rear wheel
     * @param RF power to right front wheel
     * @param RR power to right rear wheel
     */
    public void rawMove(double LF, double LR, double RF, double RR)
    {
        driveLF.set(LF);
        driveLR.set(LR);
        driveRF.set(RF);
        driveRR.set(RR);
    }

    /**
     * Sets the imu's current heading, as well as the target heading
     * 
     * @param heading the heading to be set
     */
    public void setHeading(double heading)
    {
        imu.setFusedHeading(heading);
        targetHeading = heading;
    }

    /**
     * Accesses the imu heading
     * 
     * @return the imu's current heading
     */
    public double getHeading()
    {
        return imu.getFusedHeading();
    }

    /**
     * Sets the target heading
     * 
     * @param heading the new target heading
     */
    public void setTargetHeading(double heading)
    {
        targetHeading = heading;
    }

    /**
     * Allows PID to be turned on or off
     */
    public void setPIDon(boolean isOn)
    {
        PIDon = isOn;
        if (isOn)
        {
            headingAccumError = 0;
        }
    }
}