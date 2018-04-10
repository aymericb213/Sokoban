package graphique;

import ia.*;
import sokoban.*;
import java.util.ArrayList;

public class ThreadIa implements Runnable {

    private Board b;
    public CanvasGame can;
		private boolean anytime;

    public ThreadIa(Board b, CanvasGame can, boolean anytime) {
      this.b = b;
      this.can = can;
			this.anytime=anytime;
    }

    @Override
    public void run() {
      State state = new State(this.b);
      Solver solver = new Solver(state);
      solver.aStarSolve(anytime);
      if (!solver.getMovelist().isEmpty()) {
				if (anytime) {
        	ArrayList<Integer> move = solver.getMoveRelatif().get(0);
        	this.can.movePlayer(move);
				} else {
					ArrayList<ArrayList<Integer>> listMove = solver.getMoveRelatif();
	        for (ArrayList<Integer> move : listMove) {
	          this.can.movePlayer(move);
	          try {
	            Thread.sleep(50);
	          } catch (Exception e) {
	            System.out.println("catch");
	          }
					}
				}
      }
      this.can.setSolving(false);
    }
}
