package graphique;

import ia.*;
import sokoban.*;
import java.util.ArrayList;

public class ThreadIa implements Runnable {

    private Board b;
    private CanvasGame can;

    public ThreadIa(Board b, CanvasGame can) {
      this.b = b;
      this.can = can;
    }

    @Override
    public void run() {
      State state = new State(this.b);
      Solver solver = new Solver(state);
      solver.aStarSolve();
      if (!solver.getMovelist().isEmpty()) {
        ArrayList<ArrayList<Integer>> listMove = solver.getMoveRelatif();
        for (ArrayList<Integer> move : listMove) {
          this.can.movePlayer(move);
          try {
            Thread.sleep(1000);
          } catch (Exception e) {
            System.out.println("catch");
          }
        }
      }
    }
}
