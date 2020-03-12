package core;

import java.util.List;

public class DecompositionNode {
	int action;
	List<DecompositionNode> children;
	
	public DecompositionNode(int action) {
		this.action = action;
	}
}
