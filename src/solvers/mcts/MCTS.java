package solvers.mcts;

import java.util.HashSet;
import java.util.Set;

import core.Decomposition;
import core.DecompositionNode;
import core.DynamicComponent;
import core.StaticGraph;
import solvers.Solver;
import solvers.mcts.backpropagation.BackpropagationStrategy;
import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;
import solvers.mcts.expansion.ExpansionStrategy;
import solvers.mcts.rollout.RolloutStrategy;
import solvers.mcts.selection.SelectionStrategy;

public class MCTS implements Solver{
	
	int timeLimit = 30;
	
	SelectionStrategy selection;
	ExpansionStrategy expansion;
	RolloutStrategy rollout;
	BackpropagationStrategy backpropagation;
	
	Node root;
	Set<Node> rootAsSet = new HashSet<Node>();
	
	public MCTS(DynamicComponent dc) {
		this.root = new Node(dc);
		this.rootAsSet.add(root);
	}
	
	public MCTS(DynamicComponent dc, int timeLimit) {
		this(dc);
		this.timeLimit = timeLimit;				
	}

	public Decomposition getSolution() {
		long startTime = System.currentTimeMillis();
		
		while(startTime + timeLimit*1000 < System.currentTimeMillis()) {
			Node selected = selection.select(rootAsSet);			
			Set<Node> expanded = expansion.expand(selected);
			
			selected = null;
			int largestElement = -1;
			for (Node node : expanded) {
				int size = node.getState().getSize();
				if(size > largestElement) {
					selected = node;
					largestElement = size;
				}
			}
			
			backpropagation.backpropagate(selected, rollout.rollout(selected));
		}
		
		return null;
	}
	
	private StaticGraph getActionPath() {
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
