
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import sokoban.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Menu extends JFrame {

  public Menu () {
    this.setSize(620,610);
    //this.setResizable(false);
    this.setTitle("Main menu");

    JPanel contenent = new JPanel();

    JPanel title = new JPanel();
    JLabel image = new JLabel(new ImageIcon("graphique/images/titre.png"));
    title.add(image);

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

    JLabel backgroundButton = new JLabel(new ImageIcon("graphique/images/caisseMenu.png"));

    JPanel menu = new JPanel();
    backgroundButton.setLayout(new FlowLayout(FlowLayout.CENTER,0,90));
    backgroundButton.add(menu);


    menu.add(bPlay);
    menu.add(bMap);
    menu.add(bIa);
    menu.add(bRand);
    menu.add(bQuit);

    menu.setOpaque(false);
    menu.setLayout(new GridLayout(5,1,20,20));


    contenent.setLayout(new BorderLayout());

    contenent.add(title,BorderLayout.NORTH);



    contenent.add(backgroundButton,BorderLayout.SOUTH);
    this.add(contenent);

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }
}
