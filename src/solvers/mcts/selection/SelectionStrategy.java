package solvers.mcts.selection;

import java.util.Set;

import solvers.mcts.core.Node;

public interface SelectionStrategy {
	public Set<Node> select(Set<Node> from);
}
