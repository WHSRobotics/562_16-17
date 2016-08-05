package com.whs542.lib.hwTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;
import com.whs542.ftc2016.subsys.WHSRobot;
import com.whs542.lib.Alliance;
//import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by DanielWang on 2/20/16.
 */
public class servoArmTest extends OpMode{

    public Servo servoArm;

    //WHSRobot bot;
    private double position;

    public void init()
    {
        //bot = new WHSRobot(hardwareMap, Alliance.RED);
        servoArm = hardwareMap.servo.get("servo_test");
        position = 1.0;
    }
    public void loop()
    {
        if(position > 0.2)
        {
            servoArm.setPosition(position);
            position -= 0.0000005;
        }
        else
        {
            servoArm.setPosition(0.0);
        }
        //servoArm.setPosition(Math.abs(gamepad1.left_stick_y));
        RobotLog.i(getRuntime() + "Servo Position: " + position);
    }
    public void stop()
    {

    }
}
