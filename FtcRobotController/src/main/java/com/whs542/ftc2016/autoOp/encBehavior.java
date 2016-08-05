package com.whs542.ftc2016.autoOp;

import com.qualcomm.robotcore.util.RobotLog;
import com.whs542.ftc2016.subsys.*;
import com.whs542.lib.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.whs542.lib.sensors.PIDController;
import com.whs542.lib.sensors.imu.Quaternion;
import com.whs542.lib.sensors.imu.Vector;

import org.FTC5866.lib.Bno055;

import java.lang.annotation.Target;

public class encBehavior extends OpMode{

    WHSRobot bot;

    public void init()
    {
        bot = new WHSRobot(hardwareMap, Alliance.BLUE);
        bot.drive.zeroLeftEncoders();
        bot.drive.zeroRightEncoders();
        timeChange = time;
    }

    int i = 0;
    double timeChange;

    public void loop() {
        bot.drive.setHook(false);
        bot.drive.updateEncoderValues();
        RobotLog.i("t: " + time + "LF: " + bot.drive.leftFrontFeet() + "LB: " + bot.drive.leftBackFeet() + "RF: " + bot.drive.rightFrontFeet() + "RB: " + bot.drive.rightBackFeet());
        switch(i)
        {
            case 0:
            bot.drive.setLeftRightPower(-1.0, -1.0);
                if(time > timeChange + 2.5)
                {
                    timeChange = time;
                    i = 1;
                }
                break;

            case 1:
                bot.drive.setLeftRightPower(1.0, 1.0);
                if(time > timeChange + 2.5)
                {
                    timeChange = time;
                    i = 0;
                }
                break;
        }
    }
    public void stop()
    {

    }
}
