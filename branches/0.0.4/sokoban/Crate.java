package sokoban;

public class Crate extends Block {

  private boolean deadLock;
  private boolean placed;

  public Crate(int x, int y,boolean placed) {
    super(x,y);
    this.deadLock=false;
    this.placed=placed;
  }

  public boolean isPlaced() {
    return this.placed;
  }

	public boolean isBlocked() {
		return this.deadLock;
	}

	public void setPlaced(boolean newState) {
		this.placed=newState;
	}

	public void setDeadlock(boolean newState) {
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
