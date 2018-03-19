
package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import sokoban.*;
import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;

/**
  * Fenetre de jeu pour tous les modes
  */
public class Interface extends JFrame {

  private Board b;
  private MapReader map;
  private CanvasGame can;
  private String playerName;
  private boolean modeIad;
  private boolean modeSelect;
  private boolean random;
  private static int nbMapPlay = 1;

  public Interface(Board b, MapReader map, String playerName, boolean modeIad, boolean modeSelect, boolean random) {
    this.b = b;
    nbMapPlay = b.getLevel();
    this.map = map;
    this.playerName = playerName;
    this.modeIad = modeIad;
    this.modeSelect = modeSelect;
    this.random = random;
    this.setResizable(false);
    this.setTitle("Sokoban");

    JPanel zoneControl = new JPanel();

    JLabel numberMap = new JLabel("Map " + nbMapPlay);
    numberMap.setHorizontalAlignment(SwingConstants.CENTER);

    JButton bReset = new JButton("Restart");
    bReset.setRequestFocusEnabled(false);
    bReset.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e) {
        Interface.this.map.readingMap();
        Interface.this.b.createGrid(Interface.this.map.getMap());
        Interface.this.b.setOver(false);
        Interface.this.can.setPlayer("graphique/images/perso.png");
        Interface.this.can.update();
      }
    });

    JButton bSave = new JButton("Save");
    bSave.setRequestFocusEnabled(false);
    bSave.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e) {
        Save save = new Save(b.createArrayList(),Interface.this.playerName, "map" + Interface.this.b.getLevel());
        save.saveMap();
        JOptionPane popupSave = new JOptionPane();
        Timer timer = new Timer(1000,new ActionListener () {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.getRootFrame().dispose();
          }
        });
        timer.start();
        JOptionPane.showMessageDialog(null, "Game saved", "Save", JOptionPane.INFORMATION_MESSAGE);
        timer.stop();

      }
    });

    JButton bLoad = new JButton("Load");
    bLoad.setRequestFocusEnabled(false);
    bLoad.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] savesPlayers = new File("save/").list();
        ArrayList<String> listSavesPlayers = new ArrayList<>(Arrays.asList(savesPlayers));
        if (listSavesPlayers.contains(Interface.this.playerName + ".xsb")) {
          MapReader mapLoad = new MapReader("save/" + Interface.this.playerName + ".xsb", Interface.this.map.getFileCancel());
          mapLoad.readingMap();
          Interface.this.b.createGrid(mapLoad.getSaveMap());
          Interface.this.can.update();
          mapLoad.readingSaveMap();
          Interface.this.map = mapLoad;
          Interface.this.b.setOver(false);
          Interface.this.dispose();
          new Interface(Interface.this.b, Interface.this.map, Interface.this.playerName, Interface.this.modeIad, Interface.this.modeSelect, Interface.this.random);
          Timer timer = new Timer(1000,new ActionListener () {
            public void actionPerformed(ActionEvent e) {
              JOptionPane.getRootFrame().dispose();
            }
          });
          timer.start();
          JOptionPane.showMessageDialog(null, "Game loaded", "Load", JOptionPane.INFORMATION_MESSAGE);
          timer.stop();
        } else {
          Timer timer = new Timer(1000,new ActionListener () {
            public void actionPerformed(ActionEvent event) {
              JOptionPane.getRootFrame().dispose();
            }
          });
          timer.start();
          JOptionPane.showMessageDialog(null, "No save found.", "Load", JOptionPane.INFORMATION_MESSAGE);
          timer.stop();
        }
      }
    });


    JButton bCancel = new JButton("Cancel");
    bCancel.setRequestFocusEnabled(false);
    bCancel.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e){
          String[] savesPlayers = new File("save/").list();
          ArrayList<String> listSavesPlayers = new ArrayList<>(Arrays.asList(savesPlayers));
          boolean popup = false;
          if (listSavesPlayers.contains("cancel_" + Interface.this.playerName + ".xsb")) {
            MapReader cancelMap = new MapReader("save/cancel_" + Interface.this.playerName + ".xsb");
            String cMap = cancelMap.getCancelMapName();
            if (cMap.equals("map" + Interface.this.b.getLevel())) {
              Interface.this.map.readingCancel();
              Interface.this.b.createGrid(Interface.this.map.getCancel());
              if (!Interface.this.b.isFinished()) {
                Interface.this.b.setOver(false);
                Interface.this.can.setPlayer("graphique/images/perso.png");
              } else {
                Interface.this.can.setPlayer("graphique/images/persoPerdu.png");
              }
              Interface.this.can.update();
            } else {
              popup = true;
            }
          } else {
            popup = true;
          }
          if (popup) {
            Timer timer = new Timer(1000,new ActionListener () {
              public void actionPerformed(ActionEvent event) {
                JOptionPane.getRootFrame().dispose();
              }
            });
            timer.start();
            JOptionPane.showMessageDialog(null, "Can't cancel move.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
            timer.stop();
          }
        }
    });

    JButton bQuit = new JButton("Back to menu");
    bQuit.setRequestFocusEnabled(false);
    bQuit.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e){
          Interface.nbMapPlay = 1;
          Interface.this.dispose();
          new Menu(Interface.this.playerName);
        }
    });

    this.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    zoneControl.setLayout(new GridLayout(7,1,10,10));

    zoneControl.add(numberMap);
    zoneControl.add(bReset);


    if (!this.modeIad && !this.modeSelect && !this.random) {
      nbMapPlay ++;
      JButton bNext = new JButton("Next level");
      bNext.setRequestFocusEnabled(false);
      bNext.setFocusable(false);
      bNext.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
          int nbMaps = new File("maps").list().length;
          PlayerReader player = new PlayerReader(Interface.this.playerName);
          int levelPlayerMax = player.getLevel();
          if (nbMapPlay - 1 == nbMaps) {
            JOptionPane popupLoad = new JOptionPane();
            Timer timer = new Timer(3000,new ActionListener () {
              @Override
              public void actionPerformed(ActionEvent e) {
                JOptionPane.getRootFrame().dispose();
                Interface.nbMapPlay = 1;
                Interface.this.dispose();
                new Menu(Interface.this.playerName);
              }
            });
            timer.start();
            JOptionPane.showMessageDialog(null, "All maps are played, back to menu.", "End", JOptionPane.INFORMATION_MESSAGE);
            timer.stop();

          } else if (nbMapPlay - 1 == levelPlayerMax) {
            JOptionPane popupLoad = new JOptionPane();
            Timer timer = new Timer(2000,new ActionListener () {
              @Override
              public void actionPerformed(ActionEvent e) {
                JOptionPane.getRootFrame().dispose();
              }
            });
            timer.start();
            JOptionPane.showMessageDialog(null, "The next map isn't unlocked.", "Error", JOptionPane.INFORMATION_MESSAGE);
            timer.stop();

          } else {
            Board b = new Board(Interface.nbMapPlay);
            b.setPlayerName(Interface.this.playerName);
            MapReader map = new MapReader("");
            map.setFile("maps/map" + Interface.nbMapPlay + ".xsb");
            map.readingMap();
            b.createGrid(map.getMap());
            Interface.this.dispose();
            new Interface(b,map,Interface.this.playerName,false,false,false);
          }
        }
      });
      zoneControl.add(bNext);
    } else {
      nbMapPlay = 1;
    }

    zoneControl.add(bSave);
    zoneControl.add(bLoad);
    zoneControl.add(bCancel);

    if (this.modeSelect) {

      JButton bSelect = new JButton("Select map");
      bSelect.setRequestFocusEnabled(false);
      bSelect.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.this.dispose();
            new SelectMap(Interface.this.modeIad, Interface.this.playerName);
        }
      });
      zoneControl.add(bSelect);
    } else if (this.random ||this.modeIad) {

      JButton bRand = new JButton("Other map");
      bRand.setRequestFocusEnabled(false);
      bRand.addActionListener(new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e){
          PlayerReader player = new PlayerReader(Interface.this.playerName);
          int nbMaps = player.getLevel();
          Random r = new Random();
          int n = r.nextInt(nbMaps) + 1;
          Board b = new Board(n);
          b.setPlayerName(Interface.this.playerName);
          MapReader map = new MapReader("");
          map.setFile("maps/map" + n + ".xsb");
          map.readingMap();
          b.createGrid(map.getMap());
          Interface.this.dispose();
          if (Interface.this.random) {
            new Interface(b,map,Interface.this.playerName,false,false,true);
          }
        }
      });
      zoneControl.add(bRand);
    }

    zoneControl.add(bQuit);

    gc.gridx = 0;
		gc.gridy = 0;
    if (this.modeIad) {
      this.can = new CanvasGame(this.b, 30);
    } else {
      this.can = new CanvasGame(this.b, 40);
    }


    KeyAction key =  new KeyAction (this.b, this.can);
    this.setFocusable(true);

    addKeyListener(key);

    add(can,gc);
    gc.gridx = 1;
    add(zoneControl,gc);

    if (this.modeIad) {
      Board bIa = new Board(nbMapPlay);
      bIa.createGrid(map.getMap());
      CanvasGame canIa = new CanvasGame(bIa,30);
      canIa.setFocusable(false);
      gc.gridx = 2;
      add(canIa,gc);
    }


    pack();

    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
