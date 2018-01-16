
package sokoban;

import java.util.ArrayList;

class Board {

  private Block[][] map;
  private ArrayList<Crate> listCrate;
  private ArrayList<Objective> listObjective;
  private Player player;
  private String file;

  public Board(String file) {
    this.file = file;
  }

  public Block[][] loadMap(String file) {
    
  }
}
