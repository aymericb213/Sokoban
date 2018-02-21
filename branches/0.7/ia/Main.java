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
		MapReader map = new MapReader("ia/testmaps/playermove.xsb");
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
		algo.setStart(start);
		algo.setGoal(goal);

		ArrayList<Node> path = algo.pathSearch();
		if (path==null) {
			System.out.println("Aucun chemin possible vers " + goal);
		} else {
		System.out.println(path);
		int iter=0;
		for (int i=1; i<path.size(); i++) {
			iter++;
			Thread.sleep(500);
			ArrayList<Integer> nextMove = new ArrayList<>();
			nextMove.add(path.get(i).getX()-p.getX());
			nextMove.add(path.get(i).getY()-p.getY());
			p.move(b,nextMove);
			System.out.println("\033[H\033[2J");
			System.out.println(b);
			System.out.println("itération " + iter);
		}
		}
	}
}
