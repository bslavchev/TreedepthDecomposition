package solvers.mcts.core;

import java.util.HashSet;
import java.util.Set;

public class HyperEdge {
	Node from;
	Set<Node> to;
	int action;
	int score = Integer.MAX_VALUE;
	
	public HyperEdge(int action, Node from, Set<Node> to) {
		this.action = action;
		this.from = from;
		this.to = to;
		
		computeScore();
	}
	
	public Node getFrom() { return from; }
	public Set<Node> getTo() { return to; } 
	
	public int getScore() { return score; } 
	public void computeScore() {
		int currentWorst = -1;
		
		for (Node node : to) {
			score = node.getScore();
			if(score > currentWorst)
				currentWorst = score;
		}
		
		currentWorst++;
		if(currentWorst < score) {
			score = currentWorst;
			from.computeScore();
		}			
	}
}
