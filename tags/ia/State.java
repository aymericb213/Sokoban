package ia;

import sokoban.*;
import java.util.ArrayList;

public class State {

	private Board level;
	private ArrayList<Push> lPush;

	public State() {
		this.level=new Board();
		this.lPush=new ArrayList<Push>();
	}

	public void accessiblePushes() {
		Astar comp=new Astar();
		comp.setStart(this.level.getPlayer());
		for (Node c : this.level.getCrates()) {
			for (Node n : comp.neighbours(c)) {
				comp.setGoal(n);
				comp.pathSearch();
				if (comp.getPath()!="") {
					this.lPush.add(n);
				}
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

}
