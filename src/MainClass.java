import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainClass {


	public static void main(String[] args) 
	{
		//Checks the input syntax
		if(args.length == 0 || args.length > 1)
        {
			System.out.println("Usage:");
			System.out.println("java MainClass [-binary | -binomial | -fibonacci]");
			return;
        }
		PriorityQueue heap;
		//Creates a Binary Heap
        if(args[0].equals("-binary"))                                
        {
        	heap = BinaryHeap.makeHeap();
        }
		//Creates a Binomial Heap
        else if(args[0].equals("-binomial"))                                
        {
        	heap = BinomialHeap.makeHeap();
        }
		//Creates a Fibonacci Heap
        else if(args[0].equals("-fibonacci"))                                
        {
        	heap = FibonacciHeap.makeHeap();
        }
        else
        {
			System.out.println("Usage:");
			System.out.println("java MainClass [-binary | -binomial | -fibonacci]");
			return;
        }
		while(true)
		{
			System.out.println("1. Insert");
			System.out.println("2. Delete");
			System.out.println("3. Extract Min");
			System.out.println("4. Find Min");
			System.out.println("5. Update Key");
			System.out.println("6. Display");
			System.out.println("7. Exit");
			BufferedReader bOption = new BufferedReader(new InputStreamReader(System.in));
			try 
			{
				//Reads the input option
				String sOption = bOption.readLine();
				switch (sOption) 
				{
				//Inserts a key in the heap
				case "1":
					System.out.println("Enter the key to be inserted");
					String sKey = new BufferedReader(new InputStreamReader(System.in)).readLine();
					try
					{
						int key = Integer.parseInt(sKey);
						heap.insert(key);
					}
					catch(NumberFormatException e)
					{
						System.out.println("Please provide integer values");
					}
					break;
				//Deletes a key in the heap					
				case "2":
					System.out.println("Enter the key to be deleted");
					sKey = new BufferedReader(new InputStreamReader(System.in)).readLine();
					try
					{
						int key = Integer.parseInt(sKey);
						heap.delete(key);
					}
					catch(NumberFormatException e)
					{
						System.out.println("Please provide integer values");
					}
					break;
				//Extracts the Node with minimum key value from the heap
				case "3":
					PriorityQNode min = heap.extractMin();
					if(min == null)
					{
						System.out.println("The heap is empty");
					}
					else
					{
						System.out.println("The minimum key value " + min.key + " is extracted");
					}
					break;
				//Finds the Node with minimum key value in the heap
				case "4":
					min = heap.findMin();
					if(min == null)
					{
						System.out.println("The heap is empty");
					}
					else
					{
						System.out.println("The minimum key value is: " + min.key);
					}
					break;
				//Updates a key value with a new key value
				case "5":
					System.out.println("Enter the key whose value is to be updated");
					sKey = new BufferedReader(new InputStreamReader(System.in)).readLine();
					System.out.println("Enter the new key value");
					String sNewKey = new BufferedReader(new InputStreamReader(System.in)).readLine();
					try
					{
						int key = Integer.parseInt(sKey);
						int newKey = Integer.parseInt(sNewKey);
						heap.updateKey(key, newKey);
					}
					catch(NumberFormatException e)
					{
						System.out.println("Please provide integer values");
					}
					break;
				//Prints the heap in dot format for visualization
				case "6":
					System.out.println("Enter the file name for visualization of the heap");
					String fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();
					heap.displayHeap(fileName);
					break;
				//Exits the program
				case "7":
					System.out.println("Exiting Program....");
					System.exit(0);
				default:
					System.out.println("Wrong Option!!!");
					break;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

}
