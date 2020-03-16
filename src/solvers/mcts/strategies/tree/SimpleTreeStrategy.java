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
		
		Set<HyperEdge> bestEdges = new HashSet<HyperEdge>();
		
		boolean expansion = false;
		
		if(from.getState().getIDsOfActiveVertices().size() == 1)
			System.out.println('e');
		
		if(children.size() < from.getState().getSize()) {
			bestEdges.add(expand(from));
			expansion = true;
		}	
				
		if(bestEdges.size() == 0) {		
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
		
		if(expansion)
			return worstNodes.get(randomIndex);		

		return select(worstNodes.get(randomIndex));
	}

	@Override
	public HyperEdge expand(Node from) {
		List<Integer> possibilities = from.getUnexpandedActions();
		
		if(possibilities.size() ==0)
			System.out.println('e');
		
		Integer action = possibilities.get(MCTS.random.nextInt(possibilities.size()));
		
		from.expandAction(action);
		
		return from.applyAction(action);
	}

}
