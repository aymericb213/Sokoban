
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

	public boolean setPlaced(boolean newState) {
		this.placed=newState;
	}

	public boolean setDeadlock(boolean newState) {
		this.deadLock=newState;
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
