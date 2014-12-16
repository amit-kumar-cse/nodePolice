package chord;

public class PoliceNode extends Node {

	PoliceNode[] policeNetworkFingerTable;
	Ring ring;
	PoliceNode(long nodeId) {
		super(nodeId);
		policeNetworkFingerTable = new PoliceNode[62];
		//System.out.println("police station constructed");
	}
	
	void setRing(Ring ring)	{
		this.ring = ring;
	}
	
	
	void setPoliceNetworkFinger(int i, PoliceNode p)	{
		policeNetworkFingerTable[i] = p;
	}
	
	long getPoliceNetworkFinger(int i)	{
		return policeNetworkFingerTable[i].getNodeId();
	}
	
	
	PoliceNode searchMyPoliceStation(long data, PoliceNode querySourceNode, Ring ring)	{
		
		if(data==super.getNodeId())
			return this;
		if(Utils.isInCircularRange(super.getNodeId(), data, policeNetworkFingerTable[0].getNodeId()))
			return policeNetworkFingerTable[0];
		//otherwise, forward the query to the best possible node and return the answer.
		int finger;
		for(finger = (Ring.noOfBits-1);finger>=0;finger--)	{
			if(Utils.isInCircularRange(super.getNodeId(), policeNetworkFingerTable[finger].getNodeId(), data ))
				break;
		}
		if(finger==-1)	{
			System.out.println("here is some mistake");
			System.out.println(ring.toString(false));
			System.out.println("breakpoint");
		}
		//problem happens when a polic station searches for itself. this case comes when some other node is trying to search for its police station through chord ring
		//and the query comes to the police station itself. it can be resolved by 
		//issue resolved. similer issue when a node tries to search itself may be there.
		
		return policeNetworkFingerTable[finger].searchMyPoliceStation(data, querySourceNode, ring);
		
	}

	void writeComplaint(Node Complainer, Node complained1, Node complained2)	{
		float rating1 = putNComplaints(complained1, Rating.numberOfTestsByInformer);
		float rating2 = putNComplaints(complained2, Rating.numberOfTestsByInformer);
		
		//decide whom to give credit.
		if(rating1>Rating.informerPassCritaria&&
				rating2>Rating.informerPassCritaria)	{
			//wrong complaint
			//decrease rating of complainer by some some amount
			ring.decreaseRating(Complainer, Rating.amountForComplainingWrong);
		}
		else	{
			if(rating1<Rating.informerPassCritaria)	{
				//decrease rating of complained1
				ring.decreaseRating(complained1, Rating.amountForDroppingPackets);
			}
			if(rating2<Rating.informerPassCritaria)	{
				ring.decreaseRating(complained2, Rating.amountForDroppingPackets);
			}
			ring.increaseRating(Complainer, Rating.amountForComplainingRight);
		}
	}
	
	
	
	void writeComplaint(Node complainer, Node complained)	{
		float rating = putNComplaints(complained, Rating.numberOfTestsByInformer);
		if(rating > Rating.informerPassCritaria) {
			//ring.decreaseRating(complainer, Rating.amountForComplainingWrong);
		}
		else	{
			ring.decreaseRating(complained, Rating.amountForDroppingPackets);
			ring.increaseRating(complainer, Rating.amountForComplainingRight);
		}
	}
	
	float putNComplaints(Node complained, int n)	{
		int successful = 0;
		for(int i=0;i<n;i++)	{
			InformerNode randomInformer = ring.getRandomInformer();
			if(randomInformer.investigate(complained)!=null)	{
				successful++;
			}
		}
		return (float)((successful*1.0)/n);
	}
}
