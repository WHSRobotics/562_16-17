package com.whs542.ftc2016.subsys;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.whs542.lib.*;

//
// Drive Subsystem Class
//

//TODO: Define Subsys class
//TODO: Define an interface?
//TODO: Implement left side/right side travel distance

public class Drive
{
    private static final double WHEEL_DIAMETER = 0.5;
    private static final double TICKS_TO_ROT_WHEEL = 0.75/1120.0;
    private static final double TICKS_TO_RAD_MOTOR = 2.0*Math.PI*TICKS_TO_ROT_WHEEL;
    //Sprocket ratio of 24/32
    private static final double TICKS_TO_FT = WHEEL_DIAMETER * Math.PI *TICKS_TO_ROT_WHEEL;
    private static final double JOY_THRESHOLD = 0.1;

    private Servo leftChurroHook;
    private Servo rightChurroHook;

    private Toggler hookSwitch = new Toggler(2);
    private Toggler orientationSwitch = new Toggler(2);
    private Toggler drivePowerSwitch = new Toggler(2);

	private DcMotor rightFrontMotor;
	private DcMotor rightBackMotor;
	private DcMotor leftFrontMotor;
	private DcMotor leftBackMotor;

    private Servo autoArm;

    public double [] encoderZeroes;
    public double [] encoderValues;

    public int RF = 0;
    public int RB = 1;
    public int LF = 2;
    public int LB = 3;
	// ----------------------------------
	// Drive Constructor
	// ----------------------------------
	// -Initializes the hardware references

	public Drive(HardwareMap driveMap)
	{
        rightChurroHook = driveMap.servo.get("drive_rch");
        leftChurroHook = driveMap.servo.get("drive_lch");
        autoArm = driveMap.servo.get("drive_auto");

		rightFrontMotor = driveMap.dcMotor.get("drive_rf");
        rightBackMotor = driveMap.dcMotor.get("drive_rb");
        leftFrontMotor = driveMap.dcMotor.get("drive_lf");
        leftBackMotor = driveMap.dcMotor.get("drive_lb");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        rightFrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightBackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftFrontMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightBackMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        encoderZeroes = new double[4];
        encoderValues = new double[4];
    }

    // ----------------------------------
    // Hook Methods
    // ----------------------------------

    public void setHook(boolean trigger)
    {
        hookSwitch.changeState(trigger);
        switch(hookSwitch.currentState())
        {
            case 0:
                unhook();
                break;

            case 1:
                hook90();
            break;
        }
    }

    public String getHookState()
    {
        String state = "null";
        switch(hookSwitch.currentState())
        {
            case 0:
                state = "Unhooked";
            break;

            case 1:
                state = "Hooked";
            break;
        }
        return state;
    }

    public void unhook()
    {
        leftChurroHook.setPosition(0.0);
        rightChurroHook.setPosition(1.0);
    }

    public void hook()
    {
        leftChurroHook.setPosition(1.0);
        rightChurroHook.setPosition(0.0);
    }

    public void hook90()
    {
        leftChurroHook.setPosition(0.45);
        rightChurroHook.setPosition(0.4);
    }

    // ----------------------------------
    // Drive Methods
    // ----------------------------------

    public void setLeftRightPower(double leftPower, double rightPower)
    {
        switch(orientationSwitch.currentState())
        {
            case 0:
                rightFrontMotor.setPower(7.0/9.0*rightPower);
                rightBackMotor.setPower(7.0/9.0*rightPower);
                leftFrontMotor.setPower(7.0/9.0*leftPower);
                leftBackMotor.setPower(7.0/9.0*leftPower);
                break;

            case 1:
                rightFrontMotor.setPower(-7.0/9.0*leftPower);
                rightBackMotor.setPower(-7.0/9.0*leftPower);
                leftFrontMotor.setPower(-7.0/9.0*rightPower);
                leftBackMotor.setPower(-7.0/9.0*rightPower);
                break;
        }
    }

    public void setDrive(double leftPower, double rightPower)
    {
        rightPower = Math.abs(rightPower) > JOY_THRESHOLD ? rightPower : 0.0;
        leftPower = Math.abs(leftPower) > JOY_THRESHOLD ? leftPower : 0.0;
        switch(orientationSwitch.currentState())
        {
            case 0:
                rightFrontMotor.setPower(rightPower * scale);
                rightBackMotor.setPower(rightPower * scale);
                leftFrontMotor.setPower(leftPower * scale);
                leftBackMotor.setPower(leftPower * scale);
            break;

            case 1:
                rightFrontMotor.setPower(-leftPower * scale);
                rightBackMotor.setPower(-leftPower * scale);
                leftFrontMotor.setPower(-rightPower * scale);
                leftBackMotor.setPower(-rightPower * scale);
                break;
        }
    }

    public double scale;
    public void setPower(boolean trigger)
    {
        drivePowerSwitch.changeState(trigger);
        switch(drivePowerSwitch.currentState())
        {
            case 0:
                scale = 1.0;
                break;

            case 1:
                scale = 0.8;
                break;
        }
    }

    public void setOrientation(boolean trigger)
    {
        orientationSwitch.changeState(trigger);
    }

    public String getOrientation()
    {
        String o = "null";
        switch(orientationSwitch.currentState())
        {
            case 0:
                o = "Normal";
                        break;

            case 1:
                o = "Reverse";
                break;
        }
        return o;
    }

    public boolean hasTargetHit(double target)
    {
        updateEncoderValues();
        double max = 0;
        for(int i = 0; i < 4; i++)
        {
            max = (encoderValues[i] > max) ? encoderValues[i] : max;
        }
        if(Math.abs(max)*TICKS_TO_FT > target)
        {
            zeroLeftEncoders();
            zeroRightEncoders();
            return true;
        }
        else
        {
            return false;
        }
    }

    public double maxEncValue()
    {
        updateEncoderValues();
        double max = 0.0;
        for(int i = 0; i < 4; i++)
        {
            max = (encoderValues[i] > max) ? encoderValues[i] : max;
        }
        return Math.abs(max) * TICKS_TO_FT;
    }

    public void updateEncoderValues()
    {
        encoderValues[RF] = rightFrontMotor.getCurrentPosition()-encoderZeroes[RF];
        encoderValues[RB] = rightBackMotor.getCurrentPosition()-encoderZeroes[RB];
        encoderValues[LF] = leftFrontMotor.getCurrentPosition()-encoderZeroes[LF];
        encoderValues[LB] = leftBackMotor.getCurrentPosition()-encoderZeroes[LB];
    }

    public double leftEncoderAvg()
    {
        return TICKS_TO_FT*(encoderValues[LF] + encoderValues[LB])/2.0;
    }

    public double rightEncoderAvg()
    {
        return TICKS_TO_FT*(encoderValues[RF] + encoderValues[RB])/2.0;
    }

    public void zeroLeftEncoders()
    {
        encoderZeroes[LF] = leftFrontMotor.getCurrentPosition();
        encoderZeroes[LB] = leftBackMotor.getCurrentPosition();
    }

    public void zeroRightEncoders()
    {
        encoderZeroes[RF] = rightFrontMotor.getCurrentPosition();
        encoderZeroes[RB] = rightBackMotor.getCurrentPosition();
    }

    public double leftFrontFeet()
    {
        return encoderValues[LF] * TICKS_TO_FT;
    }

    public double leftBackFeet()
    {
        return encoderValues[LB] * TICKS_TO_FT;
    }

    public double rightFrontFeet()
    {
        return encoderValues[RF] * TICKS_TO_FT;
    }

    public double rightBackFeet()
    {
        return encoderValues[RB] * TICKS_TO_FT;
    }

    // ----------------------------------
    // Servo Arm Method
    // ----------------------------------
    //Reference servoArmTest

    Toggler autoSwitch = new Toggler(2);

    public void setAutoArm(boolean trigger)
    {
        autoSwitch.changeState(trigger);
        switch(autoSwitch.currentState())
        {
            case 0:
                autoArm.setPosition(0.0);
                break;

            case 1:
                autoArm.setPosition(1.0);
                break;
        }
    }

    public void autoDump(double pos)
    {
        autoArm.setPosition(pos);
    }

    public void autoNeutral()
    {
        autoArm.setPosition(0.0);
    }

    double currentPosition = 0.5;

    public void setAutoArmPosition(double target, double delta)
    {
        if (target > currentPosition) {
            currentPosition = ((currentPosition + delta) > 1.0) ? 1.0 : currentPosition + delta;
        } else if (target < currentPosition) {
            currentPosition = ((currentPosition - delta) < 0.0) ? 0.0 : currentPosition - delta;
        } else if(Math.abs(target - currentPosition) < 0.01)
        {
           currentPosition = target;
        }
        autoArm.setPosition(currentPosition);
    }
}