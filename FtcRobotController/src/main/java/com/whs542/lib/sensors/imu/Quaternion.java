package com.whs542.lib.sensors.imu;

/*
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

public class Quaternion
{
	//TODO: Ensure unit quaternions
	private double w;
	private double x;
	private double y;
	private double z;

	//Tested
	public Quaternion()
	{
		w = 1.0;
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	//Tested
	public Quaternion(double inputW, double inputX, double inputY, double inputZ)
	{
		w = inputW;
		x = inputX;
		y = inputY;
		z = inputZ;
	}

	//tested
	public void setW(double inputW)
	{
		w = inputW;
	}

	public void setX(double inputX)
	{
		x = inputX;
	}

	public void setY(double inputY)
	{
		y = inputY;
	}

	public void setZ(double inputZ)
	{
		z = inputZ;
	}

	//tested
	public double getW()
	{
		return w;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	//Tested
	public double getMag()
	{
		return Math.sqrt(Math.pow(w,2) + Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
	}

	//Tested
	public void normalize()
	{
		double mag = getMag();
		w = w/mag;
		x = x/mag;
		y = y/mag;
		z = z/mag;
	}

	//Tested
	public Quaternion conjugate()
	{
		Quaternion q = new Quaternion();
		q.w = w;
		q.x = -x;
		q.y = -y;
		q.z = -z;
		return q;
	}

	//Tested
	//We want a result of a unit quaternion
	//We have an axis angle representation
	public void fromAxisAngle(Vector axisAngle)
	{
		if(axisAngle.getDim() != 3) throw new RuntimeException("Illegal vector length");

		double theta = axisAngle.getMag();
		double axisToQuaternion = Math.sin(theta/2.0)/theta;

		w = Math.cos(theta/2.0);
		x = axisAngle.x() * axisToQuaternion;
		y = axisAngle.y() * axisToQuaternion;
		z = axisAngle.z() * axisToQuaternion;
	}


	//Tested, but need more test cases
	public void fromMatrix(Matrix m)
	{
		if(m.rows != 3 || m.columns != 3) throw new RuntimeException("Illegal Matrix Dimensions");
		double trace = m.getEntry(0,0) + m.getEntry(1,1) + m.getEntry(2,2);

		double S = 0.0;
		if (trace > 0)
		{
			S = Math.sqrt(trace+1.0) * 2;
			w = 0.25 * S;
			x = (m.getEntry(2, 1) - m.getEntry(1, 2)) / S;
			y = (m.getEntry(0, 2) - m.getEntry(2, 0)) / S;
			z = (m.getEntry(1, 0) - m.getEntry(0, 1)) / S;
		}
		else if ((m.getEntry(0, 0) < m.getEntry(1, 1))&&(m.getEntry(0, 0) < m.getEntry(2, 2)))
		{
			S = Math.sqrt(1.0 + m.getEntry(0, 0) - m.getEntry(1, 1) - m.getEntry(2, 2)) * 2;
			w = (m.getEntry(2, 1) - m.getEntry(1, 2)) / S;
			x = 0.25 * S;
			y = (m.getEntry(0, 1) + m.getEntry(1, 0)) / S;
			z = (m.getEntry(0, 2) + m.getEntry(2, 0)) / S;
		}
		else if (m.getEntry(1, 1) < m.getEntry(2, 2))
		{
			S = Math.sqrt(1.0 + m.getEntry(1, 1) - m.getEntry(0, 0) - m.getEntry(2, 2)) * 2;
			w = (m.getEntry(0, 2) - m.getEntry(2, 0)) / S;
			x = (m.getEntry(0, 1) + m.getEntry(1, 0)) / S;
			y = 0.25 * S;
			z = (m.getEntry(1, 2) + m.getEntry(2, 1)) / S;
		}
		else
		{
			S = Math.sqrt(1.0 + m.getEntry(2, 2) - m.getEntry(0, 0) - m.getEntry(1, 1)) * 2;
			w = (m.getEntry(1, 0) - m.getEntry(0, 1)) / S;
			x = (m.getEntry(0, 2) + m.getEntry(2, 0)) / S;
			y = (m.getEntry(1, 2) + m.getEntry(2, 1)) / S;
			z = 0.25 * S;
		}
	}

	//Tested
	public Vector toAxisAngle()
	{
		Vector axisAngleVector = new Vector(3);
		if(Math.abs((Math.abs(w) - 1)) < 0.00001)
		{
			return axisAngleVector;
		}

		double quaternionToAxis = 2.0*Math.acos(w)/Math.sqrt(1.0-Math.pow(w,2));

		axisAngleVector.setEntry(0, quaternionToAxis*x);
		axisAngleVector.setEntry(1, quaternionToAxis*y);
		axisAngleVector.setEntry(2, quaternionToAxis*z);
		return axisAngleVector;
	}

	//Tested but need more test cases
	public Matrix toMatrix()
	{
		Matrix outputMatrix = new Matrix(3,3);
		outputMatrix.setEntry(0, 0, 1.0-2.0*Math.pow(y,2.0)-2.0*Math.pow(z,2.0) );
		outputMatrix.setEntry(0, 1, 2.0*x*y-2.0*w*z );
		outputMatrix.setEntry(0, 2, 2.0*x*z+2.0*w*y );

		outputMatrix.setEntry(1, 0, 2.0*x*y+2*w*z );
		outputMatrix.setEntry(1, 1, 1.0-2.0*Math.pow(x,2.0)-2*Math.pow(z,2.0) );
		outputMatrix.setEntry(1, 2, 2.0*y*z-2.0*w*x );

		outputMatrix.setEntry(2, 0, 2.0*x*z-2.0*w*y );
		outputMatrix.setEntry(2, 1, 2.0*y*z+2.0*w*x );
		outputMatrix.setEntry(2, 2, 1.0-2.0*Math.pow(x,2.0)-2*Math.pow(y,2.0) );
		return outputMatrix;
	}

	// Returns euler angles that represent the quaternion.  Angles are
	// returned in rotation order and right-handed about the specified
	// axes:
	//
	//   v[0] is applied 1st about z (ie, roll)
	//   v[1] is applied 2nd about y (ie, pitch)
	//   v[2] is applied 3rd about x (ie, yaw)
	//
	// Note that this means result.x() is not a rotation about x;
	// similarly for result.z().
	//

	//Tested, more test cases
	public Vector toEuler()
	{
		Vector outputVector = new Vector(3);
		double wsq = Math.pow(w, 2.0);
		double xsq = Math.pow(x, 2.0);
		double ysq = Math.pow(y, 2.0);
		double zsq = Math.pow(z, 2.0);

		outputVector.setEntry(0, Math.atan2(2.0 * ( x*y + z*w ), ( xsq - ysq - zsq + wsq ) ) );
		outputVector.setEntry(1, Math.asin(-2.0 * ( x*z - y*w )/( xsq + ysq + zsq + wsq ) ) );
		outputVector.setEntry(2, Math.atan2(2.0 * ( y*z + x*w ), (-xsq - ysq + zsq + wsq) ) );

		return outputVector;
	}

	//Tested, but don't understand it's significance
	//From w = 0 to orientation defined by this q
	public Vector toAngularVelocity(double dt)
	{
		Vector outputVector = new Vector(3);
		Quaternion one = new Quaternion();
		Quaternion delta = one.subtract(this);
		Quaternion angVel = (delta.scaleBy(2.0/dt)).multiplyBy(one);

		outputVector.setEntry(0, angVel.x);
		outputVector.setEntry(1, angVel.y);
		outputVector.setEntry(2, angVel.z);
		return outputVector;
	}

	//tested
	public Vector rotateVector(Vector v)
	{
		Vector qv = new Vector(x,y,z);
		Vector t = (qv.crossWith(v)).scaleBy(2.0);
		return (v.add(t.scaleBy(w))).add(qv.crossWith(t));
	}

	//Tested, more samples needed
	public Quaternion multiplyBy(Quaternion q)
	{

		// x | 1 | i | j | k |
		// 1 | 1   i   j   k
		// i | i  -1   k  -j
		// j | j  -k  -1   i
		// k | k   j  -i  -1
		Quaternion outputQuaternion = new Quaternion();
		outputQuaternion.w = w*q.w - x*q.x - y*q.y - z*q.z;
		outputQuaternion.x = w*q.x + x*q.w + y*q.z - z*q.y;
		outputQuaternion.y = w*q.y - x*q.z + y*q.w + z*q.x;
		outputQuaternion.z = w*q.z + x*q.y - y*q.x + z*q.w;
		return outputQuaternion;
	}

	//tested
	public Quaternion add(Quaternion q)
	{
		Quaternion outputQuaternion = new Quaternion();
		outputQuaternion.w = w+q.w;
		outputQuaternion.x = x+q.x;
		outputQuaternion.y = y+q.y;
		outputQuaternion.z = z+q.z;
		return outputQuaternion;
	}

	//Tested
	Quaternion subtract(Quaternion q)
	{
		Quaternion outputQuaternion = new Quaternion();
		outputQuaternion.w = w-q.w;
		outputQuaternion.x = x-q.x;
		outputQuaternion.y = y-q.y;
		outputQuaternion.z = z-q.z;
		return outputQuaternion;
	}

	//Tested
	public Quaternion scaleDownBy(double scalar)
	{
		Quaternion outputQuaternion = new Quaternion();
		outputQuaternion.w = w/scalar;
		outputQuaternion.x = x/scalar;
		outputQuaternion.y = y/scalar;
		outputQuaternion.z = z/scalar;
		return outputQuaternion;
	}

	//Tested
	public Quaternion scaleBy(double scalar)
	{
		Quaternion outputQuaternion = new Quaternion();
		outputQuaternion.w = w*scalar;
		outputQuaternion.x = x*scalar;
		outputQuaternion.y = y*scalar;
		outputQuaternion.z = z*scalar;
		return outputQuaternion;
	}


}