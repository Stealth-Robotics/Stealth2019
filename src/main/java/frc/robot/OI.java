/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.commands.*;
import frc.robot.commands.grabberCommands.*;
import frc.robot.util.constants.Constants;
import frc.robot.util.constants.OIConstants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
  
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
  
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
  
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
  
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
  
    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
  
    public Joystick driveJoystick;

    public Button slowButton;
    public Button fastButton;
    public Button resetHeadingButton;

    public Button alignWithTargetButton;

    public Joystick mechJoystick;

    public Button level2Button;
    public Button level3Button;
    public Button nextStageButton;
    public Button cancelClimbButton;

    public Button wheelForwardButton;
    public Button wheelBackwardButton;

    public Button backLegUp;
    public Button backLegDown;

    public Button tiltGrabberPos1Button;
    public Button tiltGrabberPos2Button;
    public Button tiltGrabberPos3Button;

    public Button grabHatchButton;
    public Button releaseHatchButton;

    // public Button runIntakeButton;
    // public Button reverseIntakeButton;

    public Joystick stationJoystick;

    // public Button overrideLiftPIDButton;

  
    OI()
    {
        driveJoystick = new Joystick(0);

        slowButton = new JoystickButton(driveJoystick, OIConstants.SLOW_BUTTON);
        fastButton = new JoystickButton(driveJoystick, OIConstants.FAST_BUTTON);
        resetHeadingButton = new JoystickButton(driveJoystick, OIConstants.RESET_HEADING_BUTTON);

        alignWithTargetButton = new JoystickButton(driveJoystick, OIConstants.ALIGN_WITH_TARGET_BUTTON);

        mechJoystick = new Joystick(1);

        

        tiltGrabberPos1Button = new JoystickButton(mechJoystick, OIConstants.TILT_GRABBER_POS_1_BUTTON);
        tiltGrabberPos2Button = new JoystickButton(mechJoystick, OIConstants.TILT_GRABBER_POS_2_BUTTON);
        tiltGrabberPos3Button = new JoystickButton(mechJoystick, OIConstants.TILT_GRABBER_POS_3_BUTTON);

        tiltGrabberPos1Button.whenPressed(new SetTiltPos(Constants.TILT_VERTICAL));
        tiltGrabberPos2Button.whenPressed(new SetTiltPos(Constants.TILT_HORIZONTAL));
        tiltGrabberPos3Button.whenPressed(new SetTiltPos(Constants.TILT_DOWN));

        grabHatchButton = new JoystickButton(mechJoystick, OIConstants.GRAB_HATCH_BUTTON);
        releaseHatchButton = new JoystickButton(mechJoystick, OIConstants.RELEASE_HATCH_BUTTON);

        // runIntakeButton = new JoystickButton(mechJoystick, OIConstants.RUN_INTAKE_TRIGGER);
        // reverseIntakeButton = new JoystickButton(mechJoystick, OIConstants.REVERSE_INTAKE_TRIGGER);

        stationJoystick = new Joystick(2);

        level2Button = new JoystickButton(stationJoystick, OIConstants.LEVEL_2_BUTTON);
        level3Button = new JoystickButton(stationJoystick, OIConstants.LEVEL_3_BUTTON);
        nextStageButton = new JoystickButton(stationJoystick, OIConstants.NEXT_STAGE_BUTTON);
        cancelClimbButton = new JoystickButton(stationJoystick, OIConstants.CANCEL_CLIMB_BUTTON);

        wheelForwardButton = new JoystickButton(stationJoystick, OIConstants.WHEEL_FORWARD_BUTTON);
        wheelBackwardButton = new JoystickButton(stationJoystick, OIConstants.WHEEL_BACKWARD_BUTTON);

        backLegUp = new JoystickButton(stationJoystick, OIConstants.BACK_LEG_UP);
        backLegDown = new JoystickButton(stationJoystick, OIConstants.BACK_LEG_DOWN);

        level2Button.whenPressed(new DriveOntoHab(2));
        level3Button.whenPressed(new DriveOntoHab(3));



        // overrideJoystick = new Joystick(2);

        // overrideLiftPIDButton = new JoystickButton(overrideJoystick, OIConstants.OVERRIDE_LIFT_PID_BUTTON);
        // overrideLiftPIDButton.whenPressed(new OverrideLiftPID());
    }


}
