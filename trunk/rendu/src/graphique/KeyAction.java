
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import sokoban.*;
import java.io.*;

/**
  * Evenements
  */
public class KeyAction extends KeyAdapter {

  private Board b;
  private CanvasGame can;
  private int level;
  private boolean isDab;
  private boolean playerMove;

  /**
    * Constructeur de la classe
    * @param b board que doit gérer les events
    * @param can canvas à modifier
    */
  public KeyAction (Board b, CanvasGame can) {
    this.b = b;
    this.can = can;
    this.isDab = false;
    this.playerMove = false;
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
    this.can.update(minW*this.can.sizeTile, minH*this.can.sizeTile,
                    this.can.sizeTile + Math.abs(nextMove.get(1))*this.can.sizeTile*2,
                    this.can.sizeTile + Math.abs(nextMove.get(0))*this.can.sizeTile*2);
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  /**
    * gestion du déplacement du joueur et dab
    */
  @Override
  public void keyPressed(KeyEvent e) {

    if (!this.b.getOver() && !this.can.getSolving()) {
      ArrayList<Integer> nextMove = new ArrayList<>();
      Player player = ((Player)b.getPlayer());
      int x = player.getX();
      int y = player.getY();

      if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q) {
        nextMove.add(0);
        nextMove.add(-1);
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
        nextMove.add(0);
        nextMove.add(1);
      } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z) {
        nextMove.add(-1);
        nextMove.add(0);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
        nextMove.add(1);
        nextMove.add(0);
      } else if (!this.isDab && e.getKeyCode() == KeyEvent.VK_B) {
        this.can.setPlayer("../ressources/images/persoDab.png");
        this.isDab = true;
        this.can.update(player.getY()*this.can.sizeTile,player.getX()*this.can.sizeTile,this.can.sizeTile,this.can.sizeTile);
      }

      if (!nextMove.isEmpty()) {
        Save cancel = new Save (this.b.createArrayList(),"cancel_" + this.b.getPlayerName(),"map" + this.b.getLevel());
        cancel.saveMap();
        this.movePlayer(nextMove);
        if (this.b.isFinished()) {
          this.b.setOver(true);
          if (this.b.allPlaced()) {
            this.can.setPlayer("../ressources/images/persoDab.png");
            PlayerReader read = new PlayerReader(this.b.getPlayerName());
            int niveauPlayer = read.getLevel();
            int nbMaps = new File("maps").list().length;
            if (this.b.getLevel() == niveauPlayer && this.b.getLevel() < nbMaps) {
              PlayerSave pSave = new PlayerSave(this.b.getPlayerName(), niveauPlayer + 1);
              pSave.savePlayer();
            }
          } else {
            this.can.setPlayer("../ressources/images/persoPerdu.png");
          }
        }
      }
    }
  }

  /**
    * retirer le dab quand le joueur relache la touche B
    */
  @Override
  public void keyReleased(KeyEvent e) {
    if (!this.b.getOver() && e.getKeyCode() == KeyEvent.VK_B) {
      Player player = ((Player)b.getPlayer());
      this.can.setPlayer("../ressources/images/perso.png");
      this.can.update(player.getY()*this.can.sizeTile,player.getX()*this.can.sizeTile,this.can.sizeTile,this.can.sizeTile);
      this.isDab = false;
    }
  }

}
