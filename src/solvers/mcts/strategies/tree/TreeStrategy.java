package solvers.mcts.strategies.tree;

import java.util.Set;

import solvers.mcts.core.Node;

public interface TreeStrategy {
	public Node select(Node from);
	public Set<Node> expand(Node from);
}
