package ia;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import sokoban.*;

public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private Node start;
	private Node goal;
	private String path;
	private Board level;

	public Astar() {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		this.path="";
		this.start=new Node(0,0);
		this.goal=new Node(0,0);
		this.level=new Board();
	}

	public String PathSearch(Node start, Node goal) {
		PathCost evals= new PathCost();
		this.waitingList.add(this.start);
		evals.setExplValue(evals.initMap(this.level.getSize()));
		evals.setFullValue(evals.initMap(this.level.getSize()));
		evals.getFullMap().put(this.start, evals.manhattan(this.start,this.goal));
		while (!(this.waitingList.isEmpty())) {
			Node current= minimumCost(evals.getFullMap());
			if (current==this.goal) {
				return "yay";
			}
			this.waitingList.remove(current);
			this.exploredList.add(current);
		}
		return "";
	}

	public Node minimumCost(HashMap<Node,Double> m) {
		double min=Double.POSITIVE_INFINITY;
		Node n=new Node();
		for (Map.Entry<Node,Double> x : m.entrySet()) {
			if (x.getValue()<min) {
				min=x.getValue();
				n=x.getKey();
			}
		}
		return n;
	}

	public Node[] neighbours(Node n) {
		Node north = new Node(n.getX()-1,n.getY());
		Node south = new Node(n.getX()+1,n.getY());
		Node east = new Node(n.getX(),n.getY()+1);
		Node west = new Node(n.getX(),n.getY()-1);
		Node[] neighboursList= new Node[4];
		neighboursList[0]=north;
		neighboursList[0]=east;
		neighboursList[0]=south;
		neighboursList[0]=west;
		return neighboursList;
	}

	public Node getStart() {
		return this.start;
	}

	public Node getGoal() {
		return this.goal;
	}

	public Board getLevel() {
		return this.level;
	}

	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}
}
