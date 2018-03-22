package ia;

import sokoban.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;
import java.util.Random;

public class Solver {

	private State current_state;
	private Push best_push;
	private HashMap<String,Board> explored_states;
	private ArrayList<State> explored_list;
	private ArrayList<State> waiting_list;
	private ArrayList<Push> movelist;
	public static final Logger debug = Logger.getLogger("debug IA");

	public Solver() {
		debug.setLevel(Level.ALL);
		this.current_state=null;
		this.best_push=null;
		this.explored_list = new ArrayList<State>();
		this.waiting_list = new ArrayList<State>();
		this.movelist=new ArrayList<Push>();
		this.explored_states=new HashMap<String,Board>();
	}

	public Solver(State state) {
		this();
		this.current_state=state;
	}

	public void solve() {
		Board debut = this.current_state.getLevel();
		ArrayList<String> gameboard_save=debut.createArrayList();
		Board b= new Board();
		b.createGrid(gameboard_save);
		Random r = new Random();
		while (!(this.current_state.allPlaced())) {
			ArrayList<Push> l =this.current_state.getPushes();
			System.out.println(this.current_state.getLevel());
			Push c = l.get(r.nextInt(l.size()));
			State newstate= this.current_state.push(c);
			if (newstate.isFinished() && !(newstate.allPlaced())) {
				this.current_state=new State(b);
			} else {
				this.current_state=newstate;
			}
		}
		System.out.println(this.current_state.getLevel());
		System.out.println("résolu");
	}

	public boolean bruteForce() {
		this.waiting_list.add(this.current_state);
		while (!(this.waiting_list.isEmpty())) {
			State current= this.waiting_list.get(0);
			if (current.allPlaced()) {
				return true;
			}
			this.waiting_list.remove(current);
			this.explored_list.add(current);
			for (Push p : current.getPushes()) {
				if (this.explored_list.contains(current.push(p))) {
					continue;
				}
				if (!(this.waiting_list.contains(current.push(p)))) {
					this.waiting_list.add(current.push(p));
				}
			}
		}
		return false;
	}

	public double minmin(State s, int depth){
		String board_to_string = s.getLevel().toString();
		debug.entering("Solver","minmin", new Object[]{s,depth});
		debug.info("\n"+ board_to_string);

		if (this.explored_states.get(board_to_string)!=null){
			debug.warning("Mouvement inutile");
			return Double.POSITIVE_INFINITY;
		} else {
			this.explored_states.put(board_to_string,s.getLevel());
		}

		if (depth==0 || s.isFinished()){
			double value = s.getValue();
			if (value==0) {

			}
			debug.info("Feuille atteinte ; valeur : " + value);
			return value;
		}

		double m = Double.POSITIVE_INFINITY;
		ArrayList<Push> l=s.getPushes();
		debug.info("Liste des coups : " + l);
    for (Push coup : l) {

				ArrayList<String> gameboard_save=s.getLevel().createArrayList();
				Board b= new Board();
				b.createGrid(gameboard_save);
				State s1=new State(b);

        double val=minmin(s1.push(coup), depth-1);
				debug.info("profondeur : " + depth + "; recherche pour le coup : " + coup + "; value : " + val);
        if (val <= m) {
					debug.fine("Mise à jour coup optimal");
          m = val;
					this.best_push=coup;
					}
		}
		debug.exiting("Solver","minmin", this.best_push);
		return m;
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
