package com.whs542.lib.sensors.imu;
//All methods verified on 2/12/16

/*
    Translated to Java from C++
    Inertial Measurement Unit Maths Library
    Copyright (C) 2013-2014  Samuel Cowen
    www.camelsoftware.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

public class Vector
{
	private double [] p_vec;
	int n;

	public Vector(int N)
    {
    	n = N;
    	p_vec = new double[n];
    }

    public Vector(double [] data)
    {
        n = data.length;
        p_vec = new double[n];
        for(int i = 0; i < n; i++)
        {
            p_vec[i] = data[i];
        }
    }

    public Vector(double a)
    {
    	n = 1;
    	p_vec = new double[n];
        p_vec[0] = a;
    }

    public Vector(double a, double b)
    {
    	n = 2;
    	p_vec = new double[n];
        p_vec[0] = a;
        p_vec[1] = b;
    }

    public Vector(double a, double b, double c)
    {
    	n = 3;
    	p_vec = new double[n];
        p_vec[0] = a;
        p_vec[1] = b;
        p_vec[2] = c;
    }

    public Vector(double a, double b, double c, double d)
    {
  		n = 4;
    	p_vec = new double[n];
        p_vec[0] = a;
        p_vec[1] = b;
        p_vec[2] = c;
        p_vec[3] = d;
    }

    public Vector(Vector v)
    {
    	n = v.n;
    	p_vec = new double[n];
        for (int x = 0; x < n; x++)
        {
            p_vec[x] = v.p_vec[x];
        }
    }

    public int getDim()
    {
    	return n;
    }

    public double getMag()
    {
        double quadraticSum = 0;
        for(int i = 0; i < n; i++)
        {
        	quadraticSum += Math.pow(p_vec[i], 2);
        }
        //Checking if the double is NaN
        if(quadraticSum != quadraticSum)
        {
        	return 0;
        }
        //Avoid square rooting if necessary
        else if(Math.abs(quadraticSum - 1) >= 0.000001)
        {
            return Math.sqrt(quadraticSum);
        }
        else
        {
        	return 1.0;
        }
    }

    public void normalizeVector()
    {
        double mag = this.getMag();
        //Check if unit vector? I assume
        if(Math.abs(mag - 1) <= 0.0001)
        {
            return;
        }

        for(int i = 0; i < n; i++)
        {
            p_vec[i] = p_vec[i]/mag;
        }
    }

    //Might throw in something that turns it to zero when it's close enough
    //But I don't want to zero out just because their angles are close to orthogonal
    public double dotWith(Vector v)
    {
    	double partwiseSum = 0;
    	for(int i = 0; i < n; i++)
    	{
    		partwiseSum = partwiseSum + this.p_vec[i] * v.p_vec[i];
    	}
    	return partwiseSum;
    }
    
    public Vector crossWith(Vector v)
    {
        Vector outputVector = new Vector(3);
        if(this.n != 3 || v.n != 3)
        {
        	return outputVector;
        }
        outputVector.p_vec[0] = (this.p_vec[1] * v.p_vec[2]) - (this.p_vec[2] * v.p_vec[1]);
        outputVector.p_vec[1] = (this.p_vec[2] * v.p_vec[0]) - (this.p_vec[0] * v.p_vec[2]);
        outputVector.p_vec[2] = (this.p_vec[0] * v.p_vec[1]) - (this.p_vec[1] * v.p_vec[0]);
        return outputVector;
    }

    public Vector scaleBy(double scalar)
    {
        Vector outputVector = new Vector(n);
        for(int i = 0; i < n; i++)
        {
            outputVector.p_vec[i] = p_vec[i] * scalar;
        }
        return outputVector;
    }

    public Vector invert()
    {
        Vector outputVector = new Vector(n);
        for(int i = 0; i < n; i++)
        {
            outputVector.p_vec[i] = -p_vec[i];
        }
        return outputVector;
    }

    public void setEqualTo(Vector v)
    {
    	for (int x = 0; x < n; x++ )
    	{
            p_vec[x] = v.p_vec[x];
    	}
    }

    //entries start from 1
    public double getEntry(int index)
    {
    	return p_vec[index];
    }

    public void setEntry(int index, double value)
    {
        p_vec[index] = value;
    }

    public Vector add(Vector v)
    {
    	Vector outputVector = new Vector(n);
    	for(int i = 0; i < n; i++)
    	{
    		outputVector.p_vec[i] = p_vec[i] + v.p_vec[i];
    	}
    	return outputVector;
    }

    public Vector subtract(Vector v)
    {
		Vector outputVector = new Vector(n);
    	for(int i = 0; i < n; i++)
    	{
    		outputVector.p_vec[i] = p_vec[i] - v.p_vec[i];
    	}
    	return outputVector;
    }

    public Vector divideByFactorOf(double scalar)
    {
        Vector outputVector = new Vector(n);
        for(int i = 0; i < n; i++)
        {
            outputVector.p_vec[i] = p_vec[i] / scalar;
        }
        return outputVector;
    }

    public void toDegrees()
    {
        for(int i = 0; i < n; i++)
        {
            p_vec[i] *= 57.2957795131; //180/pi
        }
    }

    public void toRadians()
    {
        for(int i = 0; i < n; i++)
        {
            p_vec[i] *= 0.01745329251;  //pi/180
        }
    }

    public double x() { return p_vec[0]; }
    public double y() { return p_vec[1]; }
    public double z() { return p_vec[2]; }
}