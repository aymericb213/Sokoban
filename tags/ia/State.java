package ia;

import sokoban.*;
import java.util.ArrayList;

public class State {

	private Board level;
	private ArrayList<Push> lPush;
	private Push generator;
	private String previous_key;
	private double value;//à minimiser

	public State(Board level) {
		this.level=level;
		this.lPush=new ArrayList<Push>();
		this.value=0.;
		this.previous_key=null;
	}


	public void accessiblePushes() {
		this.lPush.clear();
		ArrayList<Block> lCrates=this.level.getCrates();
		Player p = (Player)this.level.getPlayer();
		Node start = new Node(p.getX(), p.getY());
		for (Block c : lCrates) {
			Astar compN=new Astar();
			compN.setLevel(this.level);
			ArrayList<Node> crateNeighbours=compN.neighbours(new Node(((Crate) c).getX(),((Crate)c).getY()));
			for (Node n : crateNeighbours) {
				Astar compP = new Astar();
				compP.setLevel(this.level);
				compP.setStart(start);
				compP.setGoal(n);
				compP.pathSearch();
				if (!(compP.getPath().isEmpty())) {
					Push newpush= new Push(n.getX(),n.getY());
					newpush.setDir(((Crate) c).getX()-n.getX(),((Crate)c).getY()-n.getY());
					this.lPush.add(newpush);
				}
			}
		}
	}

	public void computeValue() {
		this.value=0;
		if (this.level.isFinished() && !(this.level.allPlaced())) {
				this.value=Double.POSITIVE_INFINITY;
		} else {
			for (Block c : this.level.getCrates()) {
				PathCost d = new PathCost();
				double minDist=Double.POSITIVE_INFINITY;
				for (Block o : this.level.getObjectives()) {
					double testDist=d.manhattan(new Node(((Crate) c).getX(),((Crate)c).getY()), new Node(((Objective) o).getX(),((Objective)o).getY()));
					if (testDist<minDist) {
						minDist=testDist;
					}
				}
				this.value+=minDist;
			}
		}
	}

	public void hamming() {
		this.value=0;
		if (this.level.isFinished() && !(this.level.allPlaced())) {
				this.value=Double.POSITIVE_INFINITY;
		} else {
			for (Block c : this.level.getCrates()) {
				if (!(((Crate)c).isPlaced())) {
					this.value+=1.;
				}
			}
		}
	}

	public State push(Push move) {
		Player p = (Player)this.level.getPlayer();
		Node start = new Node(p.getX(), p.getY());
		Node push_position = new Node(move.getX(),move.getY());
		Astar to_push_position = new Astar();
		to_push_position.setLevel(this.level);
		to_push_position.setStart(start);
		to_push_position.setGoal(push_position);
		to_push_position.pathSearch();
		for (int i=1; i<to_push_position.getPath().size(); i++) {//déplacement à la position de poussée
			ArrayList<Integer> nextMove = new ArrayList<>();
			nextMove.add(to_push_position.getPath().get(i).getX()-p.getX());
			nextMove.add(to_push_position.getPath().get(i).getY()-p.getY());
			p.move(this.level,nextMove);
			}
		p.move(this.level,move.getDir().getCoords());//poussée de la caisse
		State succ_state = new State(this.level);
		return succ_state;
	}

	public State tp(Push move) {
		Player p = (Player)this.level.getPlayer();
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(move.getX()-p.getX());
		path.add(move.getY()-p.getY());
		p.move(this.level,path);
		p.move(this.level,move.getDir().getCoords());
		State succ_state = new State(this.level);
		return succ_state;
	}

	public boolean isFinished(){
		return this.level.isFinished();
	}

	public boolean allPlaced(){
		return this.level.allPlaced();
	}

/**
	* Mutateur du niveau.
	* @param newBoard
	* Le nouveau niveau.
*/
	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}

	public Push getGenerator() {
		return this.generator;
	}

	public void setGenerator(Push p){
		this.generator = p;
	}

	public Board getLevel() {
		return this.level;
	}

	public ArrayList<Push> getPushes() {
		this.accessiblePushes();
		return this.lPush;
	}

	public double getValue() {
		this.computeValue();
		return this.value;
	}

	public double getHammingDist() {
		this.hamming();
		return this.value;
	}

	public String getPreviousKey(){
		return this.previous_key;
	}

	public void setPreviousKey(String key){
		this.previous_key=key;
	}

 public String toString() {
	 Player p = (Player)this.level.getPlayer();
	 String chCrates="";
	 for (Block c : this.level.getCrates()) {
		 chCrates+="(" + ((Crate) c).getX()+","+((Crate)c).getY() + ") ; ";
	 }
	 return "Position du joueur : (" + p.getX() + "," + p.getY() + ")\nPosition des caisses : " + chCrates + "\nListe des coups : " + this.getPushes() + "\nEtat final : " + this.isFinished() + "\nValeur : " + this.value;
 }
}
