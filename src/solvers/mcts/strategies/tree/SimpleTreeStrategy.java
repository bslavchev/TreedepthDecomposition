package solvers.mcts.strategies.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import solvers.mcts.MCTS;
import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;

public class SimpleTreeStrategy implements TreeStrategy {

	@Override
	public Node select(Node from) {
		Set<HyperEdge> children = from.getChildren();
		
		if(children.size() < from.getState().getSize())
			expand(from);
			
		
		Set<HyperEdge> bestEdges = new HashSet<HyperEdge>();
		double bestScore = -1;
		
		for (HyperEdge hyperEdge : children) {
			double ucb1 = hyperEdge.computeUCB1();			
			
			if(Double.compare(ucb1, bestScore) > 0) {
				bestScore = ucb1;
				bestEdges = new HashSet<HyperEdge>();				
			}
			
			if(Double.compare(ucb1, bestScore) == 0)
				bestEdges.add(hyperEdge);
		}
		
		List<Node> worstNodes = new ArrayList<>();
		int worstScore = -1;
		
		for (HyperEdge hyperEdge : bestEdges) {
			for (Node node : hyperEdge.getTo()) {
				if(node.getScore() > worstScore) {
					worstScore = node.getScore();
					worstNodes = new ArrayList<>();
				}
				
				if(node.getScore() == worstScore && !worstNodes.contains(node))
					worstNodes.add(node);					
			}
		}
		
		int randomIndex = MCTS.random.nextInt(worstNodes.size());
		
		return worstNodes.get(randomIndex);
	}

	@Override
	public Set<Node> expand(Node from) {
		List<Integer> possibilities = from.getUnexpandedActions();
		Integer action = possibilities.get(MCTS.random.nextInt(possibilities.size()));
		
		return from.applyAction(action);
	}

}
