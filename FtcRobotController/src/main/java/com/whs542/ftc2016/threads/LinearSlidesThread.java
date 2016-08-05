package com.whs542.ftc2016.threads;

import com.whs542.ftc2016.subsys.WHSRobot;

public class LinearSlidesThread implements Runnable
{
	//Milliseconds
	private long dt = 100;	
	private long startTime;
    private WHSRobot rob;

	private int speedMode;

	public LinearSlidesThread(WHSRobot reference)
	{
		speedMode = 0;
        rob = reference;
	}

	public void run()
	{
		while(!Thread.interrupted())
		{
			startTime = System.currentTimeMillis();

			switch (speedMode)
			{
                case 1:
					//rob.slides.shaftPosition +=
				break;

				case 0:

				break;
			}

			try
			{
				Thread.sleep(dt + Math.max(-dt, (startTime - System.currentTimeMillis()) ));
			}
			catch(InterruptedException e)
			{
				break;
			}
		}
	}

}