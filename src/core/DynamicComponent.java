package core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import util.GrGraphReader;

public class DynamicComponent {
	private StaticGraph graph;
	
	boolean[] activeVertices;
	
	int size;
	
	//TODO figure out what this should do
	boolean checked = false;
	
	private void setChecked() { this.checked = true; }	
	
	private DynamicComponent(StaticGraph graph, boolean[] activeVertices, int size) {
		this.graph = graph;
		this.activeVertices = activeVertices;
		this.size = size;
	}
	
	public DynamicComponent(StaticGraph graph) {
		this.graph = graph;
		
		this.activeVertices = new boolean[graph.getV()];
		Arrays.fill(activeVertices, true);
		
		checked = true;
		
		size = graph.getV();
	}
	
	public Set<DynamicComponent> disableVertex(int vertex, boolean checkConnectivity){
		//WARNING: with checkLater=true this will NOT return a copy of the object, but the same instance!
		
		DynamicComponent comp = this;
		
		if(vertex >= 0) {
			if(checkConnectivity)
				comp = this.copy();
			
			comp.activeVertices[vertex] = false;
		}
		
		if(checkConnectivity)		
			return comp.check();
		
		size--;
		
		HashSet<DynamicComponent> toReturn = new HashSet<>();
		toReturn.add(comp);
		return toReturn;
	}
	
	public int getSize() { return size; }
	
	public DynamicComponent copy() {
		return new DynamicComponent(graph, activeVertices.clone(), this.size);
	}
	
	public Set<DynamicComponent> check(){
		long start = System.currentTimeMillis();
		
		Set<Integer> seen = new HashSet<>();
		Set<Set<Integer>> seenComponents = new HashSet<>();
		
		int componentCount = 0;
		
		for(int i=0; i<graph.getV(); i++) {
			if (!activeVertices[i] || seen.contains(i)) continue;
			
			Set<Integer> discovered = walk(i);
			
			seen.addAll(discovered);
			seenComponents.add(discovered);
		}
		
		if(seenComponents.size()==1) {
			HashSet<DynamicComponent> toReturn = new HashSet<>();
			size--;
			toReturn.add(this);
			
			System.out.println(System.currentTimeMillis() - start);
			
			return toReturn;
		}
			 
				
		Set<DynamicComponent> components = new HashSet<>();
		
		for (Set<Integer> component : seenComponents) {
			boolean[] newActiveVertices = new boolean[graph.getV()];
			
			int count = 0;
			for (Integer element : component) {
				newActiveVertices[element] = true;
				count++;
			}
				
			
			components.add(new DynamicComponent(this.graph, newActiveVertices, count));
		}
		
		System.out.println(System.currentTimeMillis() - start);
		
		return components;
	}
	
	private Set<Integer> walk(int start){		
		LinkedList<Integer> queue = new LinkedList<>();
		Set<Integer> walked = new HashSet<>();
		
		if(!activeVertices[start]) {
			System.out.println("Walking from a disabled vertex");
			return walked;
		}
		
		queue.add(start);
		
		while(!queue.isEmpty()) {
			int currentElement = queue.poll();
			
			walked.add(currentElement);
			
			List<Integer> neighbors = graph.getNeighbors(currentElement);
			
			for (Integer neighbor : neighbors) {
				if(!activeVertices[neighbor] || walked.contains(neighbor) || queue.contains(neighbor)) continue;
				
				queue.add(neighbor);
			}
		}
		
		return walked;
	}
	public Set<DynamicComponent> disableVertex(int vertex) {return disableVertex(vertex, true);}
	
	public static void main(String[] args) throws Exception {
		StaticGraph graph = GrGraphReader.readGraph("graphs/e/exact_001.gr");
		
		DynamicComponent dc = new DynamicComponent(graph);
		DynamicComponent dc1;
		DynamicComponent dc2;
		DynamicComponent dc3;
		DynamicComponent dc4;
		Object[] dcs;
		
		//System.out.println(dc.walk(1));
		
		dc1 = (DynamicComponent) dc.disableVertex(0).toArray()[0];
		dc2 = (DynamicComponent) dc1.disableVertex(7).toArray()[0];
		dcs = dc2.disableVertex(8).toArray();
		
		/*dc3 = (DynamicComponent) dcs[0];
		dc4 = (DynamicComponent) dcs[1];*/
		
		//System.out.println(dc.walk(1));
		
		System.out.println('a');
	}
}
