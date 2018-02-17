package ia;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import sokoban.*;

public class Main {

	public static void main(String[] args) throws IOException {
		MapReader map = new MapReader("ia/testmaps/playermove.xsb");
		map.readingMap();
		Board b= new Board();
		b.createGrid(map.getMap());
		System.out.println(b);

		Astar algo=new Astar();

		Player p = (Player)b.getPlayer();
		Node start = new Node(p.getX(), p.getY());

		Objective o = ((Objective)b.getObjective().get(0));
		Node goal = new Node(o.getX(), o.getY());

		algo.setLevel(b);
		algo.setStart(start);
		algo.setGoal(goal);

		Node path = algo.pathSearch();
		}
}
