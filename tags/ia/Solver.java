package ia;

import sokoban.*;
import java.util.ArrayList;

public class Solver {

	private State state;
	private Push bestPush;

	public Solver() {
		this.state=null;
		this.bestPush=null;
	}

	public Solver(State state) {
		this();
		this.state=state;
	}
}
