package ia;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import sokoban.*;

/**
	* Classe exécutable du package sokoban.
	*/
public class Main {

	/**
		* Génère et exécute une séquence de mouvements optimale pour résoudre un niveau de Sokoban
		* à l'aide d'un algorithme de recherche de chemin.
	*/
	public static void main(String[] args) throws IOException, InterruptedException {
		MapReader map = new MapReader("ia/testmaps/cratemove.xsb");
		map.readingMap();
		Board b= new Board();
		b.createGrid(map.getMap());
		System.out.println(b);

		Astar algo=new Astar();
		Player p = (Player)b.getPlayer();
		Node start = new Node(p.getX(), p.getY());

		Objective o = ((Objective)b.getObjectives().get(0));
		Node goal = new Node(o.getX(), o.getY());

		algo.setLevel(b);
		ArrayList<Node[]> test=algo.createPairs();
		System.out.println(test);
		algo.setStart(start);
		algo.setGoal(goal);
		algo.pathSearch();
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
		System.out.println(algo);
	}
}
