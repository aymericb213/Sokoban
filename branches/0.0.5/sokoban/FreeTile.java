package sokoban;

public class FreeTile extends Block {
/**
	Représente une case vide.
*/
  public FreeTile(int x, int y) {
    super(x,y);
  }

  @Override
  public String toString() {
    return " ";
  }
}
