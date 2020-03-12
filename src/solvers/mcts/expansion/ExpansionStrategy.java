package solvers.mcts.expansion;

import java.util.Set;

import solvers.mcts.core.Node;

public interface ExpansionStrategy {
	public Set<Node> expand(Node from);
}
