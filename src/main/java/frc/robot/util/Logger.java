
package frc.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
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
public class Logger implements Runnable
{

    private FileWriter logMatch;
	private FileWriter logSystems;
	private FileWriter logError;
	//private FileWriter logEvents;
	
	private long StartTime;
	
    private Date date;

    private PigeonIMU imu;
    private DriverStation driverStation;
    
    public Logger() 
    {
        SmartDashboard.putString("Logging/Status", "Initializing");

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

            SmartDashboard.putString("Logging/Status", "Unable to create FileWriter");

            //stop the thread
            //Thread.currentThread().interrupt();
        }
        
        SmartDashboard.putString("Logging/Status", "Initialized");
        
    }

    public void run()
    {
        //get common things so that we dont call them a million times
        imu = Robot.driveBase.getPigeonIMU();
        driverStation = DriverStation.getInstance();

        //log the things!
        LogErrors();
        LogMatch();
        LogSystems();

        SmartDashboard.putString("Logging/Status", "Running");
    }

    private void LogErrors() 
    {
		
        try 
        {
			//System Start Time, System Time, 
			//isBrownedOut, isSysActive, FPGA Revision
			//Fault Count 3.3v, Fault Count 5v, Fault Count 6v,
			//CAN Status,
			//Gyro Last error, Gyro State
			logError.write(
					StartTime + "," +
					date.getTime() + "," +
					
					RobotController.isBrownedOut() + "," +
                    RobotController.isSysActive() + "," +
                    RobotController.getFPGARevision() + "," +
					
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
            
            SmartDashboard.putString("Logging/ERROR", "Unable To Write LogError");
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
            
            SmartDashboard.putString("Logging/ERROR", "Unable To Write LogMatch");
	    }
    }
    
    private void LogSystems() 
    {
		
        try 
        {
			//System Start Time, System Time, 
            //Drivebase Default Command, Drivebase Log
            //Elevator Default Command, Elevator Log
            //Grabber Default Command, Grabber Log
            //Lifter Default Command, Lifter Log
            
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
            
            SmartDashboard.putString("Logging/ERROR", "Unable To Write LogSystems");
	    }
	}
}
