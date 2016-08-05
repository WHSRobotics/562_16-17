package com.whs542.lib.swTest;

public class VectorTest
{
	/*private static void printVector(Vector v)
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
		System.out.println( "Initiating Vector Constructor Tests" );
		Vector testVector1 = new Vector(7);
		Vector testVector2 = new Vector(1, 1.0);
		Vector testVector3 = new Vector(2, 2.0, 3.0);
		Vector testVector4 = new Vector(3, 4.0, 5.0, 6.0);
		Vector testVector5 = new Vector(4, 7.0, 8.0, 9.0, 10.0);
		Vector testVector6 = new Vector(testVector5);
		printVector(testVector1);
		printVector(testVector2);
		printVector(testVector3);
		printVector(testVector4);
		printVector(testVector5);
		printVector(testVector6);
		System.out.println( "" );


		System.out.println( "Normalizing vector" );
		printVector(testVector3);
		testVector3.normalizeVector();
		printVector(testVector3);
		System.out.println("Magnitude of Normalized vector is " + testVector3.getMag());
		Vector testVector7 = new Vector(2, 3, 4);
		printVector(testVector7);
		System.out.println("Magnitude of test vector 7 is " + testVector7.getMag());
		System.out.println("");

		System.out.println("Dotting Two orthogonal vectors");
		Vector testVector8 = new Vector(2, 2.0/Math.sqrt(5), 1.0/Math.sqrt(5));
		Vector testVector9 = new Vector(2, 2.0 * Math.sqrt(5), 1.0 * Math.sqrt(5));
		System.out.println("Dot product is " + testVector8.dotWith(testVector9));
		System.out.println("magnitude of test vector 8 is " + testVector8.getMag());
		System.out.println("magnitude of test vector 9 is " + testVector9.getMag());
		System.out.println("");

		System.out.println("Crossing Two Vectors");
		Vector basisX = new Vector(3, 1/Math.sqrt(2),1/Math.sqrt(2));
		Vector basisY = new Vector(3, 1/Math.sqrt(2), 1/Math.sqrt(2));
		printVector(basisX.crossWith(basisY));

		printVector(basisX.scaleBy(Math.sqrt(2.0)));
		printVector(basisX.invert());
		basisX.setEqualTo(testVector4);
		printVector(basisX);

		printVector(basisX.add(basisY));
		printVector(basisY.subtract(basisX));
		printVector(basisY.divideByFactorOf(Math.sqrt(2.0)));

		double[] potato = {-180.0, -135.0, -90.0, -45.0, 0.0};
		double[] rotato = {-Math.PI, -0.75 * Math.PI, -Math.PI/2.0, -Math.PI/4.0, 0.0};
		Vector degreesVector = new Vector(potato);
		Vector radiansVector = new Vector(rotato);
		radiansVector.toDegrees();
		printVector(radiansVector);
		printVector(degreesVector.subtract(radiansVector));

		System.out.println("X value is: " + degreesVector.x());
		System.out.println("Y value is: " + degreesVector.y());
		System.out.println("Z value is: " + degreesVector.z());
	}*/
}