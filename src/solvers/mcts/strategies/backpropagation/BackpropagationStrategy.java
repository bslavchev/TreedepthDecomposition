package solvers.mcts.strategies.backpropagation;

import solvers.mcts.core.Node;

public interface BackpropagationStrategy {
	public void backpropagate(Node from, int result);
}
