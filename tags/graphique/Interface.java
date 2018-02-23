
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import sokoban.*;
import java.io.*;

public class Interface extends JFrame {

  private Board b;

  public Interface(Board b) {
    this.b = b;
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setTitle("Sokoban");

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
    CanvasGame can = new CanvasGame(this.b);
    can.setFocusable(false);
    add(can);
    gc.gridx = 1;
    add(zoneControl);
    KeyAction key =  new KeyAction (this.b,can);
    this.setFocusable(true);

    addKeyListener(key);
    bReset.addKeyListener(key);
    bSave.addKeyListener(key);
    bLoad.addKeyListener(key);
    bCancel.addKeyListener(key);
    bQuit.addKeyListener(key);


    pack();


    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
