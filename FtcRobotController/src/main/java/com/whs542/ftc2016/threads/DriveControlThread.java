package com.whs542.ftc2016.threads;

import com.whs542.ftc2016.subsys.WHSRobot;

public class DriveControlThread implements Runnable
{
    //Milliseconds
    private long dt = 10;
    private long startTime;

    private SpeedMode speedMode;
    private MotionType motionType;

    private double goal;
    private double maxVelocity;
    private double ramptime;
    private double[] output;
    private double power;
    private double position;

    public enum SpeedMode
    {
        RAMP, CONSTANT, SLOW;
    }

    public enum MotionType
    {
        SPIN, TRANSLATE;
    }

    public DriveControlThread()
    {

    }

    //Goal position + max velocity


    //I assume nonsimilar behavior between motors -> characterize by voltage - speed relationship???

    //r=v/w

    public void spinStart(double goalSet)
    {
        goal = goalSet;
        speedMode = SpeedMode.RAMP;
        motionType = MotionType.SPIN;
    }

    public void translateStart(double goalSet)
    {
        goal = goalSet;
        speedMode = SpeedMode.RAMP;
        motionType = MotionType.TRANSLATE;
    }

    //given a symmetrical trapezoidal velocity -
    //base length = total time
    //top base = total time - 2(ramp time)
    //height = max velocity

    //max velocity as a function of ramp time
    //(totaltime - ramptime)max velocity = total position

    //1.0 is max velocity of motor -> set that w/ 0.5 ramp time

    //ramptime = 0.5 sec;

    //0.01 timesteps
    //0.5/0.01 => 50 steps
    //0.5 0.5

    public double[] getOutput()
    {
        switch(motionType)
        {
            case SPIN:
                output[2] = -output[2];
                break;
        }
        return output;
    }

    public void angularVelocityUpdate()
    {

    }

    public void rampVelocity(double input)
    {
        //Speed up the motor you want to speed up
        power = input;

        //double currentPower = motorToSpeedUp.getPower();
        while(power < maxVelocity)//1==1)//currentPower < powerYouWant)
        {
            power += maxVelocity/(ramptime / dt);
            //rob.drive.setLeftRightPower(power, power);
            //motorToSpeedUp.setPower(currentPower * 1.3);
        }

        //rob.drive.setLeftRightPower(maxVelocity, maxVelocity);
        //motorToSpeedUp.setPower(powerYouWant);
    }

    public void slowVelocity(double input)
    {
        power = input;
        //power = input;
        while(power > 0)
        {
            power -= maxVelocity/(ramptime / dt);
            //rob.drive.setLeftRightPower(power, power);
            // motorToSlowDown.setPower(power/2);
        }
        //rob.drive.setLeftRightPower(0,0);
    }

    public double getPower()
    {
        return power;
    }

    public void setPosition(double desiredPosition)
    {
        position = desiredPosition;
    }

// position = max velocity * (ramptime + constant time)
    //motor velocity = function of power we set

    public void run()
    {
        while(!Thread.interrupted())
        {
            switch(speedMode)
            {
                case RAMP:
                    rampVelocity(0.0);
                    if(Math.abs(maxVelocity - power) < .5) {
                        speedMode = SpeedMode.CONSTANT;
                    }
                    //output[] = output[];
                    break;

                case CONSTANT:
                    if(System.currentTimeMillis() == (position/maxVelocity)) //(System.currentTimeMillis() - startTime))* maxVelocity))
                    {
                        speedMode = SpeedMode.SLOW;
                    }
                    //output[]
                    break;

                case SLOW:
                    slowVelocity(1.0);
                    break;
            }
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