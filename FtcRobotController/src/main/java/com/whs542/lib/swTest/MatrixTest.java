package com.whs542.lib.swTest;

import java.util.Arrays;
import com.whs542.lib.sensors.imu.*;

import com.whs542.lib.sensors.imu.Matrix;
import com.whs542.lib.sensors.imu.Vector;

public class MatrixTest
{
	//I suppose we can use the get entry or get array methods
	public static void printMatrix(Matrix m)
	{
		double [][] dataMatrix = new double[m.rows][m.columns];
		for(int i = 0; i < m.rows; i++)
		{
			for(int j = 0; j < m.columns; j++)
			{
				dataMatrix[i][j] = m.getEntry(i,j);
			}
			System.out.println(Arrays.toString(dataMatrix[i]));
		}
	}

	public static void printVector(Vector v)
	{
		for(int i = 0; i < v.getDim(); i++)
		{
			if(i == 0)
			{
				System.out.print("[" + v.getEntry(i) + ", ");
			}
			else if(i == v.getDim() - 1)
			{
				System.out.print(v.getEntry(i) + "]");
			}
			else
			{
				System.out.print(v.getEntry(i) + ", ");
			}
		}
		System.out.println("");
	}

	public static void main( String[] args )
	{
		System.out.println("Testing printMatrix method");
		Matrix zeroes = new Matrix(3,3);
		printMatrix(zeroes);
		System.out.println("");

		System.out.println("Testing Matrix Constructor by array pass");
		double [][] phonePad = {{1,2,3},{4,5,6},{7,8,9}};
		Matrix phonePadMatrix = new Matrix(phonePad);
		printMatrix(phonePadMatrix);
		System.out.println("");

		System.out.println("Testing Matrix Copy Constructor and setEntry method");
		Matrix phonePadWithZero = new Matrix(phonePadMatrix);
		phonePadWithZero.setEntry(1,1,0);
		printMatrix(phonePadWithZero);
		System.out.println("");

		System.out.println("Testing Matrix SetEqualTo Method and testing subtract method");
		phonePadWithZero.setEqualTo(phonePadMatrix);
		printMatrix(phonePadMatrix.subtract(phonePadWithZero));
		System.out.println("");

		System.out.println("Testing Row To Vector");
		printVector(phonePadWithZero.vectorFromRow(0));
		System.out.println("");

		System.out.println("Testing Columns To Vector");
		printVector(phonePadWithZero.vectorFromColumn(0));
		System.out.println("");

		System.out.println("Testing Row and Column into Matrix");
		phonePadWithZero.rowFromVector(phonePadWithZero.vectorFromRow(0),1);
		printMatrix(phonePadWithZero);
		System.out.println("");
		phonePadWithZero.columnFromVector(phonePadWithZero.vectorFromColumn(1),0);
		printMatrix(phonePadWithZero);
		System.out.println("");

		System.out.println("setRow and setColumn tests");
		double[] set = {-1,-2,-5};
		phonePadWithZero.setRow(0, set);
		printMatrix(phonePadWithZero);
		System.out.println("");
		double[] set2 = {-5.0, 3.452, Math.PI};
		phonePadWithZero.setColumn(2, set2);
		printMatrix(phonePadWithZero);
		System.out.println("");

		System.out.println("Getting Rows and Columns as arrays");
		System.out.println(Arrays.toString(phonePadWithZero.getRowAsArray(1)));
		System.out.println(Arrays.toString(phonePadWithZero.getColumnAsArray(1)));

		System.out.println("Adding Matrices");
		double [][] check = {{1,0,1},{0,1,0},{1,0,1}};
		double [][] checkComp = {{0,1,0},{1,0,1},{0,1,0}};
		Matrix checkM = new Matrix(check);
		Matrix checkCompM = new Matrix(checkComp);
		printMatrix(checkM);
		System.out.println("");
		printMatrix(checkCompM);
		System.out.println("");
		printMatrix(checkM.add(checkCompM));
		System.out.println("");

		System.out.println("Scaling Matrix");
		printMatrix(checkM.add(checkCompM).scaleBy(-100.23));
		System.out.println("");

		System.out.println("Multiply Matrix");
		double [][] a = {{2,3,4},{-4,-3,-3}};
		double [][] b = {{1},{0},{2}};
		Matrix aM = new Matrix(a);
		Matrix bM= new Matrix(b);
		printMatrix(aM.multiplyBy(bM));
		System.out.println("");

		System.out.println("Transpose Matrix");
		printMatrix(aM);
		System.out.println("");
		printMatrix(aM.transpose());
		System.out.println("");		
		printMatrix(aM.transpose().transpose());
		System.out.println("");

		System.out.println("Minor Matrix");
		double [][] fbf = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		Matrix fbfM = new Matrix(fbf);
		printMatrix(fbfM);
		printMatrix(fbfM.minorMatrix(1,2));
		System.out.println("");

		//Checked against wolfram alpha
		System.out.println("Matrix Determinant");
		double [][] det = {{10,2}, {-1,3}};
		Matrix detM = new Matrix(det);
		System.out.println(detM.determinant());

		//Checked against wolfram alpha
		System.out.println("Matrix Inverse");
		printMatrix(detM.invert().scaleBy(32.0));
	}

}