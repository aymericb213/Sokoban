package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.util.*;
import sokoban.*;
import java.io.*;
import javax.imageio.ImageIO;

public class SelectMap extends JFrame {

  private JList list;

  public SelectMap () {

      this.setSize(720,620);
      this.setResizable(false);
      this.setTitle("Select map");

      JPanel zoneButton = new JPanel ();

      JButton bPlay = new JButton("Play");
      bPlay.addActionListener(new ActionListener () {
        public void actionPerformed(ActionEvent e){
          int indice = SelectMap.this.list.getSelectedIndex();
          if (indice != -1) {
            Board b = new Board();
            MapReader map = new MapReader("");
            map.setFile("maps/map" + (indice + 1) + ".xsb");
            map.readingMap();
            b.createGrid(map.getMap());
            SelectMap.this.dispose();
            new Interface(b,map);
          }
        }
      });

      JButton bBack = new JButton("Back to menu");
      bBack.addActionListener(new ActionListener () {
        public void actionPerformed(ActionEvent e){
            SelectMap.this.dispose();
            new Menu();
        }
      });

      zoneButton.add(bPlay);
      zoneButton.add(bBack);
      zoneButton.setLayout(new GridLayout(2,1));

      Vector vect = new Vector();
      int nbMaps = new File("maps").list().length;
      for (int i = 1; i<(nbMaps-1); i++) {
        vect.add("Map " + i);
      }
      this.list = new JList(vect);

      this.add(zoneButton);
      this.add (list);

      this.setLayout(new GridLayout(1,2));

      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);

  }

}
