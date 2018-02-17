
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame {

  public Interface() {
    Container cont = this.getContentPane();

    JPanel zoneControl = new JPanel();
    JPanel zoneGame = new JPanel();
    JButton bPlay = new JButton("Play");
    JButton bChangeMap = new JButton("Change Map");
    JButton bQuit = new JButton("Quit");

    this.setLayout(new GridLayout(1,2));
    zoneControl.setLayout(new GridLayout(3,1));

    zoneControl.add(bPlay);
    zoneControl.add(bChangeMap);
    zoneControl.add(bQuit);
    cont.add(zoneGame);
    cont.add(zoneControl);

    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
