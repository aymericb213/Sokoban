
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
    this.crate = Toolkit.getDefaultToolkit().getImage("graphique/images/caisse.png");
    this.wall = Toolkit.getDefaultToolkit().getImage("graphique/images/mur.jpg");
    this.freeTile = Toolkit.getDefaultToolkit().getImage("graphique/images/sol.png");
    this.player = Toolkit.getDefaultToolkit().getImage("graphique/images/perso.png");
    this.star = Toolkit.getDefaultToolkit().getImage("graphique/images/star.png");
    int[] size = b.getSize();
    setSize(40*size[0],40*size[1]);
    setBackground(Color.white);
  }

  public void setPlayer (String image) {
    this.player = Toolkit.getDefaultToolkit().getImage(image);
  }

  public void update() {
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    Block[][] grid = b.getGrid();
    int[] size = b.getSize();
    int taille = 40;
    int tailleStar = taille/2;
    for (int i = 0; i<size[1] ; i++) {
      for (int j = 0; j<size[0]; j++) {
        if (grid[i][j] != null) {
          if (grid[i][j].toString().equals("#")) {
            g.drawImage(this.wall,taille*j,taille*i,taille,taille,this);

          } else if (grid[i][j].toString().equals("$")) {
            g.drawImage(this.crate,taille*j,taille*i,taille,taille,this);

          } else if (grid[i][j].toString().equals(" ")) {
            g.drawImage(this.freeTile,taille*j,taille*i,taille,taille,this);

          } else if (grid[i][j].toString().equals("@")) {
            g.drawImage(this.freeTile,taille*j,taille*i,taille,taille,this);
            g.drawImage(this.player,taille*j,taille*i,taille,taille,this);

          } else if (grid[i][j].toString().equals("*")) {
            g.drawImage(this.crate,taille*j,taille*i,taille,taille,this);
            g.drawImage(this.star,taille*j+tailleStar/2,taille*i+tailleStar/2,tailleStar,tailleStar,this);

          } else if (grid[i][j].toString().equals("+")) {
            g.drawImage(this.freeTile,taille*j,taille*i,taille,taille,this);
            g.drawImage(this.player,taille*j,taille*i,taille,taille,this);

          } else if (grid[i][j].toString().equals(".")) {
            g.drawImage(this.freeTile,taille*j,taille*i,taille,taille,this);
            g.drawImage(this.star,taille*j+tailleStar/2,taille*i+tailleStar/2,tailleStar,tailleStar,this);

          }
        } else {
          g.drawImage(this.freeTile,taille*j,taille*i,taille,taille,this);
        }
      }
    }
  }
}
