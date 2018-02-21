package sokoban;

public abstract class Block {

  protected int x;
  protected int y;

  public Block(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public abstract String toString();

}
