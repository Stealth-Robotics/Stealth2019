
package frc.robot.util;

/**
 * Holds the constants in one place
 */
public class Constants
{
    //deadzone width
    public static final double DEADZONE_MOVE = 0.07;
    public static final double DEADZONE_TWIST = 0.15;

    //driving constants
    public static final double SPEED_SLOW = 0.3;
    public static final double SPEED_FAST = 1;
    public static final double SPEED_NORMAL = 0.6;

    //driving pid constants
    public static final double DKP = 0.01;
    public static final double DKI = 0.005;
    public static final double DKD = -0.001;

    //elvator pid constants
    public static final double EKP = 0.01;
    public static final double EKI = 0.005;

    //the drive axes
    public static final int DRIVE_JOYSTICK_Y = 1;
    public static final int DRIVE_JOYSTICK_X = 0;

    //twist axis
    public static final int DRIVE_JOYSTICK_TWIST = 2;

    //drive speed button
    public static final int SLOW_BUTTON = 1;
    public static final int FAST_BUTTON = 2;
}