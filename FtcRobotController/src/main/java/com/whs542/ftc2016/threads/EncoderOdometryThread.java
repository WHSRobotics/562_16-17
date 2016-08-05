package com.whs542.ftc2016.threads;

import com.whs542.ftc2016.subsys.WHSRobot;

public class EncoderOdometryThread implements Runnable
{
    //Milliseconds
    private long dt = 10;
    private long startTime;

    //state[0] = x
    //state[2] = y
    //state[3] = theta
    double [] state = new double[3];

    public void setStartLocation(double [] startState)
    {
        for(int i=0; i<3; i++)
        {
            state[i] = startState[i];
        }
    }
    /*
    ROBOT_WIDTH;

    public void updatePosition()
    {
        currentEncoderLeft = 0.0; //Update
        currentEncoderRight = 0.0; //update
        deltaTickLeft = currentEncoderLeft - previousEncoderLeft;
        deltaTickRight = currentEncoderRight - previousEncoderRight;
        deltaLeft = 2.0 * Math.PI/1120.0 * deltaTickLeft; //Use the ticks to distance conversion
        deltaRight = 2.0 * Math.PI/1120.0 * deltaTickRight; //Use the ticks to distance conversion
        deltaCenter = (deltaLeft + deltaCenter)/2.0;
        state[0] = state[0] + deltaCenter * Math.Cos(state[2]);
        state[1] = state[1] + deltaCenter * Math.Sin(state[2]);
        state[2] = state[2] + (deltaRight - deltaLeft)/ROBOT_WIDTH;
        previousEncoderLeft = currentEncoderLeft;
        previousEncoderRight = currentEncoderRight;
    }*/

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
    /*public class ArmThread implements Runnable
    {
        public void run()
        {
            while (true)
            {
                armPower = gamepad2.right_stick_y;
                // Sleep for 10 ms.
                try { Thread.sleep(10); }
                // Catch an interrupt exception that hopefully never happens.
                catch (InterruptedException ex)
                {Thread.currentThread().interrupt(); }
            }
        }
    }*/