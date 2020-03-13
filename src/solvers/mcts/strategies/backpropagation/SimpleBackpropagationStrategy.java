package solvers.mcts.strategies.backpropagation;

import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;

public class SimpleBackpropagationStrategy implements BackpropagationStrategy {

	@Override
	public void backpropagate(Node from, int result) {
		HyperEdge parent = from.getParent();
		while(parent != null) {			
			parent.addVisit(result);
			Node parentNode = parent.getFrom();
			parentNode.addVisit();
			parent = parentNode.getParent();
		}

	}

}
