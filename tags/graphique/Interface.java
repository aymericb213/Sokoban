
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
    Container cont = this.getContentPane();

    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(gridbag);

    JPanel zoneControl = new JPanel();
    JButton bPlay = new JButton("Play");
    bPlay.setPreferredSize(new Dimension(10, 100));
    JButton bChangeMap = new JButton("Change Map");
    JButton bQuit = new JButton("Quit");

    this.setLayout(new GridLayout(1,2));
    zoneControl.setLayout(new GridLayout(3,1));

    zoneControl.setSize(50,100);
    zoneControl.add(bPlay);
    zoneControl.add(bChangeMap);
    zoneControl.add(bQuit);
    cont.add(new CanvasGame(this.b));
    cont.add(zoneControl);
    pack();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
