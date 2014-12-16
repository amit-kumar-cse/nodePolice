package chord;

import java.util.Random;

public class MyRandomGenerator {
	private static MyRandomGenerator uniqueInstance;
	Random numGen;
	
	private MyRandomGenerator()	{
		numGen = new Random();
		//this.numGen = new Random(10);
	}
	
	public static MyRandomGenerator getInstance()	{
		if(uniqueInstance==null)
			uniqueInstance = new MyRandomGenerator();
		return uniqueInstance;
	}
	
	long nextKey()	{
		return Math.abs(this.numGen.nextLong())%(1l<<Ring.noOfBits);
	}
	
	int nextPositiveInt()	{
		return Math.abs(this.numGen.nextInt());
	}
}