/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.Logging;
import frc.robot.commands.DrivebaseCommands.AutoDriveForward;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.Vision;

import frc.robot.util.constants.OIConstants;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot 
{
    public static Vision vision;
    public static DriveBase driveBase;
    public static Elevator elevator;
    public static Grabber grabber;
    public static Lifter lifter;
    public static OI oi;

    private Logging loggingCommand;
  
    Command m_autonomousCommand;
    SendableChooser<Command> m_chooser = new SendableChooser<>();
  
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() 
    {
        vision = new Vision();
        driveBase = new DriveBase();
        elevator = new Elevator();
        grabber = new Grabber();
        lifter = new Lifter();
        oi = new OI();

        //start logging command
        loggingCommand = new Logging();
        loggingCommand.start();

        m_chooser.setDefaultOption("Default Auto", new AutoDriveForward());
        // chooser.addOption("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", m_chooser);
    }
  
    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() 
    {
        putOiInfo();
    }
  
    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() 
    {
        System.out.printf("Disabled Init");
    }
  
    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }
  
    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() 
    {
        m_autonomousCommand = m_chooser.getSelected();
    
        /*
         * String autoSelected = SmartDashboard.getString("Auto Selector",
         * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
         * = new MyAutoCommand(); break; case "Default Auto": default:
         * autonomousCommand = new ExampleCommand(); break; }
         */
    
        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) 
        {
            m_autonomousCommand.start();
        }
    }
  
    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }
  
    @Override
    public void teleopInit() 
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) 
        {
            m_autonomousCommand.cancel();
        }
    }
  
    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
  
    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() 
    {
        driveBase.rawMove(0.2, 0.2, 0.2, 0.2);
    }

    /**
     * Pushes operation inferface information from joystick to network tables
     */
    public void putOiInfo()
    {
        SmartDashboard.putNumber("OI/X-axis", oi.driveJoystick.getRawAxis(OIConstants.DRIVE_JOYSTICK_X));
        SmartDashboard.putNumber("OI/Y-axis", oi.driveJoystick.getRawAxis(OIConstants.DRIVE_JOYSTICK_Y));
        SmartDashboard.putNumber("OI/Twist", oi.driveJoystick.getRawAxis(OIConstants.DRIVE_JOYSTICK_TWIST));
        SmartDashboard.putNumber("OI/Magnitude", oi.driveJoystick.getMagnitude());
        SmartDashboard.putNumber("OI/Direction", oi.driveJoystick.getDirectionDegrees());
        SmartDashboard.putNumber("Telemetry/Heading", driveBase.getHeading());
    }
}
