package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	public State state;
	public Push bestPush;
	public ArrayList<Node> coup;

	public Solver() {
		this.state=null;
		this.bestPush=null;
	}

	public Solver(State state) {
		this();
		this.state=state;
	}

	public void minmin(){

	}

	public ArrayList<Node> getCoup(){
		return this.coup;
	}

	public String toString() {
		String res="";
		Player p = (Player)this.state.getLevel().getPlayer();
		Node start = new Node(p.getX(), p.getY());
		Astar dep = new Astar();
		Node goal= new Node(this.bestPush.getX(), this.bestPush.getY());
		dep.setLevel(this.state.getLevel());
		dep.setStart(start);
		dep.setGoal(goal);
		dep.pathSearch();
		res+=dep.toString();
		switch (this.bestPush.getDir()) {
			case UP:
				res+="U";
				break;
			case DOWN:
				res+="D";
				break;
			case LEFT:
				res+="L";
				break;
			case RIGHT:
				res+="R";
				break;
		}
		return res;
	}
}
