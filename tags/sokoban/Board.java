
package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;

public class Board {

  private Block[][] map;
  private ArrayList<Crate> listCrate;
  private ArrayList<Objective> listObjective;
  private Player player;
  private String file;

  public Board(String file) {
    this.file = file;
  }

 /*
  public Block[][] loadMap(String file) {

  }*/

  public String toString() {
    String result = "";
    for (int i=0; i < this.map.length; i++) {
      for (int j=0; j < this.map.length; j++) {
        if (this.map[i][j] != null) {
          result += this.map[i][j].toString();
        } else {
          result += " ";
        }
      }
      result += "\n";
    }
    return result;
  }

  public void deadLocker () {
    for (int j = 0; j<this.map.length; j++) {
      for (int i = 0; i<this.map[0].length; i++) {
        if (this.map[i][j] instanceof Crate) {
          if ((this.map[i-1][j] instanceof Wall || this.map[i+1][j] instanceof Wall) &&
          (this.map[i][j-1] instanceof Wall || this.map[i][j+1] instanceof Wall)) {
            this.map[i][j].setDeadlock(true);
          }
        }
      }
    }
  }

  public boolean isFinished () {
    for (int j = 0; j<this.map.length; j++) {
      for (int i = 0; i<this.map[0].length; i++) {
        if (this.map[i][j] instanceof Crate) {
          if (!this.map[i][j].isPlaced()) {
            return false;
          } else if (this.map[i][j].isBlocked()) {
            return true;
          }
        }
      }
    }
    return true;
  }

  public static ArrayList<ArrayList> readingMap(String filename) throws IOException {
    BufferedReader map = new BufferedReader (new FileReader (filename));
    ArrayList<ArrayList> mapToString = new ArrayList<>();
    String line;
    while ((line = map.readLine()) != null) {
      ArrayList<String> lineToString = new ArrayList<>();
      lineToString.add(line);
      mapToString.add(lineToString);
    }

    return mapToString;

  }
}
