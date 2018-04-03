package ia;

import sokoban.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;
import java.util.Random;

public class Solver {

	private State current_state;
	private State previous_state;
	private Push best_push;
	private HashMap<String,Board> explored_states;
	private HashMap<String,State> explored_list;
	private ArrayList<String> waiting_list;
	private HashMap<String,State> waiting_dico;
	private ArrayList<Push> movelist;
	public static final Logger debug = Logger.getLogger("debug IA");

	public Solver() {
		debug.setLevel(Level.ALL);
		this.previous_state=null;
		this.current_state=null;
		this.best_push=null;
		this.explored_list = new HashMap<String,State>();
		this.waiting_list = new ArrayList<String>();
		this.waiting_dico = new HashMap<String,State>();
		this.movelist=new ArrayList<Push>();
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
		this.waiting_dico.clear();
		this.waiting_dico.put(createKey(this.current_state),this.current_state);
		this.waiting_list.add(createKey(this.current_state));
		while (!(this.waiting_list.isEmpty())) {
			// System.out.println("===================================");
			// System.out.println("debut while");
			// System.out.println("===================================");
			//
			// System.out.println("waiting list : " +this.waiting_list);
			//System.out.println(this.waiting_list.toString());
			//System.out.println(this.explored_list.toString());
			// System.out.println("currentKey : "+this.waiting_list.get(0));
			State current= this.waiting_dico.get(this.waiting_list.get(0));
			// System.out.println(current.getLevel());
			if (current.allPlaced()) {
				System.out.println(current.getLevel());
				System.out.println(current);
				return true;
			}
			this.waiting_dico.remove(createKey(current));
			// System.out.println(current);
			// System.out.println(this.waiting_list.contains(createKey(current)));
			this.waiting_list.remove(0);
			this.explored_list.put(createKey(current), current);
			for (Push p : current.getPushes()) {

				ArrayList<String> gameboard_save=current.getLevel().createArrayList();
				Board b= new Board();
				b.createGrid(gameboard_save);
				State s1=new State(b);

				//System.out.println("push : "+p);
				//System.out.println("explo : "+(this.explored_list.containsKey(createKey(s1.push(p)))));
				if (!(createKey(s1.push(p)).contains("y"))){
					if (!(this.explored_list.containsKey(createKey(s1.push(p))))) {
						//System.out.println("waiting list : " +this.waiting_list);
						//System.out.println("wait : "+(this.waiting_dico.containsKey(createKey(s1.push(p)))));

						if (!(this.waiting_dico.containsKey(createKey(s1.push(p))))) {

							this.waiting_dico.put(createKey(s1.push(p)), s1.push(p));
							this.waiting_list.add(createKey(s1.push(p)));
						}
					}
				}
			}
			// System.out.println("===================================");
			// System.out.println(this.waiting_list);
			// System.out.println("===================================");
			// System.out.println("fin while");
			// System.out.println("===================================");
		}
		return false;
	}

	public String createKey(State s){
		String key = "";
		ArrayList<Block> list_crate=s.getLevel().getCrates();
		for(Block c : list_crate){
			key+=((Crate) c).getX()+";"+((Crate) c).getY()+"/";
		}
		key+=s.getValue();
		return key;
	}

	public double minmin(State s, int depth){

			debug.entering("Solver","minmin", new Object[]{s,depth});
			debug.info("\n"+s.getLevel().toString());
			if (depth!=3 && s.getLevel().toString().equals(this.previous_state.getLevel().toString())){
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
