package solvers.mcts;

import core.Decomposition;
import core.DynamicComponent;

public class AlphaMCTS extends MCTS {
	
	public AlphaMCTS(DynamicComponent dc, int timeLimit) {
		this.dc = dc;
		this.timeLimit = timeLimit;
	}

	@Override
	public Decomposition getSolution() {
		// TODO Auto-generated method stub
		return null;
	}

}
