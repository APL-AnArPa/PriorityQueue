OBJ = ../class

all: $(OBJ)/BinaryHeap.class $(OBJ)/BinomialHeap.class $(OBJ)/FibonacciHeap.class $(OBJ)/PriorityQueue.class $(OBJ)/MainClass.class 

$(OBJ)/MainClass.class: MainClass.java
	javac -d ../class/ MainClass.java
	
$(OBJ)/PriorityQueue.class: PriorityQueue.java
	javac -d ../class/ PriorityQueue.java

$(OBJ)/BinaryHeap.class: BinaryHeap.java
	javac -d ../class/ BinaryHeap.java	
	
$(OBJ)/BinomialHeap.class: BinomialHeap.java
	javac -d ../class/ BinomialHeap.java

$(OBJ)/FibonacciHeap.class: FibonacciHeap.java
	javac -d ../class/ FibonacciHeap.java
	
clean:
	rm -f ../class/*.class
