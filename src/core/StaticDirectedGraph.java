package core;

import java.util.ArrayList;
import java.util.List;

public class StaticDirectedGraph {
	int v;
	int e;
	
	int root;
	 
	List<List<Integer>> adjacencyList;
	
	public StaticDirectedGraph(int v) {
		this.v = v;
		
		adjacencyList = new ArrayList<>();
		
		for(int i=0; i<v; i++)
			adjacencyList.add(new ArrayList<>());
		
	}
	
	public int getV() {return v;}
	public int getE() {return e;}
	
	public void addEdge(int u, int v) {
		adjacencyList.get(v).add(u);
		
		e++;
	}
	
	public List<Integer> getNeighbors(int v){ return adjacencyList.get(v); }
	
	public static void main(String args[]) {
		StaticGraph graph = new StaticGraph(10);
		
		graph.addEdge(1, 2);
		graph.addEdge(3, 2);
		
		System.out.println("ble");
	}
	
	public String printAsSolution() {
		String solutionString = new String(getDepth() + "\n");
		
		for (int i = 0; i < v; i++) {
			if(i==root) {
				solutionString += (0 + "\n");
				continue;
			}
			
			solutionString += (adjacencyList.get(i).get(0) + "\n");
		}
		
		return solutionString;
	}
	
	public int getDepth() { return -1; } //TODO
	
	public void setRoot(int root) { this.root = root; }
}
