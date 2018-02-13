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
		evals.setExplMap(evals.initMap(this.level.getSize()));
		evals.setFullMap(evals.initMap(this.level.getSize()));
		evals.putFullValue(this.start, evals.manhattan(this.start,this.goal));
		while (!(this.waitingList.isEmpty())) {
			Node current= minimumCost(evals.getFullMap());
			if (current==this.goal) {
				return "yay";
			}
			this.waitingList.remove(current);
			this.exploredList.add(current);
			for (Node n : neighbours(current)) {
				if (this.exploredList.contains(n)) {
					continue;
				}
				if (!(this.waitingList.contains(n))) {
					this.waitingList.add(n);
				}
				double testG = evals.currentDist(this.start,n) + 1.0;
				if (testG>=evals.getExplMap().get(n)) {
					continue;
				} else {
					n.setPred(current);
					evals.putExplValue(n, testG);
					evals.putFullValue(n, evals.getExplMap().get(n)+evals.manhattan(n,this.goal));
				}
			}
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
		Node[] neighboursList= new Node[4];
		neighboursList[0]=new Node(n.getX()-1,n.getY());//up
		neighboursList[1]=new Node(n.getX(),n.getY()+1);//right
		neighboursList[2]=new Node(n.getX()+1,n.getY());//down
		neighboursList[3]=new Node(n.getX(),n.getY()-1);//left
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
