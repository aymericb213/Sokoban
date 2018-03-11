package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

/**
	* Classe permettant la lecture de joueur.txt et le niveau auquel il est arrivé
	* sous la forme d'un entier
*/
public class PlayerReader {

  private String file;
  private int level;

  public PlayerReader(String name) {
    this.file = "save/players/" + name + ".txt";
    this.level = this.readLevel(this.file);
  }

	/**
		* Accesseur de l'attribut file.
		* @return La valeur de file.
	*/
	public String getFile() {
		return this.file;
	}

	/**
		* Mutateur de l'attribut map.
		* @param newfile
		* Le nouveau chemin de fichier.
	*/
	public void setFile(String newfile) {
		this.file=newfile;
	}

  public int getLevel() {
    return this.level;
  }

  public int readLevel(String file) {
    try {
      BufferedReader read = new BufferedReader(new FileReader(file));
      int lvl = Integer.parseInt(read.readLine());
      return lvl;
    } catch (IOException e) {
      e.printStackTrace();
      return 1;
    }
  }
}
