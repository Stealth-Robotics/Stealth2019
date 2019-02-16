
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

    // !< elevator speed constants
    public static final double ELEVATOR_SPEED_NORMAL = 5;

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

    public static final int ELEVATOR_JOYSTICK_Y = 1;

    // !< drive speed button
    public static final int SLOW_BUTTON = 1;
    public static final int FAST_BUTTON = 2;

    // !< reset heading button
    public static final int RESET_HEADING_BUTTON = 3;

    // !< hardware dimensions
    public static final double DRIVE_WHEEL_RADIUS = 3.0;
    public static final double LEG_WHEEL_RADIUS = 1.5;

    // !< Back Leg PID constants
    public static final double BACK_LEG_KP = 1;
    public static final double BACK_LEG_KI = 1;
    public static final double BACK_LEG_KD = -1;
    public static final int BACK_LEG_MAX = 1000;
    public static final int BACK_LEG_MIN = 0;

    // !< Front Leg PID constants
    public static final double FRONT_LEG_KP = 1;
    public static final double FRONT_LEG_KI = 1;
    public static final double FRONT_LEG_KD = -1;
    public static final int FRONT_LEG_MAX = 1000;
    public static final int FRONT_LEG_MIN = 0;

    // Lifter level constants
    public static final int FRONT_LEGS_LEVEL_2 = 5000;
    public static final int BACK_LEG_LEVEL_2 = 5000;
    public static final int FRONT_LEGS_LEVEL_3 = 10000;
    public static final int BACK_LEG_LEVEL_3 = 10000;

    // !< Tilt motor for grabber constants
    public static final double TILT_KP = 0.01;
    public static final double TILT_KI = 0.01;
    public static final double TILT_KD = -0;
    public static final double TILT_MAX = 100;
    public static final double TILT_MIN = 0;

    public static final int OVERRIDE_LIFT_PID_BUTTON = 1;
}