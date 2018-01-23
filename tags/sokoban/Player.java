
package sokoban;

import java.util.ArrayList;

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

  public Board move(Board map, ArrayList<int> position){
    Block new_place = map.map[this.x+position.get(0)][this.y+position.get(1)];
    if ( new_place instanceof Objective ){
      this.x+=position.get(0);
      this.y+=position.get(1);
      this.onObjective=true;
    }else if ( new_place instanceof Crate && !(new_place.isBlocked()) ){

        if ( map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Objective ){
          map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=map.map[this.x+position.get(0)][this.y+position.get(1)];
          map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))].setPlaced(true);
        }else if ( !(map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Wall && map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))] instanceof Crate) ){
          map.map[this.x+(2*position.get(0))][this.y+(2*position.get(1))]=map.map[this.x+position.get(0)][this.y+position.get(1)];
        }

    }else if ( !( new_place instanceof Wall ) ){
      map.map[this.x+position.get(0)][this.y+position.get(1)]=map.map[this.x][this.y];
    }

    if ( onObjective ){
      this.x-=position.get(0);
      this.y-=position.get(1);
      this.onObjective=false;
    }

    return map;
  }
}
