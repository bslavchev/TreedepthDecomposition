package solvers.mcts.core;

import java.util.HashSet;
import java.util.Set;

import core.DynamicComponent;

public class Node {
	private DynamicComponent state;
	private Set<HyperEdge> inbound = new HashSet<HyperEdge>();
	private Set<HyperEdge> outbound = new HashSet<HyperEdge>();
	
	private int visits = 0;
	private double totalResult = 0;
	
	public Node(DynamicComponent state) {
		this.state = state;
	}
	
	public void addParent(HyperEdge parentEdge) { inbound.add(parentEdge); }
	public Set<HyperEdge> getParents() { return inbound; }
	
	
	public void addChild(HyperEdge childEdge) { outbound.add(childEdge); }
	public Set<HyperEdge> getChildren() { return outbound; } 
	
	public void addVisit(double result) {
		visits++;
		totalResult += result;
	}
	
	public int getVisits() { return visits; }
	public double getResult() { return totalResult/visits; }
	
	public DynamicComponent getState() { return state; } 
}
