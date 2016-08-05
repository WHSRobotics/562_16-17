package com.whs542.lib.hwTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.whs542.ftc2016.subsys.WHSRobot;
import com.whs542.lib.Alliance;

public class servoTest extends OpMode
{
    Integer [] servoNum = new Integer[6];
    Servo [] servos = new Servo[6];
    //WHSRobot bot;

    @Override
    public void init()
    {
        for(int i = 1; i<7; i++)
        {
            servoNum[i-1] = new Integer(i);
            servos[i-1] = hardwareMap.servo.get(servoNum[i-1].toString());
            //bot = new WHSRobot(hardwareMap, Alliance.RED);
        }
    }

    @Override
    public void loop()
    {
        for(int i  = 1; i<7; i++)
        {
            servos[i-1].setPosition(1.0 - Math.abs(gamepad1.left_stick_y * 0.46)); //Closed position is 0.8 for the servo box
        }
        telemetry.addData("gamepad left stick", gamepad1.left_stick_y);
        //bot.slides.setTransmissionPower(gamepad1.left_trigger == 1.0, gamepad1.left_bumper);
    }
    public void stop()
    {

    }
}