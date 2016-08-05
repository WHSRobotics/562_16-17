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

public class Matrix
{
    int rows;
    int columns;
    double [][] data;

    public Matrix(int M)
    {
        rows = M;
        columns = M;
        data = new double[rows][columns];
    }

    public Matrix(int M, int N)
    {
        rows = M;
        columns = N;
        data = new double[rows][columns];
    }

    public Matrix(Matrix m)
    {
        rows = m.rows;
        columns = m.columns;
        data = new double[rows][columns];
        for (int i = 0; i < rows; i++ )
        {
            for(int j = 0; j < columns; j++)
            {
                data[i][j] = m.data[i][j];
            }
        }
    }

    // create matrix based on 2d array
    public Matrix(double[][] dataMatrix)
    {
        rows = dataMatrix.length;
        columns = dataMatrix[0].length;
        data = new double[rows][columns];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
               data[i][j] = dataMatrix[i][j];
            }
        }
    }

    public void setEqualTo(Matrix m)
    {
        if (rows != m.rows || columns != m.columns) throw new RuntimeException("Illegal matrix dimensions.");

        for (int i = 0; i < rows; i++ )
        {
            for(int j = 0; j < columns; j++)
            {
                data[i][j] = m.data[i][j];
            }
        }
    }

    public Vector vectorFromRow(int rowNumber)
    {
        Vector outputVector = new Vector(columns);
        for(int i = 0; i < columns; i++)
        {
            outputVector.setEntry(i, data[rowNumber][i]);
        }
        return outputVector;
    }

    public Vector vectorFromColumn(int columnNumber)
    {
        Vector outputVector = new Vector(rows);
        for(int i = 0; i < rows; i++)
        {
            outputVector.setEntry(i, data[i][columnNumber]);
        }
        return outputVector;
    }

    public void rowFromVector(Vector v, int rowNumber)
    {
        if (columns != v.n) throw new RuntimeException("Illegal vector dimensions.");

        for(int i = 0; i < columns; i++)
        {
            data[rowNumber][i] = v.getEntry(i);
        }
    }

    public void columnFromVector(Vector v, int columnNumber)
    {
        if (rows != v.n) throw new RuntimeException("Illegal vector dimensions.");

        for(int i = 0; i < rows; i++)
        {
            data[i][columnNumber] = v.getEntry(i);
        }
    }

    public double getEntry(int row, int column)
    {
        return data[row][column];
    }

    public void setEntry(int row, int column, double value)
    {
        data[row][column] = value;
    }

    public void setRow(int row, double[] rowData)
    {
        if(rowData.length != columns) throw new RuntimeException("Illegal Array Length");
        data[row]=rowData;
    }

    public void setColumn(int column, double[] columnData)
    {
        if(columnData.length != rows) throw new RuntimeException("Illegal Array Length");
        for(int i = 0; i < rows; i++)
        {
            data[i][column] = columnData[i];
        }
    }

    public double[] getRowAsArray(int row)
    {
        double[] outputArray = new double[columns];
        outputArray = data[row];
        return outputArray;
    }

    public double[] getColumnAsArray(int column)
    {
        double[] outputArray = new double[rows];
        for(int i = 0; i < rows; i++)
        {
            outputArray[i] = data[i][column];
        }
        return outputArray;
    }

    public Matrix add(Matrix m)
    {
        if (rows != m.rows || columns != m.columns) throw new RuntimeException("Illegal matrix dimensions.");

        Matrix outputMatrix = new Matrix(rows, columns);
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                outputMatrix.data[i][j] = data[i][j] + m.data[i][j];
            }
        }
        return outputMatrix;
    }

    public Matrix subtract(Matrix m)
    {
        if (rows != m.rows || columns != m.columns) throw new RuntimeException("Illegal matrix dimensions.");

        Matrix outputMatrix = new Matrix(rows, columns);
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                outputMatrix.data[i][j] = data[i][j] - m.data[i][j];
            }
        }
        return outputMatrix;
    }

    public Matrix scaleBy(double scalar)
    {
        Matrix outputMatrix = new Matrix(rows, columns);
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                outputMatrix.data[i][j] = data[i][j] * scalar;
            }
        }
        return outputMatrix;
    }

    public Matrix multiplyBy(Matrix m)
    {
        if(columns != m.rows) throw new RuntimeException("Illegal Matrix Dimensions");
        Matrix outputMatrix = new Matrix(rows, m.columns);
        for(int i = 0; i < outputMatrix.rows; i++)
        {
            for(int j = 0; j < outputMatrix.columns; j++)
            {
                Vector row = vectorFromRow(i);
                Vector column = m.vectorFromColumn(j);
                outputMatrix.data[i][j] = row.dotWith(column);
            }
        }
        return outputMatrix;
    }

    public Matrix transpose()
    {
        Matrix outputMatrix = new Matrix(columns,rows);
        for(int i = 0; i < outputMatrix.rows; i++)
        {
            for(int j = 0; j < outputMatrix.columns; j++)
            {
                outputMatrix.data[i][j] = data[j][i];
            }
        }
        return outputMatrix;
    }

    Matrix minorMatrix(int row, int column)
    {
        if(rows != columns) throw new RuntimeException("Non-square matrix");
        int columnCount = 0;
        int rowCount = 0;
        Matrix outputMatrix = new Matrix(rows - 1);
        for(int i = 0; i < rows; i++)
        {
            if(i != row)
            {
                for(int j = 0; j < columns; j++)
                {
                    if(j != column)
                    {
                        outputMatrix.data[rowCount][columnCount] = data[i][j];
                        columnCount ++;
                    }
                }
                rowCount ++;
                //The code didn't have the column reset, would probably have led to array error?
                //Don't know, because in C++ they just had a long array thing as a matrix
                columnCount = 0;
            }
        }
        return outputMatrix;
    }

    double determinant()
    {
        if(rows != columns) throw new RuntimeException("Non-square matrix");
        if(rows == 1)
        {
            return data[0][0];
        }

        double outputDeterminant = 0.0;
        for(int i = 0; i < rows; i++)
        {
            Matrix minor = minorMatrix(0, i);
            outputDeterminant += (i%2==1?-1.0:1.0) * data[0][i] * minor.determinant();
        }
        return outputDeterminant;
    }

    Matrix invert()
    {
        if(rows != columns) throw new RuntimeException("Non-square matrix");
        Matrix outputMatrix = new Matrix(rows);
        double det = determinant();

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                Matrix minor = minorMatrix(j, i);
                //The original code had the minordet * det instead of minordet/det
                outputMatrix.data[i][j]=minor.determinant()/det;
                if( (i+j)%2 == 1)
                {
                    outputMatrix.data[i][j] = -outputMatrix.data[i][j];
                }
            }
        }
        return outputMatrix;
    }

}