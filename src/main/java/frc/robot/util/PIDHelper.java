package frc.robot.util;

/**
 * A Helper Class for all pid loops
 */
public class PIDHelper {

    public static double PID_MATH(double dt, int target, int currentPosition, intObj previousError, doubleObj integral, double Kp, double Ki, double Kd){
        int error = target - currentPosition;
        integral.value = integral.value + error * dt;
        double derivarive = (error - previousError.value) / dt;
        double output = Kp * error + Ki * integral.value + Kd * derivarive;
        previousError.value = error;

        return output;
    }
}
