
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import sokoban.*;

public class KeyAction extends KeyAdapter {

  private Board b;
  private CanvasGame can;
  private boolean isDab;

  public KeyAction (Board b, CanvasGame can) {
    this.b = b;
    this.can = can;
    this.isDab = false;
  }

  public void movePlayer (ArrayList<Integer> nextMove) {
    Player player = ((Player)b.getPlayer());
    player.move(b,nextMove);
    int minW = Math.min(player.getY()-nextMove.get(1),player.getY()+nextMove.get(1));
    int minH = Math.min(player.getX()-nextMove.get(0),player.getX()+nextMove.get(0));
    this.can.update(minW*this.can.sizeTile, minH*this.can.sizeTile, this.can.sizeTile + Math.abs(nextMove.get(1))*this.can.sizeTile*2, this.can.sizeTile + Math.abs(nextMove.get(0))*this.can.sizeTile*2);
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

    if (!this.b.getPartyFinished()) {
      ArrayList<Integer> nextMove = new ArrayList<>();
      Player player = ((Player)b.getPlayer());

      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        nextMove.add(0);
        nextMove.add(-1);
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        nextMove.add(0);
        nextMove.add(1);
      } else if (e.getKeyCode() == KeyEvent.VK_UP) {
        nextMove.add(-1);
        nextMove.add(0);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        nextMove.add(1);
        nextMove.add(0);
      } else if (!this.isDab && e.getKeyCode() == KeyEvent.VK_D) {
        this.can.setPlayer("graphique/images/persoDab.png");
        this.isDab = true;
        this.can.update(player.getY()*this.can.sizeTile,player.getX()*this.can.sizeTile,this.can.sizeTile,this.can.sizeTile);
      }

      if (!nextMove.isEmpty()) {
        this.movePlayer(nextMove);
        Save cancel = new Save (this.b.createArrayList(),"cancel");
        cancel.saveMap();
        if (this.b.isFinished()) {
          this.b.setPartyFinished(true);
          if (this.b.allPlaced()) {
            this.can.setPlayer("graphique/images/persoDab.png");
          } else {
            this.can.setPlayer("graphique/images/persoPerdu.png");
          }
        }
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (!this.b.getPartyFinished() && e.getKeyCode() == KeyEvent.VK_D) {
      Player player = ((Player)b.getPlayer());
      this.can.setPlayer("graphique/images/perso.png");
      this.can.update(player.getY()*this.can.sizeTile,player.getX()*this.can.sizeTile,this.can.sizeTile,this.can.sizeTile);
      this.isDab = false;
    }
  }

}
