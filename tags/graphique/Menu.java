
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import sokoban.*;

public class Menu extends JFrame {

  public Menu () {
    this.setSize(300,500);
    this.setResizable(false);
    this.setTitle("Main menu");

    JButton bPlay = new JButton("Play");
    bPlay.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
          Board b = new Board();
          MapReader map = new MapReader("");
          map.setFile("maps/map1.xsb");
          map.readingMap();
          b.createGrid(map.getMap());
          Menu.this.dispose();
          new Interface(b,map);
      }
    });

    JButton bMap = new JButton("Select map");

    JButton bIa = new JButton("IA battle");

    JButton bRand = new JButton("Map random");

    JButton bQuit = new JButton("Quit");
    bQuit.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
          Menu.this.dispose();
      }
    });

    JPanel pane = new JPanel();

    pane.add(bPlay);
    pane.add(bMap);
    pane.add(bIa);
    pane.add(bRand);
    pane.add(bQuit);

    pane.setLayout(new GridLayout(5,1,20,20));

    this.add(pane);

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }
}
