
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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
 * <p> All wheel are controlled with one motor, connected to a TalonSRX </p>
 */
public class DriveBase extends Subsystem
{

    private static WPI_TalonSRX driveLF; //Left front wheel
    private static WPI_TalonSRX driveLR; //Left rear wheel
    private static WPI_TalonSRX driveRF; //Right front wheel
    private static WPI_TalonSRX driveRR; //Right rear wheel

    private static double speedCoef; //Is used by the joystick version of move to lower max speed

    public DriveBase()
    {
        super();

        driveLF = new WPI_TalonSRX(RobotMap.driveLF);
        driveLR = new WPI_TalonSRX(RobotMap.driveLR);
        driveRF = new WPI_TalonSRX(RobotMap.driveRF);
        driveRF.setInverted(true);
        driveRR = new WPI_TalonSRX(RobotMap.driveRR);
        driveRR.setInverted(true);

        speedCoef = Constants.SPEED_NORMAL;

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
    //TODO I'm guessing we want some soft of PID loop to keep the robot steady during auto.
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

            System.out.println(speedCoef);
        }
    }

    /**
     * Moves robot using joystick
     * 
     * @param joystick joystick used to control robot
     */
    public void move(Joystick joystick)
    {
        double speed = joystick.getMagnitude() * speedCoef;
        speed = (speed > Constants.DEADZONE_MOVE) ? speed : 0;
        double direction = joystick.getDirectionRadians();
        double rotation = joystick.getTwist() * speedCoef;
        rotation = (Math.abs(rotation) > Constants.DEADZONE_TWIST) ? rotation : 0;

        move(speed, direction, rotation);
    }

    /**
     * Moves robot using movement values
     * 
     * @param speed target speed
     * @param direction target direction
     * @param rotation target rotation speed
     */
    public void move(double speed, double direction, double rotation)
    {
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
}