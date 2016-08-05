package com.whs542.lib.hwTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.lang.Override;

public class encTest extends OpMode
{
    DcMotor testMot;

    @Override
    public void init()
    {
        testMot = hardwareMap.dcMotor.get("motor");
        testMot.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void loop()
    {
        testMot.setPower(gamepad1.left_stick_y);
        telemetry.addData("power", testMot.getCurrentPosition());
    }

    @Override
    public void stop()
    {
    }
}