package solvers.mcts.strategies.tree;

import solvers.mcts.core.Node;

public interface TreeStrategy {
	public Node select(Node from);
	public Node expand(Node from);
}
