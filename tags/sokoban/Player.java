
package sokoban;

public class Player extends Block implements Movable {

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
}
