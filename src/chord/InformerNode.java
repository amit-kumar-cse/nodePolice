package chord;



public class InformerNode extends Node {
	public InformerNode(long nodeId) {
		super(nodeId);
	}
	
	Node investigate(Node target)	{
		MyRandomGenerator numGen = MyRandomGenerator.getInstance();
		long data = numGen.nextKey();
		return target.search(data, this, new MyStack<Node>());
	}
}
