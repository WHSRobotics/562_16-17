package com.whs542.ftc2016.autoOp;

import com.whs542.ftc2016.subsys.*;
import com.whs542.lib.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class BlueAutoOpRamp extends OpMode {
    WHSRobot bot;

    int state = 0;

    public void init() {
        bot = new WHSRobot(hardwareMap, Alliance.BLUE);
    }

    public void start() {
        bot.drive.zeroLeftEncoders();
        bot.drive.zeroRightEncoders();

        time = 0.0;
    }

    public void loop() {
        telemetry.addData("LF: %D", bot.drive.encoderValues[bot.drive.LF]);
        telemetry.addData("RF: %D", bot.drive.encoderValues[bot.drive.RF]);
        telemetry.addData("state", state);

        //Small values because of overshoot
        //AutoOp that goes directly towards the ramp
        switch (state) {
            case 0:
                bot.drive.setLeftRightPower(1.0, 0.1);
                if (bot.drive.hasTargetHit(1.0)) //value to be determined
                {
                    state = 1;
                }
                break;

            case 1:
                //Turn left towards ramp
                bot.drive.setLeftRightPower(0.0, 0.1);
                if (bot.drive.hasTargetHit(0.75)) {
                    state = 2;
                }
                break;
            /*
            case 2:
                //Turn more towards ramp
                bot.drive.setLeftRightPower(0.0, 0.1);
                if (bot.drive.hasTargetHit(0.75)) {
                    state = 3;
                }
                break;
            */
            case 2:
                //Turn to line up to the beacon
                bot.drive.setLeftRightPower(0.0, 0.05);
                if (bot.drive.hasTargetHit(1.0))//beacon has been switched
                {
                    state = 3;
                }
                break;

            case 3:
                //Drive up ramp
                bot.drive.setLeftRightPower(0.1, 0.1);
                if (bot.drive.hasTargetHit(1.0)) {
                    state = 4;
                }
                break;

            case 4:
                //Stop robot
                bot.drive.setLeftRightPower(0.0, 0.0);
                break;


        }

    }
}
