package solvers.mcts.strategies.rollout;

import java.util.ArrayList;
import java.util.Set;

import core.DynamicComponent;
import solvers.mcts.MCTS;
import solvers.mcts.core.Node;

public class SimpleRolloutStrategy implements RolloutStrategy {

	@Override
	public int rollout(Node from) {
		DynamicComponent state = from.getState().copy();
		
		ArrayList<Integer> potentialActions = (ArrayList<Integer>) from.getUnexpandedActions().clone();
		
		int actionsToTake = (int) Math.ceil(potentialActions.size()/2);
		
		for(int i = 0; i < actionsToTake; i++) {
			int selectedIndex = MCTS.random.nextInt(potentialActions.size());		
			//TODO: check all places where I remove Integer from a list. Does it remove the element, or the element at the index?
			
			Integer action = potentialActions.get(selectedIndex);
			
			potentialActions.remove(selectedIndex);	
			
			state.disableVertex(action, false);			
			}
		
		int biggest = -1;
		Set<DynamicComponent> components = state.check();
		
		for (DynamicComponent dc : components)
			if(dc.getSize() > biggest)
				biggest = dc.getSize();
		
		return (biggest+actionsToTake)/(from.getState().getSize());
	}

}
