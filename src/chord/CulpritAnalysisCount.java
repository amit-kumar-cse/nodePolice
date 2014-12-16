package chord;

public class CulpritAnalysisCount {
	private int rightlyDetectedCulprits;
	private int wronglyDetectedHonests;
	
	public CulpritAnalysisCount(int rightlyDetectedCulprits, int wronglyDetectedHonests) {
		assert(rightlyDetectedCulprits>=0);
		assert(wronglyDetectedHonests>=0);
		this.rightlyDetectedCulprits = rightlyDetectedCulprits;
		this.wronglyDetectedHonests = wronglyDetectedHonests;
		// TODO Auto-generated constructor stub
	}

	public int getRightlyDetectedCulprits() {
		return rightlyDetectedCulprits;
	}

	public int getWronglyDetectedHonests() {
		return wronglyDetectedHonests;
	}


}
