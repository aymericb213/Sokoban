package ia;

import sokoban.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Solver {

	private State current_state;
	private State previous_state;
	private Push best_push;
	private HashMap<String,Board> explored_states;
	private HashMap<String,State> explored_list;
	private PriorityQueue<String> p_queue;
	private HashMap<String,State> waiting_dico;
	public static final Logger debug = Logger.getLogger("debug IA");

	public Solver() {
		debug.setLevel(Level.ALL);
		this.previous_state=null;
		this.current_state=null;
		this.best_push=null;
		this.explored_list = new HashMap<String,State>();
		this.p_queue = new PriorityQueue<String>(11, new Comparator<String>() {
																											@Override
																											public int compare(String first, String second) {
																												String[] s_first = first.split("/");
																												String[] s_second = second.split("/");
																												Double value_first = Double.parseDouble(s_first[s_first.length-1]);
																												Double value_second = Double.parseDouble(s_second[s_second.length-1]);
																												if (value_first < value_second) {
																													return -1;
																												} else if (value_first == value_second) {
																													return 0;
																												} else {
																													return 1;
																												}
																											}
																										});
		this.waiting_dico = new HashMap<String,State>();
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

	public ArrayList<Push> bruteForce() {
		long timer = 0;
		this.waiting_dico.clear();
		String init_key=this.createKey(this.current_state);
		this.waiting_dico.put(init_key,this.current_state);
		this.p_queue.offer(init_key);
		while (this.p_queue.size()!=0 || timer<150000) {
			long start = System.currentTimeMillis();
			//System.out.println("file de priorité : " +this.p_queue);
			//System.out.println(this.p_queue.toString());
			//System.out.println(this.explored_list.toString());
			// System.out.println("currentKey : "+this.p_queue.get(0));
			State current= this.waiting_dico.get(this.p_queue.poll());
			// System.out.println(current.getLevel());
			if (current.allPlaced()) {
				System.out.println(current.getLevel());
				System.out.println(current);
				timer+=(System.currentTimeMillis()-start);
				System.out.println("Résolu : " + timer);
				return buildFullPath(current);
			}
			String current_key = this.createKey(current);
			this.waiting_dico.remove(current_key);
			// System.out.println(current);
			// System.out.println(this.p_queue.contains(createKey(current)));
			this.explored_list.put(current_key, current);
			for (Push p : current.getPushes()) {

				ArrayList<String> gameboard_save=current.getLevel().createArrayList();
				Board b= new Board();
				b.createGrid(gameboard_save);
				State new_current=new State(b);
				String successor_key=this.createKey(new_current.push(p));

				if (!((successor_key.contains("Infinity")) || (this.explored_list.containsKey(successor_key)) || (this.waiting_dico.containsKey(successor_key)))) {
					//System.out.println("waiting list : " +this.p_queue);
					//System.out.println("wait : "+(this.waiting_dico.containsKey(createKey(new_current.push(p)))));
					State e = new_current.push(p);
					e.setPreviousKey(current_key);
					e.setGenerator(p);
					this.waiting_dico.put(successor_key, e);
					this.p_queue.offer(successor_key);
				}
			}
			timer+=(System.currentTimeMillis()-start);
			// System.out.println(this.p_queue);
		}
		System.out.println("Trop long : " + timer);
		return null;
	}

	public ArrayList<Push> buildFullPath(State end) {
		ArrayList<Push> bfp = new ArrayList<>();
		bfp.add(end.getGenerator());
		String end_key=end.getPreviousKey();
		while (end_key!=null){
			end = this.explored_list.get(end_key);
			bfp.add(0,end.getGenerator());
			end_key=end.getPreviousKey();
		}
		this.explored_list.clear();
		this.waiting_dico.clear();
		this.p_queue.clear();
		bfp.remove(0);
		return bfp;
	}

	public String createKey(State s){
		String key = "";
		ArrayList<Block> list_crate=s.getLevel().getCrates();
		for(Block c : list_crate){
			key+=((Crate) c).getX()+";"+((Crate) c).getY()+"/";
		}
		key+=s.getHammingDist();
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
				State new_current=new State(b);
				double val=minmin(new_current.push(coup), depth-1);
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
