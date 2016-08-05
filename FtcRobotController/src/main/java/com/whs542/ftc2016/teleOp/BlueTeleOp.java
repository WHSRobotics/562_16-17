package com.whs542.ftc2016.teleOp;

import com.whs542.ftc2016.subsys.*;
import com.whs542.lib.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by DanielWang on 12/5/15.
 */

public class BlueTeleOp extends OpMode
{
    WHSRobot bot;

    public void init()
    {
        bot = new WHSRobot(hardwareMap, Alliance.BLUE);
    }

    public void loop() {
        //-----------------
        //Gamepad 1
        //-----------------
        //Drive Telemetry
        telemetry.addData("Hook", bot.drive.getHookState());
        telemetry.addData("Orientation", bot.drive.getOrientation());
        telemetry.addData("Scale", bot.drive.scale);

        //drive

        bot.drive.setPower(gamepad1.start);
        //bot.drive.setDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
        bot.drive.setOrientation(gamepad1.a);
        bot.drive.setHook(gamepad1.right_trigger == 1.0);

        //Slides
        bot.slides.setRamp(gamepad1.right_bumper);
        bot.slides.setAngle(gamepad1.dpad_up, gamepad1.dpad_down);
        bot.slides.setIntake(gamepad1.left_trigger == 1.0 || gamepad2.left_trigger == 1.0, gamepad1.left_bumper || gamepad2.left_bumper);
        //bot.slides.setTransmissionPower(gamepad1.y, gamepad1.x);
        bot.slides.testLinearSlide(0.5, gamepad1.y, gamepad1.x);

        bot.drive.setAutoArm(gamepad1.b);

    }
}
