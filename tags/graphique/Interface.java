
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import sokoban.*;

public class Interface extends JFrame {

  private Board b;

  public Interface(Board b) {
    this.b = b;
    int[] size = b.getSize();
    setSize(2*40*size[1] + 15,40*size[0] + 33);
    Container cont = this.getContentPane();

    JPanel zoneControl = new JPanel();
    JButton bPlay = new JButton("Play");
    JButton bChangeMap = new JButton("Change Map");
    JButton bQuit = new JButton("Quit");

    this.setLayout(new GridLayout(1,2));
    zoneControl.setLayout(new GridLayout(3,1));

    zoneControl.add(bPlay);
    zoneControl.add(bChangeMap);
    zoneControl.add(bQuit);
    add(new CanvasGame(this.b));
    cont.add(zoneControl);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
