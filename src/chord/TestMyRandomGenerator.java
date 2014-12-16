package chord;

public class TestMyRandomGenerator {

	public static void main(String[] args) {
		MyRandomGenerator rand = MyRandomGenerator.getInstance();
		for(int i=0;i<10;i++)	{
			System.out.println(rand.nextKey());
		}
	}

}
