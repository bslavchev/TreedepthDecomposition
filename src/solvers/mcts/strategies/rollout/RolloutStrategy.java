package solvers.mcts.strategies.rollout;

import solvers.mcts.core.Node;

public interface RolloutStrategy {
	public int rollout(Node from);
}
