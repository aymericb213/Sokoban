package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	public State current_state;
	private State previous_state;
	private Push best_push;

	public Solver() {
		this.current_state=null;
		this.previous_state=null;
		this.best_push=null;
	}

	public Solver(State state) {
		this();
		this.current_state=state;
	}

	public double minmin(State s, int depth){
		if (depth==0 || s.isFinished()){
			return s.getValue();
		}
		double m = Double.POSITIVE_INFINITY;
    for (Push coup : s.getPushes()) {
				System.out.println("profondeur : " +depth+", coup : " + coup);
				System.out.println(s.getLevel());
        double val=minmin(s.push(coup), depth-1);
        if (val < m && !(s.getLevel().equals(this.previous_state.getLevel()))) {
            m = val;
						this.best_push=coup;
					}
		}
		System.out.println("Coup : " + this.best_push);
		return m;
	}

	public State getPreviousState() {
		return this.previous_state;
	}

	public void setPreviousState(State state) {
		this.previous_state=state;
	}

	public State getCurrentState() {
		return this.current_state;
	}

	public void setCurrentState(State state) {
		this.current_state=state;
	}

	public Push getBestPush(){
		return this.best_push;
	}

	public String toString() {
		String res="";
		Player p = (Player)this.current_state.getLevel().getPlayer();
		Node start = new Node(p.getX(), p.getY());
		Astar dep = new Astar();
		Node goal= new Node(this.best_push.getX(), this.best_push.getY());
		dep.setLevel(this.current_state.getLevel());
		dep.setStart(start);
		dep.setGoal(goal);
		dep.pathSearch();
		res+=dep.toString();
		switch (this.best_push.getDir()) {
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
