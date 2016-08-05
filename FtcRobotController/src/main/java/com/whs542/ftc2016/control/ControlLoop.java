package com.whs542.ftc2016.control;

public class ControlLoop
{
	/*public Matrix a;
	public Matrix b;
	public Matrix c;

	public Matrix l;
	public Matrix k;

	public Matrix x_hat;

	public ControlLoop(Matrix aIn, Matrix bIn, Matrix cIn, Matrix kIn, Matrix lIn)
	{
		a = new Matrix(aIn);
		b = new Matrix(bIn);
		c = new Matrix(cIn);

		k = new Matrix(kIn);
		l = new Matrix(lIn);

		x_hat = new Matrix(a.M, 1);
	}

	public Matrix nextInput(Matrix y, Matrix u, Matrix r)
	{
		x_hat = ((a.times(x_hat)).plus(b.times(u))).plus(l.times(y.minus(c.times(x_hat))));
		return r.minus(k.times(x_hat));
	}

	// Calculate x_hat
		// x_hat = A * x_hat + B * u + L * (y-C*x_hat)
	// Generate u from x_hat
		// r - K * x_hat*/
}

