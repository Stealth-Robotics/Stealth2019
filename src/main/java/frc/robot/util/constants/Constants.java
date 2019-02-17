
package frc.robot.util.constants;

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
    public static final double DRIVE_KP = 0.005;
    public static final double DRIVE_KI = 0.0001;
    public static final double DRIVE_KD = -0.0001;

    // !< elvator pid constants
    public static final double ELEVATOR_KP = 0.01;
    public static final double ELEVATOR_KI = 0.005;

    // !< hardware dimensions
    public static final double DRIVE_WHEEL_RADIUS = 3.0;
    public static final double LEG_WHEEL_RADIUS = 1.5;

    // !< Back Leg PID constants
    public static final double BACK_LEG_KP = 0.004;
    public static final double BACK_LEG_KI = 0;
    public static final double BACK_LEG_KD = -0;
    public static final int BACK_LEG_MAX = 17000;
    public static final int BACK_LEG_MIN = -17000;

    // !< Front Leg PID constants
    public static final double FRONT_LEG_KP = 0.0015;
    public static final double FRONT_LEG_KI = 0;
    public static final double FRONT_LEG_KD = -0;
    public static final int FRONT_LEG_MAX = 16500;
    public static final int FRONT_LEG_MIN = 0;

    // Lifter level constants
    public static final int FRONT_LEGS_LEVEL_2 = 5000;
    public static final int BACK_LEG_LEVEL_2 = 5000;
    public static final int FRONT_LEGS_LEVEL_3 = 17500;
    public static final int BACK_LEG_LEVEL_3 = 17500;
    public static final int FRONT_LEGS_LEVEL_0 = 0;
    public static final int BACK_LEG_LEVEL_0 = 0;

    // !< Tilt motor for grabber constants
    public static final double TILT_KP = 0.01;
    public static final double TILT_KI = 0.01;
    public static final double TILT_KD = -0;
    public static final double TILT_MAX = 100;
    public static final double TILT_MIN = 0;

    //Finding vision targets PID constants
    public static final double FIND_KP = 0.01;
    public static final double FIND_KI = 0.01;
    public static final double FIND_KD = -0.01;
}