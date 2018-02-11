package ia;

import java.util.ArrayList;
import sokoban.*;

public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private Node start;
	private Node goal;
	private String path;

	public Astar() {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		this.path="";
		this.start=new Node(0,0);
		this.goal=new Node(0,0);
	}

	public String PathSearch(Node start, Node goal) {
		PathCost evals= new PathCost();
		waitingList.add(start);
		return "";

	}

	public Node getStart() {
		return this.start;
	}

	public Node getGoal() {
		return this.goal;
	}
}
