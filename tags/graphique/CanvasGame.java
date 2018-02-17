
package graphique;

import java.awt.*;
import sokoban.*;

public class CanvasGame extends Canvas {

  private Board b;

  public CanvasGame (Board b) {
    this.b = b;
    setBackground(Color.white);
  }

  @Override
  public void paint(Graphics g) {
    Block[][] grid = b.getGrid();
    int[] size = b.getSize();
    int taille = 30;
    for (int i = 0; i<size[1] ; i++) {
      for (int j = 0; j<size[0]; j++) {
        if (!grid[j][i].toString().equals(" ")) {
          if (grid[j][i].toString().equals("#")) {
            g.setColor(Color.black);
          } else if (grid[j][i].toString().equals("$")) {
            g.setColor(Color.green);
          } else if (grid[j][i].toString().equals(".")) {
            g.setColor(Color.yellow);
          } else if (grid[j][i].toString().equals("@")) {
            g.setColor(Color.red);
          } else if (grid[j][i].toString().equals("*")) {
            g.setColor(Color.blue);
          } else if (grid[j][i].toString().equals("+")) {
            g.setColor(Color.orange);
          }

          g.fillRect(taille*i,taille*j,taille,taille);

        }
      }
    }
  }
}
