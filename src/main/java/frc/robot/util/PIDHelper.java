package frc.robot.util;

@Deprecated
/**
 * A Helper Class for all pid loops
 * @deprecated Not very clean or easy to use
 *             Will be removed eventually, probably
 *             use {@link #PIDexecutor()} instead
 */
public class PIDHelper {

    public static double PID_MATH(double dt, int target, int currentPosition, Reference<Integer> previousError, Reference<Double> integral, double Kp, double Ki, double Kd){
        int error = target - currentPosition;
        integral.value = integral.value + error * dt;
        double derivarive = (error - previousError.value) / dt;
        double output = Kp * error + Ki * integral.value + Kd * derivarive;
        previousError.value = error;

        return output;
    }
}
