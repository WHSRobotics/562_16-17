package com.whs542.lib.swTest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import com.whs542.ftc2016.control.ControlLoop;
import com.whs542.lib.*;

import com.qualcomm.robotcore.hardware.VoltageSensor;

public class posControl extends OpMode
{
	double Nbar = 13.3138;
	double TICKS_TO_RAD = 2.0*Math.PI/1120.0;
	double TWO_PI = 2.0*Math.PI;

	double [][] Adata =
			{
			{1, 0.009271, 0.00001395},
			{0, 0.8577, 0.001301},
			{0, -0.009117, -0.00001383},
	};

	double [][] Bdata = {
			{0.001002},
			{0.1967},
			{1.001}
	};

	double [][] Cdata = {
			{1.0,0.0,0.0}
	};

	double [][] Kdata = {
			{13.3138, 0.1697, -0.97}
	};

	double[][] Ldata = {
			{-14039.0},
			{197450000.0},
			{-130200000000.0}
	};

	/*Matrix A = new Matrix(Adata);
	Matrix B = new Matrix(Bdata);
	Matrix C = new Matrix(Cdata);
	Matrix K = new Matrix(Kdata);
	Matrix L = new Matrix(Ldata);

	Matrix y = new Matrix(1,1);
	Matrix r = new Matrix(1,1);
	Matrix u = new Matrix(1,1);*/

	double power = 0.0;
	DcMotor office;
	VoltageSensor voltSense;

	Toggler input;

	ControlLoop position;

	public void init()
	{
		office = hardwareMap.dcMotor.get("office");
		office.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
		voltSense = hardwareMap.voltageSensor.get("motorc");
		input = new Toggler(9, 4);

		//position = new ControlLoop(A,B,C,K,L);
	}

	public void start()
	{
		time = 0.0;
	}

	public void loop()
	{
		input.changeState(gamepad1.dpad_up,gamepad1.dpad_down);

		/*y.data[0][0] = (office.getCurrentPosition() * TICKS_TO_RAD);
		r.data[0][0] = input.currentState() * TWO_PI/8.0 * Nbar;
		u = position.nextInput(y, u, r);
		power = u.data[0][0] / voltSense.getVoltage() * 7.0/9.0;
		power = clamp(power);
		office.setPower(power);
		telemetry.addData("u", u.data[0][0] / voltSense.getVoltage() * 7.0/9.0);
		telemetry.addData("power", power);
		telemetry.addData("input", input.currentState() + " " + input.currentState() * TWO_PI/8.0);*/
	}

	public double clamp(double input)
	{
		if(input > 1.0)
			return 1.0;
		else if(input < -1.0)
			return 1.0;
		else
			return input;
	}
}