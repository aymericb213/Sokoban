
package sokoban;

public class Player extends Block {

  private boolean onObjective;

  public Player(int x, int y) {
    super(x,y);
  }

  public boolean isOnObjective() {
    return this.onObjective;
  }

  @Override
  public String toString() {
    if (this.onObjective) {
      return "+";
    } else {
      return "@";
    }
  }

  public Board move(Board map, List<int> position){
    new_place=map[this.x+position[0]][this.y+position[1]];
    if ( new_place instanceof Objective ){
      this.x+=position[0];
      this.y+=position[1];
      this.onObjective=true;
    }else if ( new_place instanceof Crate && !(new_place.isBlocked()) ){

        if ( map[this.x+(2*position[0])][this.y+(2*position[1])] instanceof Objectif ){
          map[this.x+(2*position[0])][this.y+(2*position[1])]=map[this.x+position[0]][this.y+position[1]];
          map[this.x+(2*position[0])][this.y+(2*position[1])].setPlaced=true;
        }else if ( !(map[this.x+(2*position[0])][this.y+(2*position[1])] instanceof Wall && map[this.x+(2*position[0])][this.y+(2*position[1])] instanceof Crate) ){
          map[this.x+(2*position[0])][this.y+(2*position[1])]=map[this.x+position[0]][this.y+position[1]];
        }

    }else if ( !( new_place instanceof Wall ) ){
      map[this.x+position[0]][this.y+position[1]]=map[this.x][this.y];
    }

    if ( onObjective ){
      this.x-=position[0];
      this.y-=position[1];
      this.onObjective=false;
    }

    return map;
  }
}
