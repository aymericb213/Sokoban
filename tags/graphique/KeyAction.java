
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

  public KeyAction (Board b,CanvasGame can) {
    this.b = b;
    this.can = can;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

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
    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      this.can.setPlayer("graphique/images/persoDab.png");
      this.can.update();
    }

    if (!nextMove.isEmpty()) {
      player.move(b,nextMove);
      this.can.update();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      this.can.setPlayer("graphique/images/perso.png");
      this.can.update();
    }
  }

}
