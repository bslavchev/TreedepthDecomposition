package solvers.mcts;

import core.DynamicComponent;
import core.StaticGraph;
import solvers.Solver;
import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;
import solvers.mcts.strategies.backpropagation.BackpropagationStrategy;
import solvers.mcts.strategies.rollout.RolloutStrategy;
import solvers.mcts.strategies.tree.TreeStrategy;

public class MCTS implements Solver{
	
	//TODO: convert from tree to DAG
	
	int timeLimit = 30;
	
	TreeStrategy tree;
	RolloutStrategy rollout;
	BackpropagationStrategy backpropagation;
	
	Node root;
	
	public MCTS(DynamicComponent dc, TreeStrategy tree, RolloutStrategy rollout, BackpropagationStrategy backpropagation) {
		this.tree = tree;
		this.rollout = rollout;
		this.backpropagation = backpropagation;
		this.root = new Node(dc);
	}
	
	public MCTS(DynamicComponent dc, int timeLimit, TreeStrategy tree, RolloutStrategy rollout, BackpropagationStrategy backpropagation) {
		this(dc, tree, rollout, backpropagation);
		this.timeLimit = timeLimit;				
	}

	public StaticGraph getSolution() {
		long startTime = System.currentTimeMillis();
		
		while(startTime + timeLimit*1000 < System.currentTimeMillis()) {
			Node selected = tree.select(root);
			
			backpropagation.backpropagate(selected, rollout.rollout(selected));
		}
		
		return getActionPath();
	}
	
	private StaticGraph getActionPath() {
		//TODO think very long and hard about whether this is correct
		StaticGraph tree = new StaticGraph(root.getState().getSize());
		
		HyperEdge bestEdge = root.selectBest();		
		tree.setRoot(bestEdge.getAction());
		
		recurse(tree,bestEdge);
		
		return tree;
	}
	
	private void recurse(StaticGraph graph, HyperEdge edge) {		
		int action = edge.getAction();
		
		for (Node childNode: edge.getTo()) {
			HyperEdge bestChild = childNode.selectBest();
			int action2 = bestChild.getAction();
					
			graph.addEdge(action,action2);
			
			recurse(graph,bestChild);
		}
	}	
}
