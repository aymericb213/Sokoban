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

      Block temp = new FreeTile(this.x,this.y);

      if (onObjective){
        this.onObjective=false;
        Block obje = new Objective(this.x,this.y);
        for (Block obj : b.listObjective){
          if (obj.x==this.x && obj.y==this.y){
            obje=obj;
            break;
          }
        }
        temp=obje;
      }

      b.grid[this.x][this.y]=temp;

      this.x+=position.get(0);
      this.y+=position.get(1);
      this.onObjective=true;


    } else if (new_place instanceof Crate && !(((Crate)new_place).isBlocked())){



      if (((Crate)new_place).isPlaced()){

        if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective){

          ((Crate)new_place).setPlaced(true);

        } else if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof FreeTile) {

          ((Crate)new_place).setPlaced(false);

        }

        if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective || b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof FreeTile){

          b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=b.grid[this.x+position.get(0)][this.y+position.get(1)];

          b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];


          Block temp = new FreeTile(this.x,this.y);

          if (onObjective){
            this.onObjective=false;
            Block obje = new Objective(this.x,this.y);
            for (Block obj : b.listObjective){
              if (obj.x==this.x && obj.y==this.y){
                obje=obj;
                break;
              }
            }
            temp=obje;
          }

          b.grid[this.x][this.y]=temp;

          this.x+=position.get(0);
          this.y+=position.get(1);
          this.onObjective=true;
        }


      } else {

        if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective){

          ((Crate)new_place).setPlaced(true);

        }

        if (b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective || b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof FreeTile){

          b.grid[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=b.grid[this.x+position.get(0)][this.y+position.get(1)];

          b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];


          Block temp = new FreeTile(this.x,this.y);

          if (onObjective){
            this.onObjective=false;
            Block obje = new Objective(this.x,this.y);
            for (Block obj : b.listObjective){
              if (obj.x==this.x && obj.y==this.y){
                obje=obj;
                break;
              }
            }
            temp=obje;
          }

          b.grid[this.x][this.y]=temp;

          this.x+=position.get(0);
          this.y+=position.get(1);

        }

      }



    } else if (new_place instanceof FreeTile){

      Block temp = new FreeTile(this.x,this.y);

      if (onObjective){
        this.onObjective=false;
        Block obje = new Objective(this.x,this.y);
        for (Block obj : b.listObjective){
          if (obj.x==this.x && obj.y==this.y){
            obje=obj;
            break;
          }
        }
        temp=obje;
      }

      b.grid[this.x+position.get(0)][this.y+position.get(1)]=b.grid[this.x][this.y];
      b.grid[this.x][this.y]=temp;

      this.x+=position.get(0);
      this.y+=position.get(1);
    }

    return b;
  }
}
