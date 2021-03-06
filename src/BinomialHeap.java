import java.io.FileNotFoundException;
import java.io.PrintWriter;



class BinomialHeap extends PriorityQueue
{   	
	private BinomialNode head;
	
	//Constructor   	
	private BinomialHeap()
	{	
		this.head = null; 		// make an empty root list 
	}

	//Make empty Heap   	
	public static BinomialHeap makeHeap()	
	{
		BinomialHeap H=new BinomialHeap();
		return H;
	}
	
	//Function to find minimum node 
	public BinomialNode findMin()  
	{
		if(this.head == null)
		{
			return null;
		}
		BinomialNode x = this.head;
		BinomialNode y = this.head;	
		int min = x.key;
		while (x != null) 
		{
			if (x.key < min) 
			{
				y = x;
				min = x.key;
			}
			x = x.sibling;
		}	
		return y;
	}
	
	//Function to find node with key value 
	private BinomialNode findANodeWithKey(int value, BinomialNode temp) 
	{
		BinomialNode node = null;
		while (temp != null) 
		{		
			if (temp.key == value) 
			{
				node = temp;
				break;
			}
		    if(temp.child == null)	
				temp = temp.sibling;
			else	
			{
				node = findANodeWithKey(value, temp.child);
				if (node == null)
					temp = temp.sibling;
				else
					break;
			}
		}		
		return node;
	}
		
	//Inserts key into the Heap if key is not already present
	public void insert(int key)
	{
		if(findANodeWithKey(key, this.head) != null)
		{
			System.out.println("Key already exists");
			return;
		}
		BinomialHeap H1 = makeHeap();
		BinomialNode x = new BinomialNode(key);
		H1.head = x;
		BinomialHeap H = BinomialHeap_Union(this, H1);
		this.head = H.head;
	}
	
	//Make Node y as the left child of Node z
	private void Binomial_Link(BinomialNode y, BinomialNode z)	
	{
		y.parent = z;
		y.sibling = z.child;
		z.child = y;
		z.degree++;
	}

	//Function to unite two binomial heaps
	private BinomialNode BinomialHeap_Merge(BinomialHeap h1, BinomialHeap h2)
	{
		// If either root list is empty, just return the other.
		if (h1.head == null)
			return h2.head;
		else if (h2.head == null)
    			return h1.head;
		else 
		{
			BinomialNode x;										// head of merged list
			BinomialNode tail;									// last node added to merged list
			BinomialNode h1Next = h1.head, h2Next = h2.head;	// next nodes to be examined in h1 and h2
			if (h1.head.degree <= h2.head.degree)
			{
				x = h1.head;
				h1Next = h1Next.sibling;
			}	
			else 
			{
				x = h2.head;
				h2Next = h2Next.sibling;
			}
			tail = x;					// Go through both root lists until one of them is exhausted.
	 		while (h1Next != null && h2Next != null) 
		 	{
				if (h1Next.degree <= h2Next.degree) 
				{
					tail.sibling = h1Next;
					h1Next = h1Next.sibling;
				}
				else 
				{	
					tail.sibling = h2Next;
					h2Next = h2Next.sibling;
				}
				tail = tail.sibling;
			}
			if (h1Next != null)
				tail.sibling = h1Next;
			else
				tail.sibling = h2Next;
			return x;
 		}
	 }

	//Binomial Heap Union
	private BinomialHeap BinomialHeap_Union(BinomialHeap H1, BinomialHeap H2)	
	{
		BinomialHeap h = new BinomialHeap();
		h.head = BinomialHeap_Merge(H1, H2);
		H1.head = null;			// no longer using the...
		H2.head = null; 		// ...two input lists
		if (h.head == null)
			return h;
		BinomialNode prevX = null;
		BinomialNode x = h.head;
		BinomialNode nextX = x.sibling;
		while (nextX != null)
 		{
			if (x.degree != nextX.degree ||
					(nextX.sibling != null && nextX.sibling.degree == x.degree)) 
			{
				// Cases 1 and 2.
				prevX = x;
				x = nextX;
 			}
			else 
			{
				if (x.key <= nextX.key) 
				{
					// Case 3.
					x.sibling = nextX.sibling;
	   				Binomial_Link(nextX, x);
				}
				else
				{
					// Case 4.
					if (prevX == null)
						h.head = nextX;
					else
						prevX.sibling = nextX;
	   				Binomial_Link(x,nextX);
	   				x = nextX;
				}
			}
			nextX = x.sibling;
		}
		return h;
	}

	//Function to delete a particular element
	public void delete(int value)
	{
		if(this.head == null)
		{
			System.out.println("Heap is empty");
			return;
		}
		BinomialNode x = findANodeWithKey(value,this.head);
		if (x == null)
		{
			System.out.println("Key not found");
			return;
		}
		if (x != null)
		{
			decrease(x, Integer.MIN_VALUE);
			extractMin();
		}
	}

	//Function to update key with a given value
	public void updateKey(int key, int newKey) 
	{
		if(this.head == null)
		{
			System.out.println("Heap is empty");
			return;
		}
		BinomialNode node = findANodeWithKey(key,this.head);
		if (node == null)
		{
			System.out.println("Key not found");
			return;
		}
		if(findANodeWithKey(newKey,this.head) != null)
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
   	public void decrease(PriorityQNode node, int new_value) 
	{
   		BinomialNode temp = (BinomialNode)node;
   		temp.key = new_value;
		BinomialNode tempParent = temp.parent;
		while ((tempParent != null) && (temp.key < tempParent.key)) 
		{
			int z = temp.key;
			temp.key = tempParent.key;
			tempParent.key = z;
			temp = tempParent;
			tempParent = tempParent.parent;
		}
	}
   	
  //Function to increase key with a given value
   	public void increase(PriorityQNode node, int new_value) 
	{
   		BinomialNode temp = (BinomialNode)node;
   		temp.key = new_value;
		BinomialNode smallest = temp;
		BinomialNode descendant = temp.child;
		while (descendant != null) 
		{
			while(descendant != null)
			{
				if(descendant.key < smallest.key)
				{
					smallest = descendant;					
				}
				descendant = descendant.sibling;
			}
			if(smallest == temp)
			{
				break;
			}
			int z = temp.key;
			temp.key = smallest.key;
			smallest.key = z;
			temp = smallest;
			descendant = temp.child;
		}
	}

   	//Removes the node with minimum key from the heap 
	public BinomialNode extractMin()
	{
		if(this.head == null)
		{
			return null;
		}
		BinomialNode x = this.head;		// node with minimum key
		BinomialNode y = x.sibling;		// current node being examined
		BinomialNode pred = x;			// y's predecessor
		BinomialNode xPred = null;		// predecessor of x
		while (y != null) 
		{
			if (y.key < x.key)
			{
				x = y;
				xPred = pred;
			}
			pred = y;
			y = y.sibling;
		}
		removeFromRootList(x, xPred);
		return x;
	}
	
	//Remove Node x from root list and do union with the sub heap rooted at Node x
	private void removeFromRootList(BinomialNode x, BinomialNode pred)
	{
		if (x == head)
			head = x.sibling;
		else
			pred.sibling = x.sibling;
		BinomialHeap hPrime = new BinomialHeap();
		// Reverse the order of x's children, setting hPrime.head to
		// point to the head of the resulting list.
		BinomialNode z = x.child;
		while (z != null)
		{
			BinomialNode next = z.sibling;
			z.sibling = hPrime.head;
			hPrime.head = z;
			z = next;
		}
		BinomialHeap newHeap = BinomialHeap_Union(this,hPrime);
		this.head = newHeap.head;
	}
	
	//Print the heap in dot format in a file
	public void displayHeap(String fileName)
	{
		if(this.head == null)
		{
			System.out.println("The heap is empty");
			return;
		}
		BinomialNode root = this.head;
		try 
		{
			PrintWriter out = new PrintWriter(fileName);
			out.println("digraph BinomialHeap {");
			while(root != null)
			{
				BinomialNode nextRoot = root.sibling;
				if(nextRoot != null)
				{
					out.println(root.key + " -> " + nextRoot.key + " [style=\"dotted\" constraint=false];");
				}
				displayEdges(root, out);
				root = nextRoot;
			}
			out.println("}");
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
  
	//Recursively print the heap edges in dot format in a file
	private void displayEdges(BinomialNode node, PrintWriter out)
	{
		out.println(node.key + ";");
		if(node.child != null)
		{
			out.println(node.key + " -> " + node.child.key + ";");
			displayEdges(node.child, out);
			BinomialNode nextChild = node.child.sibling;
			while(nextChild != null)
			{
				out.println(node.key + " -> " + nextChild.key + ";");
				displayEdges(nextChild, out);
				nextChild = nextChild.sibling;
			}
		}
	}
}


class BinomialNode extends PriorityQNode
{				
	int degree;		
	BinomialNode parent;		
	BinomialNode sibling;		
	BinomialNode child;	

	//Constructor		
	public BinomialNode()		
	{			
		super();
		degree = 0;			
		parent = null;			
		sibling = null;			
		child = null;
	}
	
	public BinomialNode(int key)		
	{			
		super(key);
		degree = 0;			
		parent = null;			
		sibling = null;			
		child = null;
	}
}
