
package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
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
}
