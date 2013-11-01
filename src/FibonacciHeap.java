import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class FibonacciHeap extends PriorityQueue
{
	FibNode min;
	int n;
	
	private FibonacciHeap()
	{
		min = null;
		n = 0;
	}
	
	//Make empty Heap
	public static FibonacciHeap makeHeap()
	{
		return new FibonacciHeap();
	}
	
	//Function to find minimum node 
	public FibNode findMin() 
	{
		return min;
	}

	//Concatenates list of x to list of r. r cannot be null. if x is null, nothing is performed.
	private void concatList(FibNode r, FibNode x)
	{
		FibNode tempx, tempr;
		if(r == null)
		{
			System.out.println("Make sure FibNode r cannot be null");
		}
		else if(x != null)
		{
			tempx = x.left;
			x.left = r;
			tempr = r.right;
			r.right = x;
			tempx.right = tempr;
			tempr.left = tempx;
		}
	}
	
	//Function to find node with key value 
	private FibNode findKey(FibNode node, int key)
	{
		FibNode temp = node;
		if(temp != null)
		{
			do{
				if(temp.key == key)
					return temp;
				else
				{
					FibNode RetNode = findKey(temp.child, key);
					if(RetNode != null)
						return RetNode;
					else
						temp = temp.right;
				}
			}while(temp != node);
		}
		return null;
	}	
	
	//Inserts key into the Heap if key is not already present
	public void insert(int key) 
	{
		if(findKey(min, key) != null)
		{
			System.out.println("Key already exists");
			return;
		}
		
		FibNode x = new FibNode(key);
		
		//concatenate rootlist
		if(min != null)
			concatList(min, x);
		else
			min = x;
		
		if(min == null || x.key < min.key)
			min = x;
		n++;
	}

	//Adds Node y as the child of Node x
	private void Fib_heap_link(FibNode y, FibNode x)
	{
		y.left.right = y.right;
		y.right.left = y.left;
		y.par = x;
		y.mark = false;

		y.left = y;
		y.right = y;
		if(x.child != null)
			concatList(x.child, y);
		else
			x.child = y;
		
		x.degree ++;
	}

	//Consolidates the root list
	private void consolidate()
	{
		int D = (int) (Math.log10(n)/Math.log10(1.61803));

		FibNode x = min;
		int RootListLen = 0;
		do{
			RootListLen++;
			x = x.right;
		}while(x != min);
		
		FibNode[] A = new FibNode[D];
		x = min;
		for(int i = 0; i < RootListLen; i++)
		{
			int d = x.degree;
			while(A[d] != null)
			{
				FibNode y = A[d];
				if(x.key > y.key)
				{
					//exchange x <-> y
					FibNode temp = x;
					x = y;
					y = temp;
				}
				Fib_heap_link(y, x);
				A[d] = null;
				d++;
			}
			A[d] = x;
			x = x.right;
		}
		
		min = null;
		for(int i = 0; i < D; i++)
			if(A[i] != null)
				if(min == null || A[i].key < min.key)
					min = A[i];
	}
	
   	//Removes the node with minimum key from the heap 
	public FibNode extractMin() 
	{
		if(min == null)
			return null;

		FibNode z = min; 
		FibNode x = z.child;
		if(x != null)
		{
			do{
				FibNode temp = x.right;
				
				x.right = x;
				x.left = x;
				if(min != null)
					concatList(min, x);
				else
					min = x;
				x.par = null;
				
				x = temp;
			}while(x != z.child);
		}
		//remove z from root list of H
		z.left.right = z.right;
		z.right.left = z.left;
		
		if(z == z.right)
			min = null;
		else
		{
			min = z.right;
			consolidate();
		}
		n--;
		return z;
	}

	//Node x is cut and added to the root list
	private void cut(FibNode x, FibNode par)
	{
		if(par.child == x)
			if(x.right == x)
				par.child = null;
			else
				par.child = x.right;
		
		x.left.right = x.right;
		x.right.left = x.left;
		par.degree --;
		
		x.left = x;
		x.right = x;		
		if(min != null)
			concatList(min, x);
		else
			min = x;
		
		x.par = null;
		x.mark = false;
	}
	
	//If Node y is not marked, then Node y is marked,
	//else Node y is cut and added to the root list
	private void cascading_cut(FibNode y)
	{
		FibNode z = y.par;
		if(z != null)
		{
			if(y.mark == false)
				y.mark = true;
			else
			{
				cut(y, z);
				cascading_cut(z);
			}
		}
	}

	//Function to update key with a given value
	public void updateKey(int key, int newKey) 
	{
		if(min == null)
		{
			System.out.println("Heap is empty");
			return;
		}
		FibNode x = findKey(min, key); 
		if(x == null)
		{
			System.out.println("Key not found");
			return;
		}
		if(findKey(min, newKey) != null)
		{
			System.out.println("New key already exists");
			return;
		}
		if(newKey > key)
		{
			increase(x, newKey);
		}
		else
		{
			decrease(x, newKey);
		}

		
	}	
	
	//Function to decrease key with a given value
	protected void decrease(PriorityQNode node, int ToVal) 
	{
		FibNode x = (FibNode)node;
		x.key = ToVal;
		FibNode y = x.par;
		if(y != null && x.key < y.key)
		{
			cut(x, y);
			cascading_cut(y);
		}
		
		if(x.key < min.key)
			min = x;
	}

	//Function to increase key with a given value
	protected void increase(PriorityQNode node, int ToVal) 
	{
		FibNode x = (FibNode)node;
		x.key = ToVal;
		while(x.child != null)
		{
			FibNode y = x.child, minChild = null;
			int minVal = ToVal;
			do{
				if(y.key < minVal)
				{
					minVal = y.key;
					minChild = y;
				}
				y = y.right;
			}while(y != x.child);
			
			if(minChild == x)
				break;
			else
			{
				int temp = x.key;
				x.key = minChild.key;
				minChild.key = temp;
				
				//if the updated node was min, then update min
				if(min == x)
				{
					FibNode tempMin = x;
					do{
						if(tempMin.key < min.key)
							min = tempMin;
						tempMin = tempMin.right;
					}while(tempMin != x);
				}

				x = minChild;
			}
		}
	}

	//Function to delete a particular element
	public void delete(int key) 
	{
		if(min == null)
		{
			System.out.println("Heap is empty");
			return;
		}
		FibNode node = findKey(min, key);
		if(node == null)
		{
			System.out.println("Key not found");
			return;
		}
		decrease(node, Integer.MIN_VALUE);
		extractMin();
	}

	//Print the heap in dot format in a file
	public void displayHeap(String fileName) 
	{
		if(min == null)
		{
			System.out.println("The heap is empty");
			return;
		}
		FibNode root = this.min;
		try 
		{
			PrintWriter out = new PrintWriter(fileName);
			out.println("digraph FibonacciHeap {");
			do
			{
				FibNode nextRoot = root.right;
				out.println(root.key + " -> " + nextRoot.key + " [style=\"dotted\" constraint=false];");
				out.println(nextRoot.key + " -> " + root.key + " [style=\"dotted\" constraint=false];");
				displayEdges(root, out);
				root = nextRoot;
			}
			while(root != min);
			out.println("}");
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Recursively print the heap edges in dot format in a file
	private void displayEdges(FibNode node, PrintWriter out)
	{
		out.print(node.key);
		if(node.mark)
		{
			out.print(" [color=red]");
		}
		out.println(";");
		if(node.child != null)
		{
			out.println(node.key + " -> " + node.child.key + ";");
			displayEdges(node.child, out);
			FibNode nextChild = node.child.right;
			while(nextChild != node.child)
			{
				out.println(node.key + " -> " + nextChild.key + ";");
				displayEdges(nextChild, out);
				nextChild = nextChild.right;
			}
		}
	}
}

class FibNode extends PriorityQNode
{
	FibNode par = null, child = null, left = this, right = this;
	int degree = 0;
	boolean mark = false;
	
	public FibNode() 
	{
		super();
	}
	
	public FibNode(int key) 
	{
		super(key);
	}
}
