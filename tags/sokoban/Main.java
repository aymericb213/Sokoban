package sokoban;

import java.io.*;
import java.util.ArrayList;

public class Main {

  public static void main (String[] args) throws IOException {
    ArrayList<ArrayList> map = Board.readingMap("sokoban/maps/map1.xsb");
    System.out.println(map);
  }

}
