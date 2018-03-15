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

  private JList<String> list;
  private String playerName;
  private CanvasGame can;
  private boolean modeIad;

  public SelectMap (boolean modeIad, String playerName) {

    this.modeIad = modeIad;
    this.playerName = playerName;

    int sizeTile = 20;

    this.setSize(700,350);
    this.setResizable(false);
    this.setTitle("Select map");

    JPanel zoneButton = new JPanel ();

    JButton bPlay = new JButton("Play");
    bPlay.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
        int indice = SelectMap.this.list.getSelectedIndex();
        if (!SelectMap.this.list.isSelectionEmpty()) {
          Board b = new Board(indice + 1);
          b.setPlayerName(SelectMap.this.playerName);
          MapReader map = new MapReader("maps/map" + (indice + 1) + ".xsb", "save/cancel_" + SelectMap.this.playerName + ".xsb");
          map.readingMap();
          b.createGrid(map.getMap());
          SelectMap.this.dispose();
          new Interface(b,map,SelectMap.this.playerName,SelectMap.this.modeIad,true,false);
        }
      }
    });

    JButton bBack = new JButton("Back to menu");
    bBack.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e){
          SelectMap.this.dispose();
          new Menu(SelectMap.this.playerName);
      }
    });

    zoneButton.add(bPlay);
    zoneButton.add(bBack);
    zoneButton.setLayout(new GridLayout(2,1));

    Vector<String> vect = new Vector<>();
    PlayerReader player = new PlayerReader(this.playerName);
    int nbMaps = player.getLevel();
    for (int i = 1; i<(nbMaps+1); i++) {
      vect.add("Map " + i);
    }
    this.list = new JList<>(vect);
    this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.list.setSelectedIndex(0);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(list);

    this.list.addListSelectionListener(new ListSelectionListener () {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int indice = SelectMap.this.list.getSelectedIndex();
        Board b = new Board(indice + 1);
        b.setPlayerName(SelectMap.this.playerName);
        MapReader map = new MapReader("maps/map" + (indice + 1) + ".xsb", "save/cancel_" + SelectMap.this.playerName + ".xsb");
        map.readingMap();
        b.createGrid(map.getMap());
        SelectMap.this.can.setBoard(b);
        SelectMap.this.can.update();
      }
    });

    Board b = new Board(1);
    MapReader map = new MapReader("maps/map1.xsb", "save/cancel_" + SelectMap.this.playerName + ".xsb");
    map.readingMap();
    b.createGrid(map.getMap());

    this.can = new CanvasGame(b,sizeTile);

    JPanel contenent = new JPanel();

    contenent.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.fill = GridBagConstraints.HORIZONTAL;

    gc.gridx = 0;
    gc.gridy = 0;
    gc.weightx = 0.2;
    contenent.add(zoneButton,gc);
    gc.gridx = 1;
    gc.weightx = 2;
    contenent.add(scrollPane,gc);
    gc.gridx = 2;
    gc.weightx = 4;
    contenent.add(this.can,gc);

    this.add(contenent);

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
