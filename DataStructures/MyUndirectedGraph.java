/*
 * Wilhelm Ericsson(wier0584) - wilhelm__ericsson@hotmail.com
 * Emil Vesa(emveXXXX)- emil.vesa@gmail.com	
 */
package alda.graph;



import java.lang.reflect.Parameter;
import java.util.*;



public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	Map<T, Vertex<T>> nodes;
	Vertex<T> lastModifiedNode;
	int numOfNodes, numOfEdges;
	
	//--------------------------Constructors---------------------------------
	
	public MyUndirectedGraph() {
		numOfNodes = numOfEdges = 0;
		nodes = new HashMap<T, Vertex<T>>();
	}
		
	//--------------------------Methods---------------------------------
	@Override
	public int getNumberOfNodes() {

		return numOfNodes;
	}

	@Override
	public int getNumberOfEdges() {
	
		return numOfEdges;
	}

	@Override
	public boolean add(T newNode) {
		boolean wasSuccessful = !nodes.containsKey(newNode);
		if(wasSuccessful){
			nodes.put(newNode, new Vertex<T>(newNode));
			numOfNodes++;
			lastModifiedNode = nodes.get(newNode);
		}
		return wasSuccessful;
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		boolean isConnected = isConnected(node1, node2);
		if(cost > 0 && containsNodes(node1, node2)){
			
			nodes.get(node1).connectToVertex(nodes.get(node2), cost);
			nodes.get(node2).connectToVertex(nodes.get(node1), cost);
			if(!isConnected){
				numOfEdges++;
				isConnected = true;
			}
		}else{
			isConnected = false;
		}
		return isConnected;
	}
	private boolean containsNodes(T node1, T node2){
		boolean doesContainNodes = false;
		if(node1 != null && node2 != null){
			doesContainNodes = nodes.containsKey(node1) && nodes.containsKey(node2);
		}
		
		return doesContainNodes;
	}

	
	@Override
	public boolean isConnected(T node1, T node2) {
		boolean isConnected = false;
		if(containsNodes(node1, node2)){
			isConnected = nodes.get(node1).isAdjacentTo(nodes.get(node2));
		}
		return isConnected;
	}
	
	@Override
	public int getCost(T node1, T node2) {
		Vertex<T> tempNode1 = nodes.get(node1);
		Vertex<T> tempNode2 = nodes.get(node2);
		int cost = -1;
		
		if(isConnected(node1, node2)){
			cost = tempNode1.findEdge(tempNode2).cost;
		}
		
		return cost;
	}
	

	  
	/**
	 * Performs a depth first search between the specified elements<T> and returns a path if one is found. 
	 * 
	 * @param start The element from which the search starts 
	 * 
	 * @param end The sought after element
	 * 
	 * @return A List<T> containing all nodes on the path between start and end or an empty List<T> if no path was found.
	 * 
	 * @throws NoSuchElementException If any of the elements specified is not in the graph 
	 * 
	 */
	@Override
	public List<T> depthFirstSearch(T start, T end) throws NoSuchElementException{
		
		Stack<Vertex<T>> pathTrack = new Stack<>();
		Map<T, T> accessedNodes = new HashMap<>();
		Vertex<T> currentNode = nodes.get(start);
		
		if (containsNodes(start, end)) {
			accessedNodes.put(currentNode.data, null);
			pathTrack.push(currentNode);

			while (!pathTrack.isEmpty()) {
				currentNode = getNextUnvisitedNode(currentNode, accessedNodes);
				if (currentNode != null) {
					accessedNodes.put(currentNode.data, pathTrack.peek().data);
					pathTrack.push(currentNode);
				} else {
					pathTrack.pop();
					if (!pathTrack.isEmpty()) {
						currentNode = pathTrack.peek();
					}
				}
			}

		}else{
			throw new NoSuchElementException();
		}
			

		return buildPath(accessedNodes, end);
	}
	private List<T> buildPath(Map<T,T> accessedNodes, T end){
		List<T> path = new LinkedList<>();
		T tempNode = end;
		while(tempNode != null){
			path.add(0, tempNode);
			tempNode = accessedNodes.get(tempNode);
		}
		
		return path;
	}
	/**
	 * Iterates through all adjacent nodes i a specified Vertex<T> to find one 
	 * that has not yet been visited.
	 * 
	 * @param currentNode the specified node to be searched for adjacent unvisited nodes
	 * 
	 * @param visitedNodes a Map containing all previously visited nodes and what node it was accessed from
	 * 
	 * @return a Vertex<T> that has not yet been visited or null if no one is found. 
	 */
	private Vertex<T> getNextUnvisitedNode(Vertex<T> currentNode, Map<T,T> visitedNodes){ 
		Iterator<Edge<T>> tempList = currentNode.adjacentNodes.iterator();
		Vertex<T> nextNode = tempList.next().adjacentNode;;
		while(tempList.hasNext() && visitedNodes.containsKey(nextNode.data)){
			nextNode = tempList.next().adjacentNode;
		}
		if(visitedNodes.containsKey(nextNode.data)){
			nextNode = null;
		}
		return nextNode;
	}
	
	/**
	 * Performs a breadth first search between the specified elements<T> and returns the path if one is found. 
	 * 
	 * @param start The element from which the search starts 
	 * 
	 * @param end The sought after element
	 * 
	 * @return A List<T> containing all nodes on the path between start and end or an empty List<T> if no path was found.
	 * 
	 * @throws NoSuchElementException If any of the elements specified is not in the graph 
	 * 
	 */
	@Override
	public List<T> breadthFirstSearch(T start, T end) throws NoSuchElementException{
		LinkedList<Vertex<T>> queue = new LinkedList<>();
		Map<T,T> accessedNodes = new HashMap<>();
		Vertex<T> tempNode;
		
		if(containsNodes(start, end)){
			tempNode = nodes.get(start);
			accessedNodes.put(tempNode.data, null);
			queue.add(tempNode);
			while(!queue.isEmpty()){
				tempNode = getNextUnvisitedNode(queue.getFirst(), accessedNodes);
				if(tempNode != null){
					queue.addLast(tempNode);
					accessedNodes.put(tempNode.data, queue.getFirst().data);
				}else{
					queue.removeFirst();
				}
				
			}

		}else{
			throw new NoSuchElementException();
		}
		return buildPath(accessedNodes, end);
	}
	
	
	
	
	
	@Override
	public UndirectedGraph<T> minimumSpanningTree(){
		Map<T, Edge<T>> accessedVertices = new HashMap<>();
		PriorityQueue<Edge<T>> edgeQueue = new PriorityQueue<>();
		Vertex<T> currentVertex = lastModifiedNode;
		if(currentVertex != null){
			accessedVertices.put(currentVertex.data, null);
			addVertexEdgesToQueue(edgeQueue,accessedVertices,currentVertex);
		}
		
		while(!edgeQueue.isEmpty()){
			Edge<T> tempEdge = edgeQueue.poll();
			currentVertex = tempEdge.adjacentNode;
			if(!accessedVertices.containsKey(currentVertex.data)){
				accessedVertices.put(currentVertex.data, currentVertex.findEdge(tempEdge.startNode));
				addVertexEdgesToQueue(edgeQueue, accessedVertices ,currentVertex);
			}
						
		}		
		return buildMinSpannTree(accessedVertices);
	}
	
	private void addVertexEdgesToQueue(PriorityQueue<Edge<T>> edgeQueue, Map<T, Edge<T>> accessedVertices ,Vertex<T> vertex){
		Iterator<Edge<T>> tempAdjecentNodes = vertex.adjacentNodes.iterator();
		Edge<T> tempEdge;
		while(tempAdjecentNodes.hasNext()){
			tempEdge = tempAdjecentNodes.next();
			if(!tempEdge.equals(accessedVertices.get(vertex.data))){
				edgeQueue.add(tempEdge);
			}	
		}
	}
	/**
	 * Builds a new MyUndirectedGraph<T> by iterating over the keySet of the parameter accessedVertices 
	 * 
	 * 
	 * @param accessedVertices maps all vertices that has been accessed to the corresponding Edge<T> that was used accessing them. 
	 * 
	 * 
	 * @return a new undirected graph representing a minimum spanning tree  
	 */
	private MyUndirectedGraph<T> buildMinSpannTree(Map<T, Edge<T>> accessedVertices){
		MyUndirectedGraph<T> minSpannTree = new MyUndirectedGraph<T>();
		Edge<T> edge;
		Iterator<T> tempVertices = accessedVertices.keySet().iterator();
		T tempVertex;
		while(tempVertices.hasNext()){
			tempVertex = tempVertices.next();
			edge = accessedVertices.get(tempVertex); 
			minSpannTree.add(tempVertex);
			if(edge != null){
				minSpannTree.add(edge.adjacentNode.data);
				minSpannTree.connect(tempVertex, edge.adjacentNode.data, edge.cost);
				
			}
		}
		return minSpannTree;
	}
	
	
	
	public String toString(){
		Iterator<T> graf = nodes.keySet().iterator();
		StringBuffer output = new StringBuffer();
		while(graf.hasNext()){
			output.append(nodes.get(graf.next()).toString() + "\n");
		}
		
		return output.toString();
		
		
	}
	
	
	
	
	private static class Vertex<T> {
		T data;
		LinkedList<Edge<T>> adjacentNodes;
		
		public Vertex(T data){
			this.data = data;
			adjacentNodes = new LinkedList<Edge<T>>();
		}
		
		
		public boolean isAdjacentTo(Vertex<T> vertex){
			boolean isAdjacent = false;
			Iterator<Edge<T>> tempAdjacentNodes = adjacentNodes.iterator(); 
			while(tempAdjacentNodes.hasNext() && !isAdjacent){
				isAdjacent = tempAdjacentNodes.next().adjacentNode.equals(vertex);
			}
			return isAdjacent;
		}
		@Override
		public boolean equals(Object o){
			boolean isEqual = false;
			if(o instanceof Vertex){
				Vertex<T> tempVertex = (Vertex<T>)o;
				isEqual = data.equals(tempVertex.data);
			}	
		
			return isEqual;
		}
		public Edge<T> findEdge(Vertex<T> vertex){
			Iterator<Edge<T>> tempAdjacent = adjacentNodes.iterator();
			Edge<T> edge = null;
			
			while(tempAdjacent.hasNext() && edge == null){
				edge = tempAdjacent.next();
				if(!edge.adjacentNode.equals(vertex)){
					edge = null;
				}
			}
			
			
			return edge;
			
		}
		
		public void connectToVertex(Vertex<T> vertex, int cost){
			Edge<T> tempEdge = findEdge(vertex);
			if(tempEdge == null){
				adjacentNodes.add(new Edge<T>(this ,vertex, cost));

			}else if(tempEdge != null){
				tempEdge.cost = cost;
			}
		}
		public String toString(){
			StringBuffer output = new StringBuffer();
			Iterator<Edge<T>> tempAdjNodes = adjacentNodes.iterator();
			output.append("[" + data + ", {");
			while(tempAdjNodes.hasNext()){
				output.append(tempAdjNodes.next());
				if(tempAdjNodes.hasNext()){
					output.append(", ");
				}
			}
			output.append("}]");
			
			
			return output.toString();
		}
	}
	
	
	private static class Edge<T> implements Comparable<Edge<T>>{
		Vertex<T> adjacentNode, startNode;
		int cost;
		public Edge(Vertex<T> startNode, Vertex<T> adjacentNode, int cost){
			this.startNode = startNode;
			this.adjacentNode = adjacentNode;
			this.cost = cost;
		}
		@Override
		public boolean equals(Object o){
			boolean isEqual = false;
			if(o instanceof Edge){
				Edge<T> tempEdge = (Edge<T>)o;
				if(cost == tempEdge.cost && adjacentNode.equals(tempEdge.adjacentNode)){
					isEqual = true;
				}
			}		
			return isEqual;
		}

		@Override
		public int compareTo(Edge<T> o) {
			int compRes = 0;
			if(cost > o.cost){
				compRes = 1;
			}else if(cost < o.cost){
				compRes = -1;
			}
			
			return compRes;

		}
		
		public String toString(){
			return adjacentNode.data + "(" + cost + ")";
		}
		
	}
	
}
