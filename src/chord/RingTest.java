package chord;

public class RingTest { 
	public static void main(String[] args) {
		//Ring ring = new Ring(5, 3, 2);
		Ring ring = new Ring(100, 20, 5);
		//System.out.println(ring.toString(true));
		float successRate = ring.runQueries(1000);
		System.out.println("percentage of successful queries : " + successRate + "%");
		//ring.printCulpritNodes();
		CulpritAnalysisCount count = ring.getCulpritAnalysis();
		System.out.println("correctly detected : " + count.getRightlyDetectedCulprits());
		System.out.println("wrongly detected : " + count.getWronglyDetectedHonests());
		ring.removeFaultyNodes();
		float repairedSuccessRate = ring.runQueries(1000);
		System.out.println("percentage of successful queries : " + repairedSuccessRate + "%");
		
	}
}
