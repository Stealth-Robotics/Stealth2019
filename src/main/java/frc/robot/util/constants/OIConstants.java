
package frc.robot.util.constants;

public class OIConstants 
{
    // !< deadzone width
    public static final double DEADZONE_MOVE = 0.07;
    public static final double DEADZONE_TWIST = 0.15;
    public static final double DEADZONE_GRABBER = 0.2;

    //trigger thresholds
    public static final double TRIGGER_THRESHOLD = 0.15;
    
    // !< the drive axes
    public static final int DRIVE_JOYSTICK_Y = 1;
    public static final int DRIVE_JOYSTICK_X = 0;

    // !< twist axis
    public static final int DRIVE_JOYSTICK_TWIST = 2;
    
    // !< drive speed button
    public static final int SLOW_BUTTON = 2;
    public static final int FAST_BUTTON = 1;

    // !< reset heading button
    public static final int RESET_HEADING_BUTTON = 3;

    public static final int ALIGN_WITH_TARGET_BUTTON = 4;

    // vertical joystick axis for mech joystick
    public static final int ELEVATOR_JOYSTICK_Y = 1;
    public static final double ELEVATOR_JOYSTICK_DEADZONE = 0.15;

    // lifter control buttons
    public static final int LEVEL_2_BUTTON = 4;
    public static final int LEVEL_3_BUTTON = 8;
    public static final int NEXT_STAGE_BUTTON = 6;
    public static final int CANCEL_CLIMB_BUTTON = 1;

    public static final int WHEEL_FORWARD_BUTTON = 13;
    public static final int WHEEL_BACKWARD_BUTTON = 16;

    public static final int BACK_LEG_UP_BUTTON = 15;
    public static final int BACK_LEG_DOWN_BUTTON = 14;

    // Buttons to control tilt motor for grabber
    // public static final int WRIST_POS_1_BUTTON = 4;
    // public static final int WRIST_POS_2_BUTTON = 2;
    // public static final int WRIST_POS_3_BUTTON = 1;

    public static final int GRAB_HATCH_BUTTON = 5;
    public static final int TOGGLE_HATCH_ENTEND_BUTTON = 6;

    public static final int WRIST_JOYSTICK_Y = 5;

    // button to rub intake
    public static final int RUN_INTAKE_TRIGGER = 2;
    public static final int REVERSE_INTAKE_TRIGGER = 3;

    // button to override the lifter pid
    public static final int OVERRIDE_LIFT_PID_BUTTON = 1;
    public static final int OVERRIDE_TILT_GRABBER_PID_BUTTON = 2;

    public static final int OVERRIDE_ELEVATOR_LIMIT_BUTTON = 2;
    public static final int OVERRIDE_ELEVATOR_PID = 3;
    
    //TODO: Make sure that these are the correct values!
    public static final int ELEVATOR_BALL_LEVEL_MODIFYER = 3;
    public static final int ELEVATOR_LEVEL1_BUTTON = 1;
    public static final int ELEVATOR_LEVEL2_BUTTON = 2;
    public static final int ELEVATOR_LEVEL3_BUTTON = 4;
}