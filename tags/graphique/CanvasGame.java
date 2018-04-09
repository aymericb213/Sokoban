
package graphique;

import java.awt.*;
import java.awt.Image.*;
import javax.swing.*;
import sokoban.*;
import java.util.ArrayList;

/**
  * Canvas avec la représentation des maps
  */
public class CanvasGame extends JPanel {

  private Board b;
  protected int sizeTile;
  private Image crate;
  private Image wall;
  private Image freeTile;
  private Image player;
  private Image star;
  private Image backgroundCanvas;

  public CanvasGame (Board b, int sizeTile) {
    this.b = b;
    this.crate = Toolkit.getDefaultToolkit().getImage("graphique/images/caisse.png");
    this.wall = Toolkit.getDefaultToolkit().getImage("graphique/images/mur.jpg");
    this.freeTile = Toolkit.getDefaultToolkit().getImage("graphique/images/sol.png");
    this.player = Toolkit.getDefaultToolkit().getImage("graphique/images/perso.png");
    this.star = Toolkit.getDefaultToolkit().getImage("graphique/images/star.png");
    this.sizeTile = sizeTile;
    int[] sizeGrid = b.getSize();
    setPreferredSize(new Dimension(sizeTile*sizeGrid[0],sizeTile*sizeGrid[1]));
  }

  /**
    * Modifie l'image du personnage
    * @param image chemin de la nouvelle image
    */
  public void setPlayer (String image) {
    this.player = Toolkit.getDefaultToolkit().getImage(image);
  }

  /**
    * Ajoute un nouveau board au canvas et le redimensionne
    * @param newBoard nouveau board à assigner
    */
  public void setBoard(Board newBoard) {
    this.b = newBoard;
    int[] sizeGrid = b.getSize();
    this.setSize(sizeTile*sizeGrid[0],sizeTile*sizeGrid[1]);
  }

  /**
    * Actualise le canvas entier
    */
  public void update() {
    repaint();
  }

  /**
    * Actualise une partie du canvas
    * @param x coordonnée en X du point de la selection
    * @param y coordonnée en Y du point de la selection
    * @param width taille horizontal du rectangle de selection
    * @param height taille vertical du rectangle de selection
    */
  public void update (int x, int y, int width, int height) {
    repaint(x,y,width,height);
  }

  /**
    * Actualise la zone utile à actualiser du canvas
    * @param nextMove meme argument que Player.move
    */
  public void movePlayer (ArrayList<Integer> nextMove) {
    Player player = ((Player)b.getPlayer());
    player.move(b,nextMove);
    int minW = Math.min(player.getY()-nextMove.get(1),player.getY()+nextMove.get(1));
    int minH = Math.min(player.getX()-nextMove.get(0),player.getX()+nextMove.get(0));
    this.update(minW*this.sizeTile, minH*this.sizeTile,
                    this.sizeTile + Math.abs(nextMove.get(1))*this.sizeTile*2,
                    this.sizeTile + Math.abs(nextMove.get(0))*this.sizeTile*2);
  }

  /**
    * Dessine la grille du board
    */
  @Override
  public void paint(Graphics g) {
    Block[][] grid = b.getGrid();
    int[] size = b.getSize();
    int tailleStar = this.sizeTile/2;
    this.setSize(this.sizeTile*size[0],this.sizeTile*size[1]);

    for (int i = 0; i<size[1] ; i++) {
      for (int j = 0; j<size[0]; j++) {
        if (grid[i][j] != null) {
          if (grid[i][j].toString().equals("#")) {
            g.drawImage(this.wall,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);

          } else if (grid[i][j].toString().equals("$")) {
            g.drawImage(this.crate,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);

          } else if (grid[i][j].toString().equals(" ")) {
            g.drawImage(this.freeTile,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);

          } else if (grid[i][j].toString().equals("@")) {
            g.drawImage(this.freeTile,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);
            g.drawImage(this.player,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);

          } else if (grid[i][j].toString().equals("*")) {
            g.drawImage(this.crate,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);
            g.drawImage(this.star,this.sizeTile*j+tailleStar/2,this.sizeTile*i+tailleStar/2,tailleStar,tailleStar,this);

          } else if (grid[i][j].toString().equals("+")) {
            g.drawImage(this.freeTile,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);
            g.drawImage(this.player,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);

          } else if (grid[i][j].toString().equals(".")) {
            g.drawImage(this.freeTile,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);
            g.drawImage(this.star,this.sizeTile*j+tailleStar/2,this.sizeTile*i+tailleStar/2,tailleStar,tailleStar,this);

          }
        } else {
          g.drawImage(this.freeTile,this.sizeTile*j,this.sizeTile*i,this.sizeTile,this.sizeTile,this);
        }
      }
    }
  }
}
