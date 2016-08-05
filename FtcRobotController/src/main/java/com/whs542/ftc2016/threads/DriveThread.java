package com.whs542.ftc2016.threads;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.whs542.ftc2016.subsys.Drive;
import com.whs542.ftc2016.DebugOp;
import com.whs542.ftc2016.subsys.WHSRobot;

public class DriveThread implements Runnable
{
	//Milliseconds
	private long dt = 10;
	private long startTime;
    private WHSRobot rob;

	public DriveThread(WHSRobot reference)
    {
        rob = reference;
    }

	public void run()
	{
		while(!Thread.interrupted())
		{
			startTime = System.currentTimeMillis();

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