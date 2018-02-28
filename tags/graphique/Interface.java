
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import sokoban.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Interface extends JFrame {

  private Board b;
  private MapReader map;
  private CanvasGame can;

  public Interface(Board b, MapReader map) {
    this.b = b;
    this.map = map;
    this.setResizable(false);
    this.setTitle("Sokoban");

    JPanel zoneControl = new JPanel();

    int fontSize = 12;

    JButton bReset = new JButton("Restart");
    bReset.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bReset.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e) {
        Interface.this.b.createGrid(Interface.this.map.getMap());
        Interface.this.b.setPartyFinished(false);
        Interface.this.can.setPlayer("graphique/images/perso.png");
        Interface.this.can.update();
      }
    });

    JButton bSave = new JButton("Save");
    bSave.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bSave.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e) {
        Save save = new Save(b.createArrayList(),"save");
        save.saveMap();
        JOptionPane popupSave = new JOptionPane();
        Timer timer = new Timer(1000,new ActionListener () {
          public void actionPerformed(ActionEvent e) {
            popupSave.getRootFrame().dispose();
          }
        });
        timer.start();
        popupSave.showMessageDialog(null, "Game saved", "Save", JOptionPane.INFORMATION_MESSAGE);
        timer.stop();

      }
    });

    JButton bLoad = new JButton("Load");
    bLoad.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bLoad.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e) {
        MapReader mapLoad = new MapReader("");
        mapLoad.setFile("maps/save.xsb");
        mapLoad.readingMap();
        Interface.this.b.createGrid(mapLoad.getMap());
        Interface.this.can.update();
        JOptionPane popupLoad = new JOptionPane();
        Timer timer = new Timer(1000,new ActionListener () {
          public void actionPerformed(ActionEvent e) {
            popupLoad.getRootFrame().dispose();
          }
        });
        timer.start();
        popupLoad.showMessageDialog(null, "Game loaded", "Load", JOptionPane.INFORMATION_MESSAGE);
        timer.stop();
      }
    });


    JButton bCancel = new JButton("Cancel");
    bCancel.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bCancel.addActionListener(new ActionListener () {
        public void actionPerformed(ActionEvent e){
          Interface.this.map.setFile("maps/cancel.xsb");
          Interface.this.map.readingMap();
          Interface.this.b.createGrid(Interface.this.map.getMap());
          Interface.this.can.update();
        }
    });

    JButton bQuit = new JButton("Back to menu");
    bQuit.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bQuit.addActionListener(new ActionListener () {
        public void actionPerformed(ActionEvent e){
            Interface.this.dispose();
            new Menu();
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
    this.can = new CanvasGame(this.b);
    this.can.setFocusable(false);
    add(can);
    gc.gridx = 1;
    add(zoneControl);
    KeyAction key =  new KeyAction (this.b, this.can);
    this.setFocusable(true);

    addKeyListener(key);
    bReset.addKeyListener(key);
    bSave.addKeyListener(key);
    bLoad.addKeyListener(key);
    bCancel.addKeyListener(key);
    bQuit.addKeyListener(key);


    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
