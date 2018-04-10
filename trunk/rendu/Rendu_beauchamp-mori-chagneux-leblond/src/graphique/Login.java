package graphique;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.util.*;
import sokoban.*;
import java.io.*;

/**
  * Fenetre de choix de joueur
  */
public class Login extends JFrame {

  private JList<String> list;
  private Vector<String> vect;
  private JTextField textRegister;

  public Login () {

    this.setTitle("Login");
    this.setResizable(false);

    JPanel zoneLogin = new JPanel();
    JPanel zoneRegister = new JPanel();

    this.vect = new Vector<>();
    String[] players = new File("../ressources/save/players").list();
    for (String s : players){
      vect.add(s.split("\\.")[0]);
    }
    this.list = new JList<>(vect);
    this.list.setPreferredSize(new Dimension(150,200));
    this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.list.setSelectedIndex(0);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(this.list);

    JButton bLogin = new JButton("Login");
    bLogin.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        Login.this.dispose();
        new Menu(Login.this.list.getSelectedValue());
      }
    });

    JButton bDelete = new JButton("Delete");
    bDelete.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = Login.this.list.getSelectedValue();
        int askDel = JOptionPane.showConfirmDialog (null, "Do you want to delete " + name + " ?","Delete",JOptionPane.YES_NO_OPTION);
        if (askDel == JOptionPane.YES_OPTION) {
          PlayerSave delPlayer = new PlayerSave(name);
          delPlayer.deletePlayer();
          Login.this.dispose();
          new Login();
        }
      }
    });

    JLabel lab = new JLabel("Enter your name :");

    JButton bRegister = new JButton("Register");
    bRegister.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e) {
        String newPlayer = Login.this.textRegister.getText();
        if (newPlayer.length() == 0 || newPlayer.length() >= 15) {
          JOptionPane.showMessageDialog(null, "The length of your name must be between 1 and 15 characters.", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else if (Login.this.vect.contains(newPlayer)) {
          JOptionPane.showMessageDialog(null, "This name is already choisen.", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
          PlayerSave newPlayerSave = new PlayerSave(newPlayer);
          newPlayerSave.savePlayer();
          Login.this.dispose();
          new Menu(newPlayer);
        }
      }
    });

    this.textRegister = new JTextField();
    this.textRegister.setHorizontalAlignment(JTextField.CENTER);
    this.textRegister.setPreferredSize(new Dimension(150,25));

    zoneLogin.setLayout(new GridBagLayout());
    GridBagConstraints gcg = new GridBagConstraints();

    gcg.gridx = 0;
    gcg.gridy = 0;
    zoneLogin.add(scrollPane,gcg);
    gcg.gridy = 1;
    zoneLogin.add(bLogin,gcg);
    gcg.gridy = 2;
    zoneLogin.add(bDelete,gcg);

    zoneRegister.setLayout(new GridBagLayout());
    GridBagConstraints gcd = new GridBagConstraints();

    gcd.gridx = 0;
    gcd.gridy = 0;
    zoneRegister.add(lab,gcd);
    gcd.gridy = 1;
    zoneRegister.add(this.textRegister,gcd);
    gcd.gridy = 2;
    zoneRegister.add(bRegister,gcd);

    this.setLayout(new GridLayout(1,2));
    this.add(zoneLogin);
    this.add(zoneRegister);

    pack();
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

  }

}
