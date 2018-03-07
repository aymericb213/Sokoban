package ia;

import sokoban.*;
import java.util.ArrayList;

public class State {

	private Board level;
	private ArrayList<Push> lPush;
	private double value;//à minimiser

	public State(Board level) {
		this.level=level;
		this.lPush=new ArrayList<Push>();
	}

	public void accessiblePushes() {
		ArrayList<Block> lCrates=this.level.getCrates();
		Astar comp= new Astar();
		Player p = (Player)this.level.getPlayer();
		Node start = new Node(p.getX(), p.getY());
		comp.setLevel(this.level);
		comp.setStart(start);
		for (Block c : lCrates) {
			ArrayList<Node> crateNeighbours=comp.neighbours(new Node(((Crate) c).getX(),((Crate)c).getY()));
			for (Node n : crateNeighbours) {
				comp.setGoal(n);
				comp.pathSearch();
				if (comp.getPath()!=null) {
					Push newpush= new Push(n.getX(),n.getY());
					newpush.setDir(((Crate) c).getX()-n.getX(),((Crate)c).getY()-n.getY());
					this.lPush.add(newpush);
				}
			}
		}
	}

	public void computeValue() {
		if (this.level.getOver()) {
			if (this.level.allPlaced()) {
				this.value=0.;
			}
			else {
				this.value=Double.POSITIVE_INFINITY;
			}
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

/**
	* Mutateur du niveau.
	* @param newBoard
	* Le nouveau niveau.
*/
	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}

 public ArrayList<Push> getPushes() {
	 return this.lPush;
 }

 public double getValue() {
	 return this.value;
 }

 public String toString() {
	 Player p = (Player)this.level.getPlayer();
	 String chCrates="";
	 for (Block c : this.level.getCrates()) {
		 chCrates+="(" + ((Crate) c).getX()+","+((Crate)c).getY() + ") ; ";
	 }
	 String chPushes="";
	 for (Push m : this.lPush) {
		 chPushes+="(" + m.toString() + ") ; ";
	 }
	 return "Position du joueur : (" + p.getX() + "," + p.getY() + ")\nPosition des caisses : " + chCrates + "\nListe des coups : " + chPushes + "\nEtat final : " + this.level.getOver() + "\nValeur : " + this.getValue();
 }
}
