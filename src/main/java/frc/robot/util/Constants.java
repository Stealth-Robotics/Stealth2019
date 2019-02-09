
package frc.robot.util;

/**
 * Holds the constants in one place
 */
public class Constants
{
    // !< deadzone width
    public static final double DEADZONE_MOVE = 0.07;
    public static final double DEADZONE_TWIST = 0.15;

    // !< driving constants
    public static final double SPEED_SLOW = 0.3;
    public static final double SPEED_FAST = 1;
    public static final double SPEED_NORMAL = 0.6;

    // !< driving pid constants
    public static final double DKP = 0.005;
    public static final double DKI = 0.0001;
    public static final double DKD = -0.0001;

    // !< elvator pid constants
    public static final double EKP = 0.01;
    public static final double EKI = 0.005;

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

    // !< hardware dimensions
    public static final double DRIVE_WHEEL_RADIUS = 3.0;
    public static final double LEG_WHEEL_RADIUS = 1.5;

    // !< Back Leg PID Constants
    public static final double BACK_LEG_Kp = 1;
    public static final double BACK_LEG_Ki = 1;
    public static final double BACK_LEG_Kd = 1;
    public static final int BACK_LEG_MAX = 1000;
    public static final int BACK_LEG_MIN = 0;

    // !< Front Leg PID Constants
    public static final double FRONT_LEG_Kp = 1;
    public static final double FRONT_LEG_Ki = 1;
    public static final double FRONT_LEG_Kd = 1;
    public static final int FRONT_LEG_MAX = 1000;
    public static final int FRONT_LEG_MIN = 0;

    public static final int OVERRIDE_LIFT_PID_BUTTON = 1;
}