package ia;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import sokoban.*;
import java.util.logging.*;

/**
	* Classe exécutable du package sokoban.
	*/
public class Main {

	/**
		* Résout un niveau de Sokoban à l'aide d'un algorithme de recherche de chemin.
	*/
	public static void main(String[] args) throws IOException, InterruptedException {
		FileHandler debug_output = new FileHandler("debug%g.log");
		debug_output.setFormatter(new SimpleFormatter());
		MapReader map = new MapReader("maps/map2.xsb");
		map.readingMap();
		Board b= new Board();
		b.createGrid(map.getMap());
		// System.out.println(b);

		// State test=new State(b);
		// test.accessiblePushes();
		// test.computeValue();
		// System.out.println(test);
		// test =test.push(test.getPushes().get(0));
		// System.out.println(b);
		// test.accessiblePushes();
		// test.computeValue();
		// System.out.println(test);
		String total_path="";

		Board search_board= new Board();
		search_board.createGrid(map.getMap());

		ArrayList<State> list_state = new ArrayList<>();
		State present_state=new State(b);

		Solver ia=new Solver(present_state);

		ia.debug.addHandler(debug_output);
		System.out.println(ia.bruteForce());
		 int depth=3;

		 /*while (!(present_state.isFinished())){
		 	ia.debug.info("itération while");
		 	State eval=new State(search_board);
		 	ia.setCurrentState(eval);
			double best_state_value = ia.minmin(eval,depth);
		 	total_path+=ia.toString();
		 	present_state=present_state.push(ia.getBestPush());
		 	ia.debug.info("\n" + present_state.getLevel().toString());
		 	ia.debug.info("=======================================");
		 	ia.debug.info("Valeur du board de jeu : " + best_state_value + "; coup : "+ia.getBestPush());
		 	ia.debug.info("=======================================");
				ia.setPreviousState(present_state);
		 	ArrayList<String> gameboard_save=present_state.getLevel().createArrayList();
		 	search_board.createGrid(gameboard_save);
		 	list_state.add(present_state);

		}*/
		 ia.debug.finer("Fin while");

		 if( present_state.getValue()==0 ) {
			ia.debug.finest("résolu");
			System.out.println(total_path);
		} else if (present_state.getValue()==Double.POSITIVE_INFINITY){
			ia.debug.severe("deadlock");
		}
/*
		Astar algo=new Astar();
		Player p = (Player)b.getPlayer();
		Node start = new Node(p.getX(), p.getY());

		Objective o = ((Objective)b.getObjectives().get(0));
		Node goal = new Node(o.getX(), o.getY());

		algo.setLevel(b);
		algo.setStart(start);
		algo.setGoal(goal);
		algo.pathSearch();
		total_path+=algo.getPath();
		int iter=0;
		for (int i=1; i<algo.getPath().size(); i++) {
			iter++;
			Thread.sleep(250);
			ArrayList<Integer> nextMove = new ArrayList<>();
			nextMove.add(algo.getPath().get(i).getX()-p.getX());
			nextMove.add(algo.getPath().get(i).getY()-p.getY());
			p.move(b,nextMove);
			System.out.println("\033[H\033[2J");
			System.out.println(b);
			System.out.println("itération " + iter);
		}
		System.out.println(algo);*/
	}
}
