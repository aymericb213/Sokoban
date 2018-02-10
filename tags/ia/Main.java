package ia;

import java.io.*;
import java.util.ArrayList;
import sokoban.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Board b= new Board();
		MapReader map = new MapReader("sokoban/maps/map1.xsb");
		map.readingMap();
		b.createGrid(map.getMap());
		Astar algo=new Astar(b);
  	}
}
