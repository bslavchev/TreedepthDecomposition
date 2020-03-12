package solvers.mcts.core;

import java.util.HashSet;
import java.util.Set;

public class HyperEdge {
	Node from;
	Set<Node> to;
	
	public HyperEdge(Node from, Set<Node> to) {
		this.from = from;
		this.to = to;
	}
	
	public Node getFrom() { return from; }
	public Set<Node> getTo() { return to; } 
}
