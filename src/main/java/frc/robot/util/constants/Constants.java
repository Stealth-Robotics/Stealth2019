
package frc.robot.util.constants;

/**
 * Holds the constants in one place
 */
public class Constants
{
    // !< driving speed constants
    public static final double SPEED_SLOW = 0.3;
    public static final double SPEED_SORT_OF_SLOW = 0.6;
    public static final double SPEED_NORMAL = 1;
    public static final double SPEED_TURN = 0.75;

    // !< driving pid constants
    public static final double DRIVE_KP = 0.27;
    public static final double DRIVE_KI = 0.0033;
    public static final double DRIVE_KD = 0.055;

    // !< elevator speed constants
    public static final double ELEVATOR_SPEED_NORMAL = 20;

    // !< elvator pid constants
    public static final double ELEVATOR_KP = 0.005;
    public static final double ELEVATOR_KI = 0;
    public static final double ELEVATOR_KD = 0;

    // !< elevator position constants
    //TODO: Tune Elevator Constants
    public static final int ELEVATOR_TOP = 15000;
    public static final int ELEVATOR_BOTTOM = 0;
    public static final int ELEVATOR_LEVEL1_BALL = 0;
    public static final int ELEVATOR_LEVEL2_BALL = 1000;
    public static final int ELEVATOR_LEVEL3_BALL = 2000;
    public static final int ELEVATOR_LEVEL1_HATCH = 0;
    public static final int ELEVATOR_LEVEL2_HATCH = 1000;
    public static final int ELEVATOR_LEVEL3_HATCH = 2000;

    // !< Front Leg PID constants
    public static final double FRONT_LEG_KP = 0.0023;
    public static final double FRONT_LEG_KI = 0;
    public static final double FRONT_LEG_KD = -0;
    public static final int FRONT_LEG_MAX = 16500;
    public static final int FRONT_LEG_MIN = 0;

    // !< Back Leg PID constants
    public static final double BACK_LEG_KP = 0.0017;
    public static final double BACK_LEG_KI = 0;
    public static final double BACK_LEG_KD = -0;
    public static final int BACK_LEG_MAX = 17000;
    public static final int BACK_LEG_MIN = -17000;

    // Stablilization PID constants
    public static final double STABLILIZATION_KP = 0.009;
    public static final double STABLILIZATION_KI = 0;
    public static final double STABLILIZATION_KD = -0;

    // Lifter level constants
    public static final int FRONT_LEGS_LEVEL_GROUND = 950;
    public static final int BACK_LEG_LEVEL_GROUND = 500;
    public static final int FRONT_LEGS_LEVEL_2 = 7000;
    public static final int BACK_LEG_LEVEL_2 = 7250;
    public static final int FRONT_LEGS_LEVEL_3 = 16500;
    public static final int BACK_LEG_LEVEL_3 = 16700;
    public static final int FRONT_LEGS_LEVEL_0 = 0;
    public static final int BACK_LEG_LEVEL_0 = 0;

    //Wrist position constants
    // public static final int WRIST_VERTICAL = 0;
    // public static final int WRIST_HORIZONTAL = 100;
    // public static final int WRIST_DOWN = 150;

    //Wrist motor speed constant
    // public static final int WRIST_SPEED_NORMAL = 10;

    public static final double WRIST_SPEED = 0.5;

    // !< Tilt motor for grabber PID constants
    public static final double TILT_KP = -0.0015;
    public static final double TILT_KI = 0;
    public static final double TILT_KD = -0.000;

    //Rotation speed when finding vision target
    public static final double FIND_ROT_SPEED = 0.5;

    //Aligning with vision targets rotation PID constants
    public static final double ROT_ALIGN_KP = 0.01;
    public static final double ROT_ALIGN_KI = 0.01;
    public static final double ROT_ALIGN_KD = -0.01;
    public static final double ROT_ALIGN_END = 3;

    //Aligning with vision targets translation PID constants
    public static final double TRANS_ALIGN_KP = 0.1;
    public static final double TRANS_ALIGN_KI = 0.01;
    public static final double TRANS_ALIGN_KD = -0.01;

    //Driving to vision targets PID constants
    public static final double DRIVE_TOWARDS_KP = 0.01;
    public static final double DRIVE_TOWARDS_KI = 0.01;
    public static final double DRIVE_TOWARDS_KD = 0.01;
    public static final int DRIVE_TOWARDS_END = 75;

    public static final double INTAKE_SPEED = 1;

    //More driving to vision targets PID
    public static final double DESIRED_TARGET_AREA = 10;          // Area of the target when the robot reaches the wall
    public static final double SPEEDkP = 0.15;
    public static final double SPEEDkD = 0.5;
    public static final double MAX_DRIVE = 0.3;                   // Simple speed limit so we don't drive too fast

    public static final double STRAFEkP = 0.05;
    public static final double STRAFEkD = 0.5;
    public static final double MAX_STRAFE = 0.1;
    
    public static final double STEER_kP = 0.03;                    // how hard to turn toward the target
    public static final double STEER_kI = 0;   
    public static final double STEER_kD = 0;
    public static final double STEER_TARGET_RATIO = 1.812; //horizontal bounding box over vertical bounding box

    public static final double CURRENT_LIMIT_LIFTER = 250; //maximum current allowed for the lifter before current limited

    public static final double TWOPI = Math.PI * 2;
}