
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import sokoban.*;
import java.io.*;

public class Interface extends JFrame {

  private Board b;

  public Interface(Board b) {
    this.b = b;
    Container cont = this.getContentPane();

    JPanel zoneControl = new JPanel();
    JButton bReset = new JButton("Restart");
    JButton bSave = new JButton("Save");
    bSave.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e) {
        Save save = new Save(b.createArrayList(),"save");
        save.saveMap();
      }
    });

    JButton bLoad = new JButton("Load");
    JButton bCancel = new JButton("Cancel");
    JButton bQuit = new JButton("Quit");
    bQuit.addActionListener(new ActionListener () {
        public void actionPerformed(ActionEvent e){
            Interface.this.dispose();
        }
    });

    this.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    zoneControl.setLayout(new GridLayout(5,1,10,10));

    zoneControl.add(bReset);
    zoneControl.add(bSave);
    zoneControl.add(bLoad);
    zoneControl.add(bCancel);
    zoneControl.add(bQuit);

    gc.gridx = 0;
		gc.gridy = 0;
    cont.add(new CanvasGame(this.b));
    gc.gridx = 1;
    cont.add(zoneControl);
    pack();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
