
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.drivebaseCommands.*;
import frc.robot.util.*;
import frc.robot.util.constants.*;

import java.lang.Math;
import java.util.function.DoubleSupplier;

import frc.robot.util.constants.Constants;

/**
 * This subsystem defines the drivebase of the robot
 * 
 * <p> The drivebase is holonomic, with four mechanum wheels </p>
 * 
 * <p> Each wheel is powered by one motor, connected to a TalonSRX </p>
 */
public class DriveBase extends Subsystem
{

    private static WPI_TalonSRX driveLF; // !< Left front wheel
    private static WPI_TalonSRX driveLR; // !< Left rear wheel
    private static WPI_TalonSRX driveRF; // !< Right front wheel
    private static WPI_TalonSRX driveRR; // !< Right rear wheel

    private static PigeonIMU imu; // !< The IMU

    private static double speedCoef; // !< Is used by the joystick version of move to lower max speed

    private static PIDexecutor headingPIDloop; 

    private static StopWatch timeSinceRot;

    private static boolean isDriverRotating;
    private static boolean isHeadingSet;

    public boolean EnableUserDrive = true;

    public DriveBase()
    {
        super();

        //Creates motor controllers as specified by the RobotMap
        driveLF = new WPI_TalonSRX(RobotMap.driveLF);
        // driveLF.setInverted(true);
        driveLR = new WPI_TalonSRX(RobotMap.driveLR);
        // driveLR.setInverted(true);
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
            break;
        default:
            imu = new PigeonIMU(new WPI_TalonSRX(RobotMap.pigeonIMU));
        }

        headingPIDloop = new PIDexecutor(Constants.DRIVE_KP, Constants.DRIVE_KI, Constants.DRIVE_KD, 0, new DoubleSupplier()
        {
        
            @Override
            public double getAsDouble() 
            {
                return getHeading();
            }
        });

        setHeading(0);

        speedCoef = Constants.SPEED_NORMAL; //Drive speed for UserDrive

        timeSinceRot = new StopWatch();

        SmartDashboard.putString("DriveBase/Status", Status.Good.toString());
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
        //Adjusts robot speed for user control
        if (Robot.oi.slowButton.get())
        {
            speedCoef = Constants.SPEED_SLOW;
        }
        else if (Robot.oi.fastButton.get())
        {
            speedCoef = Constants.SPEED_SORT_OF_SLOW;
        }
        else 
        {
            speedCoef = Constants.SPEED_NORMAL;
        }

        if (Robot.oi.resetHeadingButton.get())
        {
            setHeading(0);
        }

        //gets control values from joystick
        double speed = joystick.getMagnitude() * speedCoef;
        speed = (speed > OIConstants.DEADZONE_MOVE) ? speed : 0;
        double direction = joystick.getDirectionRadians();
        double rotation = joystick.getTwist() * speedCoef * Constants.SPEED_TURN;
        rotation = (Math.abs(rotation) > OIConstants.DEADZONE_TWIST) ? rotation : 0;

        //checks if IMU input necessary, and sends to appropriate function
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

        //adjusts target direction to make directions field relative
        if (headless)
        {
            direction -= currentHeading;
        }

        //uses a PID loop to keep robot facing same direction
        if (withHeadingPID)
        {
            //disables PID loop if rotating robot
            if (Math.abs(rotation) > OIConstants.DEADZONE_TWIST)
            {
                headingPIDloop.setTarget(currentHeading);

                isDriverRotating = true;
            }
            else
            {
                if (isDriverRotating)
                {
                    timeSinceRot.reset();
                    timeSinceRot.setTime(250);
                    isDriverRotating = false;
                    isHeadingSet = false;
                }
                else if (timeSinceRot.isExpired() && !isHeadingSet)
                {
                    headingPIDloop.setTarget(currentHeading);
                    headingPIDloop.reset();
                    isHeadingSet = true;
                }
                
                if (isHeadingSet)
                {
                    rotation -= headingPIDloop.run();
                }
            }
        }

        //System.out.println(currentHeading + " " + direction);

        //proceeds to motor calculations after corrections
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
        //speed for LF and RR
        double speed1 = Math.sqrt(2) * Math.sin(-direction + 3 * Math.PI / 4) * speed;
        //speed for RF and LR
        double speed2 = Math.sqrt(2) * Math.sin(-direction + Math.PI / 4) * speed;

        //maximum possible power value
        double maxValue = Math.sqrt(2) * Math.abs(speed) + Math.abs(rotation);

        //adjusts all power values to be less than one, if necessary, and sends to motor controllers
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
        headingPIDloop.setTarget(heading);
    }

    /**
     * Accesses the imu heading
     * 
     * @return the imu's current heading in radians
     */
    public double getHeading()
    {
        return -imu.getFusedHeading() * Math.PI / 180;
    }

    /**
     * Sets the target heading
     * 
     * @param heading the new target heading
     */
    public void setTargetHeading(double targetHeading)
    {
        double currentHeading = getHeading();
        
        double angleDiff = (currentHeading - targetHeading) % Constants.TWOPI;
        if(angleDiff < -Math.PI)
        {
            angleDiff += Constants.TWOPI;
        }
        else if(angleDiff > Math.PI)
        {
            angleDiff -= Constants.TWOPI;
        }

        targetHeading = currentHeading + angleDiff;

        
        headingPIDloop.setTarget(targetHeading);
    }

    /**
     * Gets the target heading of the Heading PID loop
     */
    public double getTargetHeading()
    {
        return headingPIDloop.getTarget();
    }

    /**
     * Allows PID to be turned on or off
     */
    public void resetHeadingAccumError()
    {
        headingPIDloop.reset();
    }

    /**
     * Returns the IMU
     * 
     * @return The Pigeon IMU Instance
     */
    public PigeonIMU getPigeonIMU()
    {
        return imu;
    }

    /**
     * Returns telemetry data about the subsystem
     */
    @Override
    public String toString()
    {
        return "" + driveLF.get() + "," +
                driveLR.get() + "," +
                driveRF.get() + "," +
                driveRR.get() + "," +

                driveLF.getSelectedSensorPosition(0) + "," +
                driveRF.getSelectedSensorPosition(0) + "," +

                imu.getFusedHeading() + "," + 
                getTargetHeading();
    }
}