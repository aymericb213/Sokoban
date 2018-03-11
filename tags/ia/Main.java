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
		* Résout un niveau de Sokoban à l'aide d'un algorithme de recherche de chemin.
	*/
	public static void main(String[] args) throws IOException, InterruptedException {
		MapReader map = new MapReader("maps/map2.xsb");
		map.readingMap();
		Board b= new Board();
		b.createGrid(map.getMap());
		System.out.println(b);
		State test=new State(b);
		test.accessiblePushes();
		test.computeValue();
		System.out.println(test);
		test =test.push(test.getPushes().get(0));
		System.out.println(b);
		test.accessiblePushes();
		test.computeValue();
		System.out.println(test);
		String totalPath="";
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
		totalPath+=algo.getPath();
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
