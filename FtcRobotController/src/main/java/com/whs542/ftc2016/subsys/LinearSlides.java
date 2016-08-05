package com.whs542.ftc2016.subsys;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.whs542.lib.Alliance;
import com.whs542.lib.Toggler;
import com.whs542.lib.sensors.CurrentACS711EX;
import com.whs542.lib.sensors.PIDController;
//
// Linear Slides Subsystem Class
//

//TODO: Implement Angling and Extension methods

public class LinearSlides
{
    // ----------------------------------
    // Linear Slide Variables
    // ----------------------------------
    // -Hardware object reference variables for motors and servos
    // -Double variables for servo positions

    private Alliance color;

    private Servo rampServo;

    private Toggler rampSwitch = new Toggler(2);

    private DcMotor anglingMotor;
    private DcMotor intakeMotor;

	public DcMotor leftExtensionMotor;
	public DcMotor rightExtensionMotor;

    private double output;

    public PIDController pidControl;


    //Pulley Circumference = 3pi in/2 rot

    private static final double TICK_TO_IN = 3.0*Math.PI/2240.0;

	public LinearSlides(HardwareMap slideMap, Alliance side)
	{
        anglingMotor = slideMap.dcMotor.get("ls_am");
        intakeMotor = slideMap.dcMotor.get("intake_motor");

        rampServo = slideMap.servo.get("ls_ramp");

        leftExtensionMotor = slideMap.dcMotor.get("ls_le");
        rightExtensionMotor = slideMap.dcMotor.get("ls_re");

        leftExtensionMotor.setDirection(DcMotor.Direction.REVERSE);

        //anglingMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftExtensionMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightExtensionMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        pidControl = new PIDController(0.00025, 0.0, 0.0, 0.6);

        color = side;
    }

    public double getAngle()
    {
        //Angle is a function of String length
        //For one rotation of the pulley, there is 4 rotation of the motor, which is a AM-NV20 with 560 pulses per rotation
        return Math.asin((Math.pow((Math.PI * anglingMotor.getCurrentPosition()/1120.0 - 0.01615593741),2.0) - 33.6177)/-31.8308393386)+0.577018458248;

    }

    public void setAngle(boolean up, boolean down)
    {
        if(up)
        {
            anglingMotor.setPower(-1.0);
        }
        else if(down)
        {
            anglingMotor.setPower(1.0);
        }
        else
        {
            anglingMotor.setPower(0.0);
        }
    }

    public void setRamp(boolean trigger)
    {
        rampSwitch.changeState(trigger);
        switch(rampSwitch.currentState())
        {
            case 0:
                //Ramp Up
                rampUp();
                break;

            case 1:
                //Ramp Down
                rampDown();
                break;
        }
    }

    public void rampDown()
    {
        switch(color)
        {
            case RED:
                rampServo.setPosition(0.0);
                break;

            case BLUE:
                rampServo.setPosition(1.0);
                break;
        }
    }

    public void rampUp()
    {
        switch(color)
        {
            case RED:
                rampServo.setPosition(1.0);
                break;

            case BLUE:
                rampServo.setPosition(0.0);
                break;
        }
    }

    //Set extension speed
    public void setTransmissionPower(boolean up, boolean down)
    {
        if(up)
        {
            leftExtensionMotor.setPower(-1.0);
            rightExtensionMotor.setPower(-1.0);
        }
        else if(down)
        {
            leftExtensionMotor.setPower(1.0);
            rightExtensionMotor.setPower(1.0);
        }
        else
        {
            leftExtensionMotor.setPower(0.0);
            rightExtensionMotor.setPower(0.0);
        }
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
    public void testLinearSlide(double power, boolean extend, boolean retract)
    {
        output = pidControl.update(leftExtensionMotor.getCurrentPosition(), rightExtensionMotor.getCurrentPosition());
        if(retract)
        {
            leftExtensionMotor.setPower(clamp(power - output));
            rightExtensionMotor.setPower(clamp(power));
        }
        else if(extend)
        {
            leftExtensionMotor.setPower(clamp(-power - output));
            rightExtensionMotor.setPower(clamp(-power));
        }
        else
        {
            leftExtensionMotor.setPower(0.0);
            rightExtensionMotor.setPower(0.0);
        }
    }

    public boolean fullyExtended(double benchmark)
    {
        return TICK_TO_IN * (leftExtensionMotor.getCurrentPosition() + rightExtensionMotor.getCurrentPosition())/2.0 > benchmark;
    }

    // ----------------------------------
    // Conveyor Methods
    // ----------------------------------

    public void setIntake(boolean up, boolean down)
    {
        if(up)
        {
            intakeMotor.setPower(7.0/9.0);
        }
        else if(down)
        {
            intakeMotor.setPower(-7.0/9.0);
        }
        else
        {
            intakeMotor.setPower(0.0);
        }
    }

}