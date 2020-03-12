package core;

import java.util.ArrayList;
import java.util.List;

public class StaticGraph {
	int v;
	int e;
	
	int root;
	
	/* TODO: optimization: once the full adjacency list is created, we can convert it into List<int[]> to work with primitive ints
	 * instead of Integer objects. Saves memory and is less annoying. */
	 
	List<List<Integer>> adjacencyList;
	
	public StaticGraph(int v) {
		this.v = v;
		
		adjacencyList = new ArrayList<>();
		
		for(int i=0; i<v; i++)
			adjacencyList.add(new ArrayList<>());
		
	}
	
	public int getV() {return v;}
	public int getE() {return e;}
	
	public void addEdge(int u, int v) {
		adjacencyList.get(u).add(v);
		adjacencyList.get(v).add(u);
		
		e++;
	}
	
	public List<Integer> getNeighbors(int v){ return adjacencyList.get(v); }
	
	public void addEdge(int[] uv) {	this.addEdge(uv[0],uv[1]); }
	
	public static void main(String args[]) {
		StaticGraph graph = new StaticGraph(10);
		
		graph.addEdge(1, 2);
		graph.addEdge(3, 2);
		
		System.out.println("ble");
	}
	
	public void setRoot(int root) { this.root = root; }
}
