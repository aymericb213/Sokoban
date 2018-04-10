package ia;

import sokoban.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.*;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Solver {

	private Board initial_board;
	private State current_state;
	private State previous_state;
	private Push best_push;
	private HashMap<String,Board> explored_states;
	private HashMap<String,State> explored_list;
	private PriorityQueue<String> p_queue;
	private HashMap<String,State> waiting_dico;
	private ArrayList<Push> movelist;
	public static final Logger debug = Logger.getLogger("debug IA");
	private ArrayList<ArrayList<Integer>> move_relatif;

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
		this.movelist= new ArrayList<Push>();
		this.move_relatif= new ArrayList<ArrayList<Integer>>();
	}

	public Solver(State state) {
		this();
		this.current_state=state;
		ArrayList<String> gameboard_save=this.current_state.getLevel().createArrayList();
		Board b= new Board();
		b.createGrid(gameboard_save);
		this.initial_board=b;
	}

	public void aStarSolve(boolean anytime) {
		long timer = 0;
		this.waiting_dico.clear();
		String init_key=this.createKey(this.current_state);
		this.waiting_dico.put(init_key,this.current_state);
		this.p_queue.offer(init_key);
		while ((this.p_queue.size()!=0) && (timer<600000) && (this.explored_list.size()<180000)) {
			long start = System.currentTimeMillis();
			State current= this.waiting_dico.get(this.p_queue.poll());
			if (((anytime) && (Thread.interrupted())) || ((!(anytime)) && (current.allPlaced()))) {
				buildFullPath(current);
				break;
			}

			String current_key = this.createKey(current);
			this.waiting_dico.remove(current_key);
			this.explored_list.put(current_key, current);
			for (Push p : current.getPushes()) {

				ArrayList<String> gameboard_save=current.getLevel().createArrayList();
				Board b= new Board();
				b.createGrid(gameboard_save);
				State new_current=new State(b);
				String successor_key=this.createKey(new_current.tp(p));

				if (!((successor_key.contains("Infinity")) || (this.explored_list.containsKey(successor_key)) || (this.waiting_dico.containsKey(successor_key)))) {
					State e = new_current.tp(p);
					e.setPreviousKey(current_key);
					e.setGenerator(p);
					this.waiting_dico.put(successor_key, e);
					this.p_queue.offer(successor_key);
				}
			}
			timer+=(System.currentTimeMillis()-start);
		}
	}

	public void buildFullPath(State end) {
		this.movelist= new ArrayList<Push>();
		movelist.add(end.getGenerator());
		String end_key=end.getPreviousKey();
		while (end_key!=null){
			end = this.explored_list.get(end_key);
			movelist.add(0,end.getGenerator());
			end_key=end.getPreviousKey();
		}
		this.explored_list.clear();
		this.waiting_dico.clear();
		this.p_queue.clear();
		movelist.remove(0);
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
				double val=minmin(new_current.tp(coup), depth-1);
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

	public ArrayList<ArrayList<Integer>> getMoveRelatif(){
		this.move_relatif.clear();
		ArrayList<Integer> tempList= new ArrayList<Integer>();
		String[] path=this.toString().split("");
		for(String x : path){
			tempList=new ArrayList<Integer>();
			if (x.equals("u") || x.equals("U")){
				tempList.add(-1);
				tempList.add(0);
			}else if (x.equals("d") || x.equals("D")){
				tempList.add(1);
				tempList.add(0);
			}else if (x.equals("r") || x.equals("R")){
				tempList.add(0);
				tempList.add(1);
			}else if (x.equals("l") || x.equals("L")){
				tempList.add(0);
				tempList.add(-1);
			}
			this.move_relatif.add(tempList);
		}
		return this.move_relatif;
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

	public ArrayList<Push> getMovelist() {
		return this.movelist;
	}

	public String toString() {
		String res="";
		State cb = new State(this.initial_board);
		for (Push move : this.movelist) {
			Player p = (Player)cb.getLevel().getPlayer();
			Node start = new Node(p.getX(), p.getY());
			Astar dep = new Astar();
			dep.setLevel(cb.getLevel());
			dep.setStart(start);
			Node goal= new Node(move.getX(), move.getY());
			dep.setGoal(goal);
			dep.pathSearch();
			res+=dep.toString();
			switch (move.getDir()) {
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
			cb.push(move);

		}
		return res;
	}
}
