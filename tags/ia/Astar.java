package ia;

import java.util.ArrayList;
import sokoban.*;

public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private String path;

	public Astar(Board b) {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		Player p = (Player)b.getPlayer();
		Node start = new Node(p.getX(), p.getY());
		waitingList.add(start);
		this.path="";
	}
}
