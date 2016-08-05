package com.whs542.ftc2016;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.RobotLog;
import com.whs542.lib.sensors.PIDController;

/**
 * Created by DanielWang on 2/12/16.
 */
public class PIDTest extends OpMode{
    //public DcMotor testMot;
    //public DcMotor testMot2;
    PIDController pidControl;
    private double correction;

    private DcMotor leftExtensionMotor;
    private DcMotor rightExtensionMotor;

    public void init()
    {
        //PID Testing//
        //testMot = hardwareMap.dcMotor.get("motor");
        //testMot2 = hardwareMap.dcMotor.get("motor2");
        //testMot.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        //testMot2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        leftExtensionMotor = hardwareMap.dcMotor.get("ls_le");
        rightExtensionMotor = hardwareMap.dcMotor.get("ls_re");

        leftExtensionMotor.setDirection(DcMotor.Direction.REVERSE);

        leftExtensionMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightExtensionMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        pidControl = new PIDController(0.00025, 0.0, 0.0, 0.6);
    }

    public double clamp(double value)
    {
        if(value > 1.0)
        {
            return 1.0;
        }
        else if(value < -1.0)
        {
            return -1.0;
        }
        else
        {
            return value;
        }
    }
    public void testLinearSlide(double power, double output)
    {

        leftExtensionMotor.setPower(clamp(power - output));
        rightExtensionMotor.setPower(clamp(power));
    }
    public void loop()
    {
        correction = pidControl.update(leftExtensionMotor.getCurrentPosition(), rightExtensionMotor.getCurrentPosition());
        /*
        if((Math.abs(testMot.getCurrentPosition() * (1.0/1120.0)) > 10.0) ||
        (Math.abs(testMot2.getCurrentPosition() * (1.0/1120.0)) > 10.0))
        {
            testMot.setPower(0.0);
            testMot2.setPower(0.0);
        }
        else
        {
            testMot.setPower(0.5 + pidControl.update(testMot.getCurrentPosition(), testMot2.getCurrentPosition()));
            testMot2.setPower(0.5 - pidControl.update(testMot.getCurrentPosition(), testMot2.getCurrentPosition()));
        }
        */
        testLinearSlide(gamepad1.left_stick_y, correction);
        telemetry.addData("Left Motor", leftExtensionMotor.getCurrentPosition() * (1.0/1120.0));
        telemetry.addData("Right Motor", rightExtensionMotor.getCurrentPosition() * (1.0/1120.0));
        telemetry.addData("Output", correction);
        RobotLog.i(getRuntime() + " TestMot: " + leftExtensionMotor.getCurrentPosition() + " TestMot2: " + rightExtensionMotor.getCurrentPosition() + " Update: " + correction);
    }
}
