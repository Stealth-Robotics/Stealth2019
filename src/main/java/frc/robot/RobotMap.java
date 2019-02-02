/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap 
{
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
  
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
  
    //ports for drivebase talons
    public static final int driveLF = 8;
    public static final int driveLR = 2;
    public static final int driveRF = 7;
    public static final int driveRR = 1;
  
    //ports for elevator talon
    public static final int elevator = 5;

    //module for hatch picker solenoid
    public static final int pickerModule = 16;
    //channel for hatch picker solenoid
    public static final int pickerChannel = 0;
    //ports for intake talons
    public static final int intakeL = 10;
    public static final int intakeR = 11;
    //port for tilt motor talon
    public static final int tilt = 13;

    //ports for lifter talons
    public static final int legL = 6;
    public static final int legR = 7;
    public static final int legBack = 8;
    public static final int wheel = 9;
  
    //talon for the pigeon IMU
    public static final int pigeonIMU = 12;
}
