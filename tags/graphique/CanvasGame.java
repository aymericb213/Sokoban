
package graphique;

import java.awt.*;
import javax.swing.*;
import sokoban.*;

public class CanvasGame extends Canvas {

  private Board b;
  private Image crate;

  public CanvasGame (Board b) {
    this.b = b;
    this.crate = new ImageIcon("caisse.png").getImage();
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
            g.fillRect(taille*i,taille*j,taille,taille);
          } else if (grid[j][i].toString().equals("$")) {
            g.drawImage(this.crate,taille*i,taille*j,null);
          } else if (grid[j][i].toString().equals(".")) {
            g.setColor(Color.yellow);
            g.fillRect(taille*i,taille*j,taille,taille);
          } else if (grid[j][i].toString().equals("@")) {
            g.setColor(Color.red);
            g.fillRect(taille*i,taille*j,taille,taille);
          } else if (grid[j][i].toString().equals("*")) {
            g.setColor(Color.blue);
            g.fillRect(taille*i,taille*j,taille,taille);
          } else if (grid[j][i].toString().equals("+")) {
            g.setColor(Color.orange);
            g.fillRect(taille*i,taille*j,taille,taille);
          }


        }
      }
    }
  }
}
