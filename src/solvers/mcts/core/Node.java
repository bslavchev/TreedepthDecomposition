package solvers.mcts.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.DynamicComponent;

public class Node {
	private DynamicComponent state;
	private Set<HyperEdge> inbound = new HashSet<HyperEdge>();
	private Set<HyperEdge> outbound = new HashSet<HyperEdge>();
	
	private int visits = 0;
	private double totalResult = 0;
	
	private int score;
	
	public Node(DynamicComponent state) {
		this.state = state;
		this.score = state.getSize();
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

	public int getScore() {	return score; } 
	
	public void computeScore() {
		for (HyperEdge iEdge : outbound) {
			if(iEdge.getScore() < this.score) {
				this.score = iEdge.getScore();
				for (HyperEdge oEdge : outbound) {
					oEdge.computeScore();
				}
			}
		}
	}
	
	public HyperEdge selectBest() {
		List<HyperEdge> best = new ArrayList<>();
		
		for (HyperEdge hyperEdge : outbound)
			if(hyperEdge.getScore() == this.score)
				best.add(hyperEdge);
		
		int randomIndex = (int) (Math.random() * best.size());
		
		return best.get(randomIndex);
	}
}
