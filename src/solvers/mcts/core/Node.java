package solvers.mcts.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.DynamicComponent;

public class Node {
	private DynamicComponent state;
	private HyperEdge inbound;
	private Set<HyperEdge> outbound = new HashSet<HyperEdge>();
	private List<Integer> unexpandedActions;
	
	private int score;
	
	private int visits;
	
	public Node(DynamicComponent state) {
		this.state = state;
		this.score = state.getSize();
		
		this.unexpandedActions = state.getIDsOfActiveVertices();
	}
	
	public HyperEdge applyAction(int i){
		Set<DynamicComponent> dcs = state.disableVertex(i);
		
		Set<Node> nodes = new HashSet<>();
		
		for (DynamicComponent dc : dcs) {
			Node newNode = new Node(dc);			
			nodes.add(newNode);
		}
		
		HyperEdge newEdge = new HyperEdge(i, this, nodes);
		
		this.addChild(newEdge);
		
		for (Node node : nodes)
			node.addParent(newEdge);
		
		return newEdge;
	}
	
	public List<Integer> getUnexpandedActions() { return unexpandedActions; }
	
	public void expandAction(int i) { unexpandedActions.remove(new Integer(i)); }
	
	public void addParent(HyperEdge parentEdge) { inbound = parentEdge; }
	public HyperEdge getParent() { return inbound; }
	
	
	public void addChild(HyperEdge childEdge) { outbound.add(childEdge); }
	public Set<HyperEdge> getChildren() { return outbound; } 
	
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
		int bestScore = Integer.MAX_VALUE;
		
		for (HyperEdge hyperEdge : outbound) {
			int score = hyperEdge.getScore();
			if(score < bestScore) {
				bestScore = score;
				best = new ArrayList<>();
			}
			
			if(score == bestScore)
				best.add(hyperEdge);
		}
		
	
		if(best.size() == 0)
			return null;
		
		int randomIndex = (int) (Math.random() * best.size());
		
		return best.get(randomIndex);
	}
	
	public void addVisit() { visits++; }
	public int getVisits() { return visits;}
	
	public String toString() {
		return ("Node(" + state.getIDsOfActiveVertices() + " v" + visits + " s" + score + ")");
	}
}
