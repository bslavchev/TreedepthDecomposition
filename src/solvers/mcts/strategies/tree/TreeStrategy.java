package solvers.mcts.strategies.tree;

import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;

public interface TreeStrategy {
	public Node select(Node from);
	public HyperEdge expand(Node from);
}
