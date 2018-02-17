package ia;

import java.util.HashMap;
import java.lang.Math;
import sokoban.*;

public class PathCost {

	private HashMap<Node,Double> exploredMap;
	private HashMap<Node,Double> fullPathMap;

	public PathCost() {
	  this.exploredMap = new HashMap<Node,Double>();
	  this.fullPathMap = new HashMap<Node,Double>();
	}

	public double currentDist(Node start, Node n) {
		/* j'ai rajouté || n==null */
		if (n==start || n==null) {
			return 0.;
		} else {
			System.out.println("start : "+ start.toString() +" getpred : " + n.toString());
			return 1. + currentDist(start, n.getPred());
		}
	}

	public double manhattan(Node n, Node goal) {
		return Math.abs(goal.getX() - n.getX()) + Math.abs(goal.getY() - n.getY());
	}

	public HashMap<Node,Double> initMap(int[] size) {
		int nbKeys=size[0]*size[1];
		double inf=Double.POSITIVE_INFINITY;
		HashMap<Node,Double> map=new HashMap<>(nbKeys);
		for (int i=0; i<size[0]; i++) {
			for (int j=0; j<size[1]; j++) {
				map.put(new Node(j,i), inf);
			}
		}
		return map;
	}

	public HashMap<Node,Double> getExplMap() {
		return this.exploredMap;
	}

	public void putExplValue(Node key, double newval) {
		this.exploredMap.put(key,newval);
	}

	public void setExplMap(HashMap<Node,Double> m) {
		this.exploredMap=new HashMap<Node,Double>(m);
	}

	public HashMap<Node,Double> getFullMap() {
		return this.fullPathMap;
	}

	public void putFullValue(Node key, double newval) {
		this.fullPathMap.put(key,newval);
	}

	public void setFullMap(HashMap<Node,Double> m) {
		this.fullPathMap=new HashMap<Node,Double>(m);
	}
}
