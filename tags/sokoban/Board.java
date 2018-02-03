package sokoban;

import java.util.ArrayList;

public class Board {

  protected Block[][] grid;
  protected ArrayList<Block> listCrate;
  protected ArrayList<Block> listObjective;
  protected Block player;

  public Board() {
		this.listCrate = new ArrayList<>();
		this.listObjective = new ArrayList<>();
  }

  public String toString() {
    String result = "";
    for (int i=0; i < this.grid.length; i++) {
      for (int j=0; j < this.grid[0].length; j++) {
        if (this.grid[i][j] != null) {
          result += this.grid[i][j].toString();
        }
      }
      result += "\n";
    }
    return result;
  }

  public boolean isDead(Block c, int i, int j) {

    if ((this.grid[i-1][j] instanceof Crate || this.grid[i+1][j] instanceof Crate) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {

      return true;

    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Crate || this.grid[i][j+1] instanceof Crate)) {

        return true;

    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {

      return true;
    }
    return false;
  }

  public boolean allPlaced() {
    for (Block c : this.listCrate) {
      if (!((Crate)c).isPlaced()) {
        return false;
      }
    }
    return true;
  }

  public boolean isFinished() {
    boolean test = false;
    if (this.allPlaced()) {
      return true;
    }
    for (Block c : this.listCrate) {
      int i = ((Crate)c).x;
      int j = ((Crate)c).y;

      if (this.isDead(c,i,j) && !((Crate)c).isPlaced()) {
        if (this.grid[i-1][j] instanceof Crate && !this.isDead(this.grid[i-1][j],i-1,j)) {
          test = false;
        } else if (this.grid[i+1][j] instanceof Crate && !this.isDead(this.grid[i+1][j],i+1,j)) {
          test = false;
        } else if (this.grid[i][j-1] instanceof Crate && !this.isDead(this.grid[i][j-1],i,j-1)) {
          test = false;
        } else if (this.grid[i][j+1] instanceof Crate && !this.isDead(this.grid[i][j+1],i,j+1)) {
          test = false;
        } else {
          ((Crate)c).setDeadlock(true);
          return true;
        }
      }
    }
    return test;
  }

  public void createGrid(ArrayList<ArrayList<String>> mapToString){
    int[] gridSize = this.getSize(mapToString);
    this.grid = new Block[gridSize[1]][gridSize[0]];
    for (int i=0;i<mapToString.size();i++) {
      for (int j=0;j<mapToString.get(i).size();j++) {
        if (mapToString.get(i).get(j).equals(" ")){
					Block t = new FreeTile(i,j);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("#")) {
          Block t = new Wall(i,j);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("$")) {
          Block t = new Crate(i,j,false);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals(".")) {
          Block t = new Objective(i,j);
					this.listObjective.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("*")) {
          Block t = new Crate(i,j,true);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("@")) {
          Block t = new Player(i,j,false);
					this.player=t;
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("+")) {
          Block t = new Player(i,j,true);
					this.player=t;
          this.grid[i][j] = t;
        }
      }
    }
  }

  public int[] getSize(ArrayList<ArrayList<String>> map) {
    int[] size = new int[2];
    int maxHeight = 0;
    int maxWidth = 0;
    for (ArrayList<String> line : map) {
      maxHeight += 1;
      int widthTemp = 0;
      for (String val : line) {
        widthTemp += 1;
      }
      if (widthTemp>maxWidth) {
        maxWidth = widthTemp;
      }
    }
    size[0] = maxWidth;
    size[1] = maxHeight;
    return size;
  }
}
