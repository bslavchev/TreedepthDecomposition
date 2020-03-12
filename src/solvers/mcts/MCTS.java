package solvers.mcts;

import java.util.Set;

import core.Decomposition;
import core.DynamicComponent;
import solvers.Solver;

public class MCTS implements Solver{
	
	DynamicComponent dc;
	int timeLimit = 30;
	
	public MCTS(DynamicComponent dc, int timeLimit) {
		this.dc = dc;
		this.timeLimit = timeLimit;
	}

	@Override
	public Decomposition getSolution() {
		// TODO Auto-generated method stub
		return null;
	}
}
