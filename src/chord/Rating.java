package chord;

public class Rating {
	private float value;
	public static final int numberOfTestsByInformer = 8;
	public static final float amountForComplainingRight = .02f;
	public static final float amountForComplainingWrong = .04f;
	public static final float amountForDroppingPackets = .1f;
	public static final float informerPassCritaria = .5f;
	
	Rating(){
		value = .5f;
	}
	
	void increase(float amount)	{
		if(value+amount>1.0)
			value=1.0f;
		else 
			value +=amount;
	}
	
	void decrease(float amount)	{
		if(value-amount<0)
			value=0;
		else
			value-=amount;
	}
	
	public enum  Catagory	{
		CULPRIT, HONEST;
	}
	
	public Catagory getCatagory()	{
		if(value<.2)
			return Catagory.CULPRIT;
		else
			return Catagory.HONEST;
	}
}
