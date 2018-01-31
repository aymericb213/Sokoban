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
      for (int j=0; j < this.grid.length; j++) {
        if (this.grid[i][j] != null) {
          result += this.grid[i][j].toString();
        }
      }
      result += "\n";
    }
    return result;
  }

  public boolean isFinished() {
    boolean test = true;
    for (Block c : this.listCrate) {
      int i = ((Crate)c).x;
      int j = ((Crate)c).y;
      if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
      (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
        ((Crate)c).setDeadlock(true);
        return true;
      } else if (!((Crate)c).isPlaced()) {
         test = false;
      }
    }
    return test;
  }

  public void createGrid(ArrayList<ArrayList<String>> mapToString){
    int[] gridSize = this.getSize(mapToString);
    this.grid = new Block[gridSize[0]][gridSize[1]];
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
    size[0] = maxWidth+1;
    size[1] = maxHeight;
    return size;
  }
}
