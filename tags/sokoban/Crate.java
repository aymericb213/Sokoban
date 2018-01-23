
package sokoban;

public class Crate extends Block implements Movable {

  private boolean deadLock;
  private boolean placed;

  public Crate (int x, int y) {
    super(x,y);
  }

  public boolean isPlaced() {
    return this.placed;
  }

	public boolean isBlocked() {
		return this.deadLock;
	}

  @Override
  public String toString() {
    if (this.placed) {
      return "*";
    } else {
      return "$";
    }
  }
}
