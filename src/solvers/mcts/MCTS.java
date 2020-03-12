package solvers.mcts;

import core.DynamicComponent;
import solvers.Solver;
import solvers.mcts.backpropagation.BackpropagationStrategy;
import solvers.mcts.expansion.ExpansionStrategy;
import solvers.mcts.rollout.RolloutStrategy;
import solvers.mcts.selection.SelectionStrategy;

public abstract class MCTS implements Solver{
	
	SelectionStrategy selection;
	ExpansionStrategy expansion;
	RolloutStrategy rollout;
	BackpropagationStrategy backpropagation;
	
	DynamicComponent dc;
	int timeLimit = 30;
}
