package solvers.mcts.core;

import java.util.Set;

import solvers.mcts.MCTS;

public class HyperEdge {
	Node from;
	Set<Node> to;
	int action;
	int score = Integer.MAX_VALUE;
	
	private int visits = 0;
	private double totalResult = 0;
	
	public HyperEdge(int action, Node from, Set<Node> to) {
		this.action = action;
		this.from = from;
		this.to = to;
		
		computeScore();
	}
	
	public double computeUCB1() {
		if(visits == 0) 
			return Double.MAX_VALUE;
		
		int N = from.getVisits();
		return (totalResult/visits + MCTS.C * Math.sqrt(Math.log(N)/visits));
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

	public int getAction() { return action; }
	
	public void addVisit(double result) {
		visits++;
		totalResult += result;
	}
	
	public int getVisits() { return visits; }
	public double getResult() { return totalResult/visits; }
}
