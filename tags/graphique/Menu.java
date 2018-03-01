
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import sokoban.*;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class Menu extends JFrame {

  public Menu () {
    this.setSize(720,620);
    this.setResizable(false);
    this.setTitle("Main menu");


    JPanel contenent = new JPanel();

    JPanel title = new JPanel();
    JLabel image = new JLabel(new ImageIcon("graphique/images/titre.png"));
    title.add(image);

    int fontSize = 20;

    JButton bPlay = new JButton("Play");
    bPlay.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bPlay.setFocusable(false);
    bPlay.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
          Board b = new Board();
          MapReader map = new MapReader("");
          map.setFile("maps/map1.xsb");
          map.readingMap();
          b.createGrid(map.getMap());
          Menu.this.dispose();
          new Interface(b,map,false,false,false);
      }
    });

    JButton bMap = new JButton("Select map");
    bMap.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bMap.setFocusable(false);
    bMap.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
          Menu.this.dispose();
          new SelectMap ();
      }
    });

    JButton bIa = new JButton("IA battle");
    bIa.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bIa.setFocusable(false);
    bIa.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
        int nbMaps = new File("maps").list().length - 2;
        Random r = new Random();
        int n = r.nextInt(nbMaps) + 1;
        Board b = new Board();
        MapReader map = new MapReader("");
        map.setFile("maps/map" + n + ".xsb");
        map.readingMap();
        b.createGrid(map.getMap());
        Menu.this.dispose();
        new Interface(b,map,true,false,false);
      }
    });

    JButton bRand = new JButton("Map random");
    bRand.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bRand.setFocusable(false);
    bRand.addActionListener(new ActionListener () {
      public void actionPerformed(ActionEvent e){
        int nbMaps = new File("maps").list().length - 2;
        Random r = new Random();
        int n = r.nextInt(nbMaps) + 1;
        Board b = new Board();
        MapReader map = new MapReader("");
        map.setFile("maps/map" + n + ".xsb");
        map.readingMap();
        b.createGrid(map.getMap());
        Menu.this.dispose();
        new Interface(b,map,false,false,true);
      }
    });

    JButton bQuit = new JButton("Quit");
    bQuit.setFont(new Font("Monospace", Font.BOLD, fontSize));
    bQuit.setFocusable(false);
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
    menu.setLayout(new GridLayout(5,1,10,10));


    contenent.setLayout(new BorderLayout());
    contenent.setOpaque(false);

    title.setOpaque(false);
    contenent.add(title,BorderLayout.NORTH);

    contenent.add(backgroundButton,BorderLayout.SOUTH);

    JLabel background = new JLabel(new ImageIcon("graphique/images/fondMenu.png"));
    background.setLayout(new FlowLayout());
    background.add(contenent);

    this.add(background);

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }
}
