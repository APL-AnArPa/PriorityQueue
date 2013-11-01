
public abstract class PriorityQueue 
{
	abstract public void insert(int key);
	abstract public void delete(int key);
	abstract public PriorityQNode extractMin();
	abstract public PriorityQNode findMin();
	abstract public void updateKey(int key, int newKey);
	abstract protected void decrease(PriorityQNode node, int newKey);
	abstract protected void increase(PriorityQNode node, int newKey);
	abstract public void displayHeap(String fileName);
}

class PriorityQNode
{
	int key;
	
	public PriorityQNode() 
	{
		key = 0;
	}
	
	public PriorityQNode(int key) 
	{
		this.key = key;
	}
}
