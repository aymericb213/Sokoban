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

/**
  * Fenetre du mode de selection de niveau
  */
public class SelectMap extends JFrame {

  private JList<String> list;
  private String playerName;
  private JPanel zoneCanvas;
  private CanvasGame can;
  private JToggleButton toggleButton;
  private int sizeTile;
  private boolean modeIad;

  public SelectMap (boolean modeIad, String playerName) {

    this.modeIad = modeIad;
    this.playerName = playerName;

    this.sizeTile = 18;

    this.setResizable(true);
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
          new Interface(b,map,SelectMap.this.playerName,SelectMap.this.modeIad,true,false,SelectMap.this.toggleButton.isSelected());
        }
      }
    });

    JPanel zoneAnyTime = new JPanel();

    JLabel labAnyTime = new JLabel("Any Time : ");

    this.toggleButton = new JToggleButton("No", false);
    this.toggleButton.setRequestFocusEnabled(false);
    toggleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!SelectMap.this.toggleButton.isSelected()) {
          SelectMap.this.toggleButton.setText("No");
          SelectMap.this.toggleButton.setSelected(false);
        } else {
          SelectMap.this.toggleButton.setText("Yes");
          SelectMap.this.toggleButton.setSelected(true);
        }
        SelectMap.this.toggleButton.repaint();
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

    if (this.modeIad) {
      zoneAnyTime.add(labAnyTime);
      zoneAnyTime.add(this.toggleButton);
      zoneAnyTime.setLayout(new GridLayout(1,2));
      zoneButton.add(zoneAnyTime);
    }
    zoneButton.add(bBack);
    zoneButton.setLayout(new GridLayout(3,1,10,10));

    int nbMapsTotal = new File("maps").list(new FilenameFilter() {
																						@Override
																						public boolean accept(File dir, String name) {
																							return name.matches("^map...xsb$") || name.matches("^map..xsb$");
																						}
																					}).length;
    PlayerReader player = new PlayerReader(this.playerName);
    int levelPlayerMax = player.getLevel();

    JLabel numberMapUnlocked = new JLabel("Map Unlocked " + levelPlayerMax + "/" + nbMapsTotal);
    numberMapUnlocked.setHorizontalAlignment(SwingConstants.CENTER);

    Vector<String> vect = new Vector<>();
    for (int i = 1; i<(levelPlayerMax+1); i++) {
      vect.add("Map " + i);
    }
    this.list = new JList<>(vect);
    this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    DefaultListCellRenderer centrer = (DefaultListCellRenderer)this.list.getCellRenderer();
    centrer.setHorizontalAlignment(SwingConstants.CENTER);
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
        CanvasGame can = SelectMap.this.can;
        can.setBoard(b);
        int[] sizeGrid = b.getSize();
        int sizeTile = SelectMap.this.sizeTile;
        can.setPreferredSize(new Dimension(sizeTile*sizeGrid[0],sizeTile*sizeGrid[1]));
        can.update();
        SelectMap.this.zoneCanvas.updateUI();
      }
    });

    JPanel listAndNbMapUnlocked = new JPanel();
    listAndNbMapUnlocked.setLayout(new GridBagLayout());
    GridBagConstraints cList = new GridBagConstraints();
    cList.gridx = 0;
    cList.gridy = 0;
    listAndNbMapUnlocked.add(numberMapUnlocked,cList);
    cList.gridy = 1;
    scrollPane.setPreferredSize(new Dimension(100,300));
    listAndNbMapUnlocked.add(scrollPane,cList);

    Board b = new Board(1);
    MapReader map = new MapReader("maps/map1.xsb", "save/cancel_" + SelectMap.this.playerName + ".xsb");
    map.readingMap();
    b.createGrid(map.getMap());

    this.zoneCanvas = new JPanel();
    zoneCanvas.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
    zoneCanvas.setPreferredSize(new Dimension(400,320));

    this.can = new CanvasGame(b,sizeTile);
    zoneCanvas.add(this.can);

    JPanel contenent = new JPanel();

    contenent.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();
    gc.fill = GridBagConstraints.HORIZONTAL;

    gc.gridx = 0;
    gc.gridy = 0;
    contenent.add(zoneButton,gc);
    gc.gridx = 1;
    contenent.add(listAndNbMapUnlocked,gc);
    gc.gridx = 2;
    gc.anchor = GridBagConstraints.CENTER;
    contenent.add(zoneCanvas,gc);

    this.add(contenent);
    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
