package chord;


public abstract class Node implements Comparable<Node>{
	

	private long nodeId;
	Node[] fingerTable;
	PoliceNode myAreaPoliceStation;
	
	
	Node search(long data, Node querySourceNode, MyStack<Node> nodes)	{
		if(data == nodeId)
			return this;

		if(Utils.isInCircularRange(nodeId, data, fingerTable[0].getNodeId()))
			return fingerTable[0];

		//otherwise, forward the query to the best possible node and return the answer.
		int finger;
		for(finger = Ring.noOfBits-1 ;finger>=0; finger--)	{
			if(Utils.isInCircularRange(nodeId, fingerTable[finger].getNodeId(), data ))
				break;
		}

		nodes.push(fingerTable[finger]);
		Node returnVal = fingerTable[finger].search(data, querySourceNode, nodes);
		//TODO: write complaints properly
		if(this==querySourceNode&&returnVal==null)	{
			if(nodes.size()==1)	{
				Node suspect1 = nodes.pop();
				myAreaPoliceStation.writeComplaint(this, suspect1);
				if(!(suspect1 instanceof CulpritNode))	{
					System.out.println("this suspect is complained wrongly");
				}
			}
			else if(nodes.size()>1)	{
				Node suspect1 = nodes.pop();
				Node suspect2 = nodes.pop();
				myAreaPoliceStation.writeComplaint(this, suspect1, suspect2);
				if(!(suspect1 instanceof CulpritNode)&&!(suspect2 instanceof CulpritNode))	{
					System.out.println("these suspects are complained wrongly");
				}
			}
		}
		return returnVal;
		
	}
	
	
	
	public int compareTo(Node other)	{
		if(nodeId-other.getNodeId()==0)
			return 0;
		if(nodeId-other.getNodeId()>0)
			return 1;
		else
			return -1;
	}
	
	//list of getters and setters
	Node(long nodeId)	{
		this.nodeId = nodeId;
		fingerTable = new Node[62];
	}
	
	public long getFinger(int i)	{
		return fingerTable[i].getNodeId();
	}
	
	public void setFinger(int i, Node node)	{
		fingerTable[i] = node;
	}
	
	
	public PoliceNode getMyAreaPoliceStation() {
		return myAreaPoliceStation;
	}

	public void setMyAreaPoliceStation(PoliceNode myAreaPoliceStation) {
		this.myAreaPoliceStation = myAreaPoliceStation;
	}

	public long getNodeId()	{
		return nodeId;
	}
}
