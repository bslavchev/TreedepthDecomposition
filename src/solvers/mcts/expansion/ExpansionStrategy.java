package solvers.mcts.expansion;

import java.util.Set;

import solvers.mcts.core.Node;

public interface ExpansionStrategy {
	public Node expand(Node from);
}
