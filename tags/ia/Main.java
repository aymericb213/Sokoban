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
	public static void main(String[] args) throws IOException {
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

		Node path = algo.pathSearch();
		}
}
