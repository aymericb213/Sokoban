package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	private State state;
	private Push bestPush;

	public Solver() {
		this.state=null;
		this.bestPush=null;
	}

	public Solver(State state) {
		this();
		this.state=state;
	}

	public double minmin(State s, int depth){
		if (depth==0 || s.isFinished()){
			return s.getValue();
		}
		double m = Double.POSITIVE_INFINITY;
    for (Push coup : s.getPushes()) {
				System.out.println(s.getLevel());
        double val=minmin(s.push(coup), depth-1);
        if (val < m) {
            m = val;
						this.bestPush=coup;
					}
		}
		System.out.println("Coup : " + this.bestPush);
		return m;
	}

	public State getState() {
		return this.state;
	}

	public Push getBestPush(){
		return this.bestPush;
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
