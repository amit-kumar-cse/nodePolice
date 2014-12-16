package chord;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import chord.Rating.Catagory;

public class Ring {
	Node[] nodesArray;          //policing nodes
	InformerNode[] informerNodesArray;
	PoliceNode firstPoliceNode;
	public static final int noOfBits = Utils.getNoOfBits();
	int nPoliceNodes;
	int nHonestNodes;
	int nCulpritNodes;
	int nInformerNodes;
	int nTotalNodes;
	Map<Node, Rating> ratings;
	
	
	int getNumberOfInformerNodes()	{
		return nInformerNodes;
	}
	
	Ring(int nHonestNodes, int nCulpritNodes, int nPoliceNodes)	{
		this.nHonestNodes = nHonestNodes;
		this.nCulpritNodes = nCulpritNodes;
		this.nPoliceNodes = nPoliceNodes;
		nInformerNodes = nPoliceNodes;
		ratings = new TreeMap<Node, Rating>();
		
		
		nTotalNodes = nHonestNodes+nCulpritNodes + nPoliceNodes;
		nodesArray = new Node[nTotalNodes];
		//fill the policeNode;
		MyRandomGenerator numGen = MyRandomGenerator.getInstance();
		
		//create policing nodes and generate policing finger tables.
		for(int i=0;i<nPoliceNodes;i++)	{
			nodesArray[i] = new PoliceNode(numGen.nextKey());
			((PoliceNode)nodesArray[i]).setRing(this);
		}
		Arrays.sort(nodesArray, 0, nPoliceNodes);
		for(int nodeNumber=0;nodeNumber<nPoliceNodes;nodeNumber++)	{
			//System.out.println(nodesArray[i].getNodeId());
			nodesArray[nodeNumber].setMyAreaPoliceStation((PoliceNode)nodesArray[nodeNumber]);
			for(int finger=0;finger<noOfBits;finger++)	{
				PoliceNode dest = null;
				long fingerEnd = (nodesArray[nodeNumber].getNodeId()+ (1l<<finger))%(1l<<noOfBits);
				//get the ceiling node responsible in dest;
				int lowIndex = 0;
				int highIndex = nPoliceNodes-1;
				if(fingerEnd>nodesArray[highIndex].getNodeId())	{
					dest = (PoliceNode)nodesArray[0];
				}
				else	{
					while(lowIndex<highIndex)	{
						int mid = (lowIndex + highIndex)/2;
						if(fingerEnd<=nodesArray[mid].getNodeId())
							highIndex = mid;
						else
							lowIndex = mid+1;
					}
					assert(lowIndex == highIndex);
					dest = (PoliceNode)nodesArray[lowIndex];
				}
				((PoliceNode)nodesArray[nodeNumber]).setPoliceNetworkFinger(finger, dest);
			}
		}
		firstPoliceNode = (PoliceNode)nodesArray[0];
		System.out.println("police ring constructed");
		//~
		
		
		//add culprit nodes and the regular nodes.
		for(int i=nPoliceNodes;i<nPoliceNodes+nCulpritNodes;i++)
			nodesArray[i] = new CulpritNode(numGen.nextKey());
		for(int i=nPoliceNodes+nCulpritNodes;i<nTotalNodes;i++)
			nodesArray[i] = new HonestNode(numGen.nextKey());
		//sort
		Arrays.sort(nodesArray);
		//~
		
		//filling the finger tables
		for(Node node:nodesArray){
			//System.out.println();
			if(!(node instanceof PoliceNode))	{
				//PoliceNode myStation = firstPoliceNode.searchMyPoliceStation(node.getNodeId(), firstPoliceNode);
				PoliceNode myStation = firstPoliceNode.searchMyPoliceStation(node.getNodeId(), firstPoliceNode, this);
				node.setMyAreaPoliceStation(myStation);
			}
			for(int finger=0;finger<noOfBits;finger++)	{
				Node dest = null;											//useless warning
				long fingerEnd = (node.getNodeId()+(1l<<finger))%(1l<<noOfBits);
				int lowIndex = 0;
				int highIndex = nTotalNodes-1;
				if(fingerEnd>nodesArray[nTotalNodes-1].getNodeId())	{
					dest = nodesArray[0];
				}
				else	{
					while(lowIndex<highIndex)	{
						int mid = (lowIndex + highIndex)/2;
						if(fingerEnd<=nodesArray[mid].getNodeId())
							highIndex = mid;
						else
							lowIndex = mid+1;
					}
					assert(lowIndex == highIndex);
					dest = nodesArray[lowIndex];
				}
				
				node.setFinger(finger, dest);
			}
		}
		//~
		//full chord construction done
		
		
		//creating informerNodes;
		informerNodesArray = new InformerNode[nInformerNodes];
		for(int i=0; i<nInformerNodes; i++){
			informerNodesArray[i] = new InformerNode(numGen.nextKey());
		}
		//~
		
		System.out.println("full chord construction done");
	}
	
	public InformerNode getRandomInformer()	{
		MyRandomGenerator numGen = MyRandomGenerator.getInstance();
		
		InformerNode temp = informerNodesArray[numGen.nextPositiveInt()%nInformerNodes];
		return temp;
		//Random numGen = new Random(1);
		/*for(int i=0;i<nPoliceNodes;i++)
			nodesArray[i] = new PoliceNode(Math.abs(numGen.nextLong())%(1l<<noOfBits) );
			*/
	}
	
	public String toString(boolean detailed)	{
		String s="";
		//for(Node node:nodesArray)	{
		for (int i = 0; i < nTotalNodes; i++) {
			Node node = nodesArray[i];
			if(node instanceof HonestNode)	
				s += "Honest Node : ";
			else if(node instanceof CulpritNode)	
				s += "Culprit Node : ";
			if(node instanceof PoliceNode)	
				s += "Police Node : ";
			s += "[" + i + "] : ";
			if(detailed==true)	{
				s += node.getNodeId() + "\n";
				for(int finger=0;finger<noOfBits;finger++)
					s += node.getFinger(finger) + "  ";
			}
			if(node instanceof PoliceNode){
				s += "\n Policing ring : ";
				for(int finger=0;finger<noOfBits;finger++)
					s += ((PoliceNode) node).getPoliceNetworkFinger(finger) + "  ";
			}
			s += "\n";	
			
		}
		return s;
	}
	
	void increaseRating(Node node, float amount) {
		if(ratings.containsKey(node)==false)	
			ratings.put(node, new Rating());
		ratings.get(node).increase(amount);
	}
	
	void decreaseRating(Node node, float amount) {
		if(ratings.containsKey(node)==false)	
			ratings.put(node, new Rating());
		ratings.get(node).decrease(amount);
	}
	
	public float runQueries(int n)	{
		//Random numGen = new Random(System.currentTimeMillis());
		MyRandomGenerator numGen = MyRandomGenerator.getInstance();
		MyStack<Node> visited = new MyStack<Node>();
		int successfulQueries = 0;
		for(int i=0;i<n;i++)	{
			Node node = nodesArray[numGen.nextPositiveInt()%nTotalNodes];
			long query = numGen.nextKey();
			visited.clear();
			if(node.search(query, node, visited)!=null)
				successfulQueries++;
		}
		return (float)((successfulQueries*1.0/n)*100.0);
	}
	
	public void removeFaultyNodes()	{
		//TODO: this assumption is faulty that the total nodes in the array will always be of the size of nodesArray. make sure it is not causing bugs
		//TODO: so it is important to update nTotalNodes carefully here
		int newIndex = 0;
		for(int i=0;i<nodesArray.length;i++)	{
			Node node = nodesArray[i];
			if((node instanceof PoliceNode)||
					!(ratings.containsKey(node))||
					!(ratings.get(node).getCatagory()==Catagory.CULPRIT))	{
				//TODO: correct non culprit detection method
				nodesArray[newIndex++] = node;
			}
			else	{
				ratings.remove(node);
			}
		}
		int newIndexCopy = newIndex;
		while(newIndex<nTotalNodes)	{
			nodesArray[newIndex] = null;
			newIndex++;
		}
		this.nTotalNodes = newIndexCopy;
		//TODO: update the other nodes count information
		
		
		//update the ring
		for(int i=0;i<nTotalNodes;i++)	{
			Node node = nodesArray[i];

			for(int finger=0;finger<noOfBits;finger++)	{
				Node dest = null;											//useless warning
				long fingerEnd = (node.getNodeId()+(1l<<finger))%(1l<<noOfBits);
				int lowIndex = 0;
				int highIndex = nTotalNodes-1;
				if(fingerEnd>nodesArray[nTotalNodes-1].getNodeId())	{
					dest = nodesArray[0];
				}
				else	{
					while(lowIndex<highIndex)	{
						int mid = (lowIndex + highIndex)/2;
						if(fingerEnd<=nodesArray[mid].getNodeId())
							highIndex = mid;
						else
							lowIndex = mid+1;
					}
					assert(lowIndex == highIndex);
					dest = nodesArray[lowIndex];
				}
				
				node.setFinger(finger, dest);
			}
		}
	}
	
	public CulpritAnalysisCount getCulpritAnalysis()	{
		int correct=0, incorrect=0;
		//System.out.println("list of culprit nodes : ");
		for(Node node:ratings.keySet())	{
			if(ratings.get(node).getCatagory()==Catagory.CULPRIT)	{
				//System.out.println(node.getNodeId());
				if(node instanceof CulpritNode)
					correct++;
				else
					incorrect++;
			}
		}
		return new CulpritAnalysisCount(correct, incorrect);
	}
}
