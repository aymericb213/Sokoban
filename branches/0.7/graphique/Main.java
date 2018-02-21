package graphique;

import sokoban.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

      Board b = new Board();
      MapReader map = new MapReader("");
      map.setFile("sokoban/maps/map1.xsb");
      map.readingMap();
      b.createGrid(map.getMap());
      Interface fen = new Interface(b);
    }

}
