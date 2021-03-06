import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


public class BinaryHeap extends PriorityQueue
{
	private ArrayList<BinaryNode> NodeList;
	
	//Make empty Heap 
	public static BinaryHeap makeHeap()
	{
		return new BinaryHeap();
	}
	
	private BinaryHeap() 
	{
		NodeList = new ArrayList<BinaryNode>();
	}
	
	//Function to find node with key value 
	private BinaryNode find(int key)
	{
		for(int i = 0; i < NodeList.size(); i++)
		{
			if(NodeList.get(i).key == key)
			{
				return NodeList.get(i);
			}
		}
		return null;
	}
	
	//Inserts key into the Heap if key is not already present
	public void insert(int key)
	{
		if(find(key) != null)
		{
			System.out.println("Key already exists");
			return;
		}
		BinaryNode node = new BinaryNode(key);
		NodeList.add(node);
		int i = NodeList.size() - 1;
		while(i > 0)
		{
			int j = (i + 1)/2 - 1;
			BinaryNode ancestor = NodeList.get(j);
			if(ancestor.key > node.key)
			{
				Collections.swap(NodeList, i, j);
				i=j;
				node = NodeList.get(j);
			}
			else
			{
				break;
			}
		}
	}
	
	//Function to delete a particular element
	public void delete(int key)
	{
		if(NodeList.size() == 0)
		{
			System.out.println("Heap is empty");
			return;
		}
		BinaryNode node = find(key);
		if(node == null)
		{
			System.out.println("Key not found");
			return;
		}
		int index = NodeList.indexOf(node);
		Collections.swap(NodeList, index, NodeList.size() - 1);
		NodeList.remove(NodeList.size() - 1);
		if(index == NodeList.size())
		{
			return;
		}
		int i = index;
		int parent = (i + 1)/2 - 1;
		if(parent >= 0 && NodeList.get(index).key < NodeList.get(parent).key)
		{
			BinaryNode currentNode = NodeList.get(index); 
			while(i > 0)
			{
				parent = (i + 1)/2 - 1;
				BinaryNode ancestor = NodeList.get(parent);
				if(ancestor.key > currentNode.key)
				{
					Collections.swap(NodeList, i, parent);
					i=parent;
					currentNode = NodeList.get(parent);
				}
				else
				{
					break;
				}
			}
		}
		else
		{
			int length = NodeList.size();
			while(i < length)
			{
				int smallest = i;
				int left = 2*(i + 1) - 1;
				int right = 2*(i + 1);
				if(left < length && NodeList.get(left).key < NodeList.get(i).key)
				{
					smallest = left;
				}
				if(right < length && NodeList.get(right).key < NodeList.get(smallest).key)
				{
					smallest = right;
				}
				if(smallest == i)
				{
					break;
				}
				else
				{
					Collections.swap(NodeList, i, smallest);
					i = smallest;
				}
			}
		}
	}
	
   	//Removes the node with minimum key from the heap 
	public BinaryNode extractMin()
	{
		if(NodeList.size() == 0)
		{
			return null;
		}
		Collections.swap(NodeList, 0, NodeList.size() - 1);
		BinaryNode node = NodeList.remove(NodeList.size() - 1);
		int length = NodeList.size();
		int i = 0;
		while(i < length)
		{
			int smallest = i;
			int left = 2*(i + 1) - 1;
			int right = 2*(i + 1);
			if(left < length && NodeList.get(left).key < NodeList.get(i).key)
			{
				smallest = left;
			}
			if(right < length && NodeList.get(right).key < NodeList.get(smallest).key)
			{
				smallest = right;
			}
			if(smallest == i)
			{
				break;
			}
			else
			{
				Collections.swap(NodeList, i, smallest);
				i = smallest;
			}
		}
		return node;
	}
	
	//Function to find minimum node 
	public BinaryNode findMin()
	{
		if(NodeList.size() == 0)
		{
			return null;
		}
		return NodeList.get(0);
	}

	//Function to update key with a given value
	public void updateKey(int key, int newKey)
	{
		if(NodeList.size() == 0)
		{
			System.out.println("Heap is empty");
			return;
		}
		BinaryNode node = find(key);
		if(node == null)
		{
			System.out.println("Key not found");
			return;
		}
		if(find(newKey) != null)
		{
			System.out.println("New key already exists");
			return;
		}
		if(newKey > key)
		{
			increase(node, newKey);
		}
		else
		{
			decrease(node, newKey);
		}
	}
	
	//Function to decrease key with a given value
	protected void decrease(PriorityQNode node, int newKey)
	{
		node.key = newKey;
		int i = NodeList.indexOf(node);
		int parent = (i + 1)/2 - 1;
		if(parent >= 0 && NodeList.get(i).key < NodeList.get(parent).key)
		{
			while(i > 0)
			{
				parent = (i + 1)/2 - 1;
				BinaryNode ancestor = NodeList.get(parent);
				if(ancestor.key > node.key)
				{
					Collections.swap(NodeList, i, parent);
					i=parent;
					node = NodeList.get(parent);
				}
				else
				{
					break;
				}
			}
		}
	}
	
	//Function to increase key with a given value
	protected void increase(PriorityQNode node, int newKey)
	{
		node.key = newKey;
		int i = NodeList.indexOf(node);
		int length = NodeList.size();
		while(i < length)
		{
			int smallest = i;
			int left = 2*(i + 1) - 1;
			int right = 2*(i + 1);
			if(left < length && NodeList.get(left).key < NodeList.get(i).key)
			{
				smallest = left;
			}
			if(right < length && NodeList.get(right).key < NodeList.get(smallest).key)
			{
				smallest = right;
			}
			if(smallest == i)
			{
				break;
			}
			else
			{
				Collections.swap(NodeList, i, smallest);
				i = smallest;
			}
		}
	}
	
	//Print the heap in dot format in a file
	public void displayHeap(String fileName)
	{
		if(NodeList.size() == 0)
		{
			System.out.println("The heap is empty");
			return;
		}
		try 
		{
			PrintWriter out = new PrintWriter(fileName);
			out.println("digraph BinaryHeap {");
			displayEdges(0, out);
			out.println("}");
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	//Recursively print the heap edges in dot format in a file
	private void displayEdges(int index, PrintWriter out)
	{
		out.println(NodeList.get(index).key + ";");
		int left = 2*(index + 1) - 1;
		if(left >= NodeList.size())
		{
			return;
		}
		out.println(NodeList.get(index).key + " -> " + NodeList.get(left).key + ";");
		int right = 2*(index + 1);
		if(right >= NodeList.size())
		{
			return;
		}
		out.println(NodeList.get(index).key + " -> " + NodeList.get(right).key + ";");
		displayEdges(left, out);
		displayEdges(right, out);
	}
}


class BinaryNode extends PriorityQNode
{
	public BinaryNode() 
	{
		super();
	}
	
	public BinaryNode(int key) 
	{
		super(key);
	}
}
