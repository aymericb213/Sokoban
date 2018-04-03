
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

/**
  * Fenetre du menu principal
  */
public class Menu extends JFrame {

  private String playerName;

  public Menu (String playerName) {

    this.playerName = playerName;

    this.setResizable(false);
    this.setTitle("Main menu (" + this.playerName + ")");

    JPanel allContenent = new JPanel();
    allContenent.setPreferredSize(new Dimension(700,600));

    JPanel contenent = new JPanel();

    JPanel title = new JPanel();
    JLabel image = new JLabel(new ImageIcon("../ressources/images/titre.png"));
    title.add(image);

    int fontSize = 20;

    JButton bPlay = new JButton("Play");
    bPlay.setFont(new Font("Arial", Font.BOLD, fontSize));
    bPlay.setFocusable(false);
    bPlay.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
          PlayerReader player = new PlayerReader(Menu.this.playerName);
          int niveau = player.getLevel();
          Board b = new Board(niveau);
          b.setPlayerName(Menu.this.playerName);
          MapReader map = new MapReader("../ressources/maps/map" + niveau + ".xsb", "../ressources/save/cancel_" + Menu.this.playerName + ".xsb");
          map.readingMap();
          b.createGrid(map.getMap());
          Menu.this.dispose();
          new Interface(b,map,Menu.this.playerName,false,false,false);
      }
    });

    JButton bMap = new JButton("Select map");
    bMap.setFont(new Font("Arial", Font.BOLD, fontSize));
    bMap.setFocusable(false);
    bMap.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
          Menu.this.dispose();
          new SelectMap (false,Menu.this.playerName);
      }
    });

    JButton bIa = new JButton("IA battle");
    bIa.setFont(new Font("Arial", Font.BOLD, fontSize));
    bIa.setFocusable(false);
    bIa.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
        Menu.this.dispose();
        new SelectMap (true, Menu.this.playerName);
      }
    });

    JButton bRand = new JButton("Map random");
    bRand.setFont(new Font("Arial", Font.BOLD, fontSize));
    bRand.setFocusable(false);
    bRand.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
        PlayerReader player = new PlayerReader(Menu.this.playerName);
        int nbMaps = player.getLevel();
        Random r = new Random();
        int n = r.nextInt(nbMaps) + 1;
        Board b = new Board(n);
        b.setPlayerName(Menu.this.playerName);
        MapReader map = new MapReader("../ressources/maps/map" + n + ".xsb","../ressources/save/cancel_" + Menu.this.playerName + ".xsb");
        map.readingMap();
        b.createGrid(map.getMap());
        Menu.this.dispose();
        new Interface(b,map,Menu.this.playerName,false,false,true);
      }
    });

    JButton bQuit = new JButton("Quit");
    bQuit.setFont(new Font("Arial", Font.BOLD, fontSize));
    bQuit.setFocusable(false);
    bQuit.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
          Menu.this.dispose();
      }
    });

    JLabel backgroundButton = new JLabel(new ImageIcon("../ressources/images/caisseMenu.png"));

    JPanel menu = new JPanel();
    backgroundButton.setLayout(new FlowLayout(FlowLayout.CENTER,0,95));
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

    JLabel background = new JLabel(new ImageIcon("../ressources/images/fondMenu.png"));
    background.setLayout(new FlowLayout());
    background.add(contenent);

    allContenent.add(background);
    this.add(allContenent);
    this.setLayout(new FlowLayout(FlowLayout.CENTER,0,-5));
    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }
}
