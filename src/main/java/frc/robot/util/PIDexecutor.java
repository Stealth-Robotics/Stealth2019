
package frc.robot.util;

import java.util.function.DoubleSupplier;

/**
 * Runs a PID loop
 */
public class PIDexecutor
{
    double KP;
    double KI;
    double KD;

    double target;

    double accumError;
    double lastError;

    DoubleSupplier curValueFunct;

    StopWatch stopWatch;

    /**
     * Sets up variables
     * 
     * @param KP the proportional constant
     * @param KI the integral constant
     * @param KD the drrivative constant
     * @param curValueFunct the function for finding the error
     */
    public PIDexecutor(double KP, double KI, double KD, double target, DoubleSupplier curValueFunct)
    {
        this.KP = KP;
        this.KI = KI;
        this.KD = KD;
        this.target = target;

        accumError = 0;
        lastError = 0;

        this.curValueFunct = curValueFunct;
        
        stopWatch = new StopWatch();
    }

    /**
     * Run the PID executor
     */
    public double run()
    {
        double error = target - curValueFunct.getAsDouble();

        accumError += error;

        double result =  stopWatch.deltaTime() * (KP * error + KI * accumError + KD * (error - lastError));

        lastError = error;

        return result;
    }

    /**
     * Resets previous error variables
     */
    public void reset()
    {
        accumError = 0;
        lastError = curValueFunct.getAsDouble();
    }

    /**
     * Sets the target value
     * 
     * @param target the target value;
     */
    public void setTarget(double target)
    {
        this.target = target;
    }

    /**
     * Returns the current target
     * 
     * @return the current target
     */
    public double getTarget()
    {
        return target;
    }
}