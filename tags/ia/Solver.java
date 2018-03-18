package ia;

import sokoban.*;
import java.util.ArrayList;
import java.util.logging.*;

public class Solver {

	public State current_state;
	private State previous_state;
	private Push best_push;
	private int d;
	public static final Logger debug = Logger.getLogger("debug IA");

	public Solver() {
		debug.setLevel(Level.ALL);
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
		debug.entering("Solver","minmin", new Object[]{s,depth});
		debug.info("\n"+s.getLevel().toString());

		if (depth!=this.d && s.getLevel().equals(this.previous_state.getLevel())){
			debug.warning("Mouvement inutile");
			return Double.POSITIVE_INFINITY;
		}

		if (depth==0 || s.isFinished()){
			double value = s.getValue();
			debug.info("Feuille atteinte ; valeur : " + value);
			return value;
		}

		double m = Double.POSITIVE_INFINITY;
		ArrayList<Push> l=s.getPushes();
		debug.info("Liste des coups : " + l);
    for (Push coup : l) {
        double val=minmin(s.push(coup), depth-1);
				debug.info("profondeur : " + depth + "; recherche pour le coup : " + coup + "; value : " + val);
        if (val < m) {
					debug.fine("Mise à jour coup optimal");
          m = val;
					this.best_push=coup;
					}
		}
		debug.exiting("Solver","minmin", this.best_push);
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
