package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	private State state;
	private Push bestPush;
	private ArrayList<Node> coup;

	public Solver() {
		this.state=null;
		this.bestPush=null;
	}

	public Solver(State state) {
		this();
		this.state=state;
	}

	public void minmin(){
		
	}

	public ArrayList<Node> getCoup(){
		return this.coup;
	}
}
