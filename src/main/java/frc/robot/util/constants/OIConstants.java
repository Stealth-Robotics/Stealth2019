
package frc.robot.util.constants;

public class OIConstants 
{
    // !< deadzone width
    public static final double DEADZONE_MOVE = 0.07;
    public static final double DEADZONE_TWIST = 0.15;

    // !< the drive axes
    public static final int DRIVE_JOYSTICK_Y = 1;
    public static final int DRIVE_JOYSTICK_X = 0;

    // !< twist axis
    public static final int DRIVE_JOYSTICK_TWIST = 2;
    
    // !< drive speed button
    public static final int SLOW_BUTTON = 1;
    public static final int FAST_BUTTON = 2;

    // !< reset heading button
    public static final int RESET_HEADING_BUTTON = 3;

    // vertical joystick axis for mech joystick
    public static final int ELEVATOR_JOYSTICK_Y = 1;

    // lifter control buttons
    public static final int LEG_DOWN_BUTTON = 1;
    public static final int LEG_UP_BUTTON = 2;
    public static final int NEXT_STAGE_BUTTON = 3;

    // button to override the lifter pid
    public static final int OVERRIDE_LIFT_PID_BUTTON = 1;
}