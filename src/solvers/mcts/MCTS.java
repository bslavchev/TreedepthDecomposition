package solvers.mcts;

import java.util.Random;

import core.DynamicComponent;
import core.StaticDirectedGraph;
import core.StaticGraph;
import solvers.Solver;
import solvers.mcts.core.HyperEdge;
import solvers.mcts.core.Node;
import solvers.mcts.strategies.backpropagation.BackpropagationStrategy;
import solvers.mcts.strategies.backpropagation.SimpleBackpropagationStrategy;
import solvers.mcts.strategies.rollout.RolloutStrategy;
import solvers.mcts.strategies.rollout.SimpleRolloutStrategy;
import solvers.mcts.strategies.tree.SimpleTreeStrategy;
import solvers.mcts.strategies.tree.TreeStrategy;
import util.GrGraphReader;

public class MCTS implements Solver{
	
	//TODO: convert from tree to DAG
	
	int timeLimit = 30;
	
	public static final double C = Math.sqrt(2);
	
	public static final Random random = new Random();
	
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

	public StaticDirectedGraph getSolution() {
		boolean cont = true;
		
		System.out.println("mcts starting for " + timeLimit + "s");
		long startTime = System.currentTimeMillis();
		
		long endTime = startTime + timeLimit*1000;
		
		int iterations = 0;
		
		while(endTime > System.currentTimeMillis() || cont) {
			Node selected = tree.select(root);
			
			backpropagation.backpropagate(selected, rollout.rollout(selected));
			
			iterations++;
		}
		
		System.out.println(" iterations: " + iterations);
		
		return getActionPath();
	}
	
	private StaticDirectedGraph getActionPath() {
		//TODO think very long and hard about whether this is correct
		StaticDirectedGraph tree = new StaticDirectedGraph(root.getState().getSize());
		
		HyperEdge bestEdge = root.selectBest();		
		tree.setRoot(bestEdge.getAction());
		
		recurse(tree,bestEdge);
		
		return tree;
	}
	
	//TODO: add checks for fully-expanded subtrees
	
	private void recurse(StaticDirectedGraph graph, HyperEdge edge) {		
		int action = edge.getAction();
		
		for (Node childNode: edge.getTo()) {
			HyperEdge bestChild = childNode.selectBest();
			
			if(bestChild == null)
				continue;
			
			int action2 = bestChild.getAction();
					
			graph.addEdge(action,action2);
			
			recurse(graph,bestChild);
		}
	}	
	
	public static void main(String[] args) throws Exception {
		StaticGraph graph = GrGraphReader.readGraph("graphs/e/exact_001.gr");
		DynamicComponent dc = new DynamicComponent(graph);
		
		TreeStrategy ts = new SimpleTreeStrategy();
		RolloutStrategy rs = new SimpleRolloutStrategy();
		BackpropagationStrategy bs = new SimpleBackpropagationStrategy();
		
		MCTS mcts = new MCTS(dc, 5, ts, rs, bs);
		StaticDirectedGraph solution = mcts.getSolution();
		
		System.out.println("mcts done");
		
		System.out.println(solution.printAsSolution());
	}
}
