package ia;

import java.util.HashMap;
import java.lang.Math;

public class PathCost {

	private HashMap<Node,Integer> exploredMap;
	private HashMap<Node,Integer> heuristicMap;

	public PathCost() {
	  this.exploredMap = new HashMap<Node,Integer>();
	  this.heuristicMap = new HashMap<Node,Integer>();
	}

	public int currentDist(Node start, Node n) {
		if (n==start) {
			return 0;
		} else {
			return 1 + currentDist(start, n.getPred());
		}
	}

	public int manhattan(Node n, Node goal) {
		return Math.abs(goal.getX() - n.getX()) + Math.abs(goal.getY() - n.getY());
	}

	public HashMap<Node,Integer> getExplMap() {
		return this.exploredMap;
	}

	public void setExplValue(Node key, int newval) {
		this.exploredMap.put(key,newval);
	}

	public HashMap<Node,Integer> getHeurMap() {
		return this.heuristicMap;
	}

	public void setHeurValue(Node key, int newval) {
		this.heuristicMap.put(key,newval);
	}
}
