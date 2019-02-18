
package frc.robot.util.constants;

public class OIConstants 
{
    // !< deadzone width
    public static final double DEADZONE_MOVE = 0.07;
    public static final double DEADZONE_TWIST = 0.2;

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

    public static final int ALIGN_WITH_TARGET_BUTTON = 4;

    // vertical joystick axis for mech joystick
    public static final int ELEVATOR_JOYSTICK_Y = 1;

    // lifter control buttons
    public static final int LEVEL_2_BUTTON = 2;
    public static final int LEVEL_3_BUTTON = 1;
    public static final int NEXT_STAGE_BUTTON = 3;
    public static final int CANCEL_CLIMB_BUTTON = 4;

    // button to override the lifter pid
    public static final int OVERRIDE_LIFT_PID_BUTTON = 1;
}