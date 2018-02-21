package sokoban;

public class Wall extends Block {

  public Wall(int x, int y) {
    super(x,y);
  }

  @Override
  public String toString() {
    return "#";
  }
}
