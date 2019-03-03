
package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.Robot;
import frc.robot.subsystems.*;

/**
 * Logs telemetry  to file
 */
public class Logging extends Command 
{
    private boolean run = true;
    private boolean isDone = false;

    private FileWriter logMatch;
	private FileWriter logSystems;
	private FileWriter logError;
	//private FileWriter logEvents;
	
	private long StartTime;
	
    private Date date;

    private PigeonIMU imu;
    private DriverStation driverStation;

    private Thread thread;
    
    public Logging() 
    {
        super("Logging");
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.driveBase);
        SmartDashboard.putString("Logging/Status", "Creating Logging");

        //create a new thread to run logging system off of
        thread = new Thread()
        {
            @Override
            public void run()
            {
            SmartDashboard.putString("Logging/Status", "Starting Up!");

            date = new Date();
            StartTime = date.getTime();
            
            try 
            {
                logMatch = new FileWriter("/LOGS/logMatch.csv", true);
                logSystems = new FileWriter("/LOGS/logSystems.csv", true);
                logError = new FileWriter("/LOGS/logError.csv", true);
                //logEvents = new FileWriter("/LOGS/logEvents.csv", true);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                System.out.println("Unable to create/find FileWriter");
                SmartDashboard.putString("Logging/Status", "Unable to create/find FileWriter");

                SmartDashboard.putBoolean("Logging/Initialized", false);
                //set the command to done
                isDone = true;

                //stop the thread
                Thread.currentThread().interrupt();
            }
            
            SmartDashboard.putBoolean("Logging/Initialized", true);

            while (true)
            {
                //get common things so that we dont call them a million times
                imu = Robot.driveBase.getPigeonIMU();
                driverStation = DriverStation.getInstance();

                //log the things!
                LogErrors();
                LogMatch();
                LogSystems();
            }
        }};
    }
  
    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
        //run = true;

        thread.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return isDone;
    }
  
    // Called once after isFinished returns true
    @Override
    protected void end()
    {
        //run = false;
        thread.interrupt();
        try
        {
            logError.close();
            logMatch.close();
            logSystems.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        SmartDashboard.putString("Logging/Status", "Ending");
    }
  
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
        end();
    }

    private void LogErrors() 
    {
		
        try 
        {
			//System Start Time, System Time, 
			//isBrownedOut, isSysActive,
			//Fault Count 3.3v, Fault Count 5v, Fault Count 6v,
			//CAN Status,
			//Gyro Last error, Gyro State
			logError.write(
					StartTime + "," +
					date.getTime() + "," +
					
					RobotController.isBrownedOut() + "," +
					RobotController.isSysActive() + "," +
					
					RobotController.getFaultCount3V3() + "," +
					RobotController.getFaultCount5V() + "," +
					RobotController.getFaultCount6V() + "," +
				
					RobotController.getCANStatus() + "," +
					
					imu.getLastError() + "," +
					imu.getState()
					
					+ "\n"
					);
        } 
        catch (IOException e)
        {
			e.printStackTrace();
            System.out.println("Unable to write to LogError");
            SmartDashboard.putString("Logging/ErrorLog/Status", "Unable to create/find FileWriter");
	    }
	}
  
    private void LogMatch()
    {
		
        try 
        {
			//System Start Time, System Time, 
			//Match Time, isFMSConnected, Event Name, Match Number, Match Type, Alliance, Replay Number, Game Specific Message
			logMatch.write(
					StartTime + "," +
					date.getTime() + "," +
					
                    Timer.getMatchTime() + "," +
					driverStation.isFMSAttached() + "," +
					driverStation.getEventName() + "," +
					driverStation.getMatchNumber() + "," +
					driverStation.getMatchType() + "," +
					driverStation.getAlliance() + "," +
					driverStation.getReplayNumber() + "," +
					driverStation.getGameSpecificMessage() 
					
					+ "\n"
			);
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
            System.out.println("Unable to write to LogMatch");
            SmartDashboard.putString("Logging/MatchLog/Status", "Unable to create/find FileWriter");
	    }
    }
    
    private void LogSystems() 
    {
		
        try 
        {
			//System Start Time, System Time, 
			//isBrownedOut, isSysActive,
			//Fault Count 3.3v, Fault Count 5v, Fault Count 6v,
			//CAN Status,
            //Gyro Last error, Gyro State
            
            DriveBase driveBase = Robot.driveBase;
            Elevator elevator = Robot.elevator;
            Grabber grabber = Robot.grabber;
            Lifter lifter = Robot.lifter;


			logSystems.write(
					StartTime + "," +
					date.getTime() + "," +
					
                    driveBase.getDefaultCommandName() + "," +
                    driveBase.toString() + "," +
					
                    elevator.getDefaultCommandName() + "," +
                    elevator.toString() + "," +
				
                    grabber.getDefaultCommandName() + "," +
                    grabber.toString() + "," +
					
                    lifter.getDefaultCommandName() + "," +
                    lifter.toString()
					
					+ "\n"
					);
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
            System.out.println("Unable to write to LogError");
            SmartDashboard.putString("Logging/SystemLog/Status", "Unable to create/find FileWriter");
	    }
	}
}
