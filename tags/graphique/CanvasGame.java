
package graphique;

import java.awt.*;
import java.awt.Image.*;
import javax.swing.*;
import sokoban.*;

public class CanvasGame extends Canvas {

  private Board b;
  private Image crate;
  private Image wall;
  private Image freeTile;
  private Image player;
  private Image star;

  public CanvasGame (Board b) {
    this.b = b;
    this.crate = Toolkit.getDefaultToolkit().getImage("graphique/caisse.png");
    this.wall = Toolkit.getDefaultToolkit().getImage("graphique/mur.jpg");
    this.freeTile = Toolkit.getDefaultToolkit().getImage("graphique/sol2.png");
    this.player = Toolkit.getDefaultToolkit().getImage("graphique/Personnage.png");
    this.star = Toolkit.getDefaultToolkit().getImage("graphique/star.png");
    setBackground(Color.white);
  }

  @Override
  public void paint(Graphics g) {
    Block[][] grid = b.getGrid();
    int[] size = b.getSize();
    int taille = 30;
    int tailleStar = 14;
    for (int i = 0; i<size[1] ; i++) {
      for (int j = 0; j<size[0]; j++) {
        if (grid[j][i] != null) {
          if (grid[j][i].toString().equals("#")) {
            g.drawImage(this.wall,taille*i,taille*j,taille,taille,this);
          } else if (grid[j][i].toString().equals("$")) {
            g.drawImage(this.crate,taille*i,taille*j,taille,taille,this);
          } else if (grid[j][i].toString().equals(" ")) {
            g.drawImage(this.freeTile,taille*i,taille*j,taille,taille,this);
          } else if (grid[j][i].toString().equals("@")) {
            g.drawImage(this.freeTile,taille*i,taille*j,taille,taille,this);
            g.drawImage(this.player,taille*i,taille*j,taille,taille,this);
          } else if (grid[j][i].toString().equals("*")) {
            g.drawImage(this.crate,taille*i,taille*j,taille,taille,this);
            g.drawImage(this.star,taille*i+tailleStar/2 + 1,taille*j+tailleStar/2 + 1,tailleStar,tailleStar,this);
          } else if (grid[j][i].toString().equals("+")) {
            g.drawImage(this.player,taille*i,taille*j,taille,taille,this);
          } else if (grid[j][i].toString().equals(".")) {
            g.drawImage(this.freeTile,taille*i,taille*j,taille,taille,this);
            g.drawImage(this.star,taille*i+tailleStar/2 + 1,taille*j+tailleStar/2 + 1,tailleStar,tailleStar,this);
          }
        } else {
          g.drawImage(this.freeTile,taille*i,taille*j,taille,taille,this);
        }
      }
    }
  }
}
