package solvers.mcts.tree;

import java.util.Set;

import solvers.mcts.core.Node;

public interface TreeStrategy {
	public Node select(Node from);
	public Node expand(Node from);
}
