package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;

public class Board {

  protected Block[][] grid;
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
    for (int i=0; i < this.grid.length; i++) {
      for (int j=0; j < this.grid.length; j++) {
        if (this.grid[i][j] != null) {
          result += this.grid[i][j].toString();
        } else {
          result += " ";
        }
      }
      result += "\n";
    }
    return result;
  }

  public void deadLocker() {
    for (int j = 0; j<this.grid.length; j++) {
      for (int i = 0; i<this.grid[0].length; i++) {
        if (this.grid[i][j] instanceof Crate) {
          if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
          (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
            ((Crate)this.grid[i][j]).setDeadlock(true);
          }
        }
      }
    }
  }

  public boolean isFinished() {
    for (int j = 0; j<this.grid.length; j++) {
      for (int i = 0; i<this.grid[0].length; i++) {
        if (this.grid[i][j] instanceof Crate) {
          if (!((Crate)this.grid[i][j]).isPlaced()) {
            return false;
          } else if (((Crate)this.grid[i][j]).isBlocked()) {
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
      String[] line2 = line.split("");
      for (int i = 0; i<line2.length; i++){
        lineToString.add(line2[i]);
      }
      mapToString.add(lineToString);
    }
    return mapToString;
  }
}
