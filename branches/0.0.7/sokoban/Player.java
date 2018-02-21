package sokoban;

import java.util.ArrayList;

/**
	* Classe fille de Block représentant le joueur.
*/
public class Player extends Block {

  private boolean onObjective;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param second
		* Abscisse de la case.
		* @param onObjective
		* Booléen indiquant si le joueur se trouve sur un objectif.
	*/
  public Player(int x, int y, boolean onObjective) {
    super(x,y);
    this.onObjective=onObjective;
  }

	/**
		* Accesseur permettant de tester si le joueur se trouve sur un objectif.
		* @return La valeur de l'attribut onObjective.
	*/
  public boolean isOnObjective() {
    return this.onObjective;
  }

	/**
		* Retourne la représentation du joueur.
		* @return Un caractère représentant le joueur et son état (sur un objectif ou non).
	*/
  @Override
  public String toString() {
    if(this.onObjective) {
      return "+";
    } else {
      return "@";
    }
  }

	/**
		* Déplace le joueur et éventuellement une caisse.
		* @param b
		* Le niveau avant le déplacement.
		* @param position
		* Les coordonnées de la destination du joueur.
		* @return Le niveau mis à jour après le déplacement.
	*/
  public Board move(Board b, ArrayList<Integer> position){
    Block new_place = b.grid[this.x+position.get(0)][this.y+position.get(1)];
    if (new_place instanceof Objective){
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
          new_place.x+=position.get(0);
          new_place.y+=position.get(1);
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
          new_place.x+=position.get(0);
          new_place.y+=position.get(1);
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

/**
	* Accesseur de l'ordonnée du joueur.
	* @return La valeur de x.
	*/
	public int getX() {
		return this.x;
	}

/**
	* Accesseur de l'abscisse du joueur.
	* @return La valeur de y.
	*/
	public int getY() {
		return this.y;
	}
}
