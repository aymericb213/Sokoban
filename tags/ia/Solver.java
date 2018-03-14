package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	public State current_state;
	private State previous_state;
	private Push best_push;
	private int d;

	public Solver() {
		this.current_state=null;
		this.previous_state=null;
		this.best_push=null;
		this.d=3;
	}

	public Solver(State state) {
		this();
		this.current_state=state;
	}

	public double minmin(State s, int depth){
		if (deth!=this.d && s.getLevel().equals(this.previous_state.getLevel())){
			System.out.println("pareil");
			return Double.POSITIVE_INFINITY;
		}

		if ((depth==0 || s.isFinished())){
			System.out.println("Debut");
			double value = s.getValue();
			System.out.println("Fin");
			System.out.println(value);

			return value;
		}
		double m = Double.POSITIVE_INFINITY;
    for (Push coup : s.getPushes()) {
        double val=minmin(s.push(coup), depth-1);
        if (val < m) {
					//System.out.println(!(s.getLevel().equals(this.previous_state.getLevel())));
            m = val;
						this.best_push=coup;
					}
		}
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
