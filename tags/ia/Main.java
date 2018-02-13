package ia;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import sokoban.*;

public class Main {

	public static void main(String[] args) throws IOException {
		MapReader map = new MapReader("sokoban/maps/map1.xsb");
		map.readingMap();
		Board b= new Board();
		b.createGrid(map.getMap());
		Astar algo=new Astar();
		Player p = (Player)b.getPlayer();
		Node start = new Node(p.getX(), p.getY());
  	}
}
