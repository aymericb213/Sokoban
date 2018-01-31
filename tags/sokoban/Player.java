package sokoban;

import java.util.ArrayList;

public class Player extends Block {

  private boolean onObjective;

  public Player(int x, int y, boolean onObjective) {
    super(x,y);
    this.onObjective=onObjective;
  }

  public boolean isOnObjective() {
    return this.onObjective;
  }

  @Override
  public String toString() {
    System.out.println(this.onObjective);
    if(this.onObjective) {
      return "+";
    } else {
      return "@";
    }
  }

  public Board move(Board b, ArrayList<Integer> position){
    Block new_place = b.grid[this.x+position.get(0)][this.y+position.get(1)];
    if (new_place instanceof Objective){

      System.out.println("objectif");
      b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
      Block t = new FreeTile(this.x,this.y);
      b.grid[this.x][this.y]=t;

      this.x+=position.get(0);
      this.y+=position.get(1);
      this.onObjective=true;


    } else if (new_place instanceof Crate && !(((Crate)new_place).isBlocked())){
        if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective){

          b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=b.grid[this.x+position.get(0)][this.y+position.get(1)];

          ((Crate)b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))]).setPlaced(true);

          b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
          Block t = new FreeTile(this.x,this.y);
          b.grid[this.x][this.y]=t;

          this.x+=position.get(0);
          this.y+=position.get(1);

        } else if (!(b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Wall || b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Crate)){

          b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=b.grid[this.x+position.get(0)][this.y+position.get(1)];

          b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
          Block t = new FreeTile(this.x,this.y);
          b.grid[this.x][this.y]=t;

          this.x+=position.get(0);
          this.y+=position.get(1);
        }

        /* rajouter une condition si la caisse est sur un objectif */




    } else if (!( new_place instanceof Wall)){

      Block temp = new FreeTile(this.x,this.y);
      b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
      b.grid[this.x][this.y]=temp;

      this.x+=position.get(0);
      this.y+=position.get(1);
    }


    if (onObjective==true){

      Block obje = new Objective(this.x,this.y);
      System.out.println("remet lobj");
      for (Block obj : b.listObjective){
        if (obj.x==this.x && obj.y==this.y){
          obje=obj;
          break;
        }
      }
      this.onObjective=false;
      b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
      b.grid[this.x][this.y]=obje;
      this.x+=position.get(0);
      this.y+=position.get(1);
      this.onObjective=false;
    }


    return b;
  }
}
