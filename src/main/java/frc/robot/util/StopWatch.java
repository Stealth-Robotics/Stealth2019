/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

/**
 * An All around helpfull class to keep time in order
 */
public class StopWatch 
{
    long mLastTime = 0;
    long mLastTimeDT = 0;
	int mWaitTime = 0;
	
	public StopWatch(int waitTime)
	{
        mWaitTime = waitTime;
        mLastTime = now();
        mLastTimeDT = now();
    }
    
    public StopWatch()
	{
        mWaitTime = 0;
        mLastTime = now();
        mLastTimeDT = now();
	}
	
	public void setTime(int waitTime)
	{
		mWaitTime = waitTime;
	}
	
	public boolean isExpired()
	{
		if((now()-mLastTime)>mWaitTime)
		{
			return true;
		}
		return false;
	}
	
	public void reset()
	{
        mLastTime = now();
	}
	
	public long timeLeft()
	{
		return mWaitTime - (now()-mLastTime);
    }
    
    public static long now(){
        return System.nanoTime() * 1000000;
    }

    /**
     * Returns the time passed in miliseconds since the last time this function was called or the stopwatch was initilized
     * @return
     * The Delta Time
     */
    public long deltaTime(){
        //get the dt
        long output = now() - mLastTimeDT;
        //reset last time
        mLastTimeDT = now();
        //return it
        return output;
    }
}
