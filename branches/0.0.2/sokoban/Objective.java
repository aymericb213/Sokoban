
package sokoban;

public class Objective extends Block {

  public Objective(int x, int y) {
    super(x,y);
  }

  @Override
  public String toString() {
    return ".";
  }
}
