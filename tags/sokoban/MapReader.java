package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;

/**
	* Classe permettant la lecture de fichiers .xsb et l'extraction de leur contenu
	* sous la forme d'un tableau de chaînes de caractères.
*/
public class MapReader {

  private String file;
  protected ArrayList<String> map;
  protected ArrayList<String> cancel;

	/**
		* Constructeur de la classe.
		* Initialise le tableau destiné à contenir les chaînes de caractères lues.
		* @param file
		* Chemin d'un fichier .xsb.
	*/
  public MapReader(String file) {
    this.file = file;
    this.map = new ArrayList<> ();
    this.cancel = new ArrayList<> ();
  }

	/**
		* Lit le fichier .xsb trouvé avec l'attribut file et en extrait un tableau de chaînes de caractères.
	*/
  public void readingMap() {
    try {
      BufferedReader map = new BufferedReader (new FileReader (this.file));
      ArrayList<String> mapToString = new ArrayList<>();
      String line;
      while ((line = map.readLine()) != null) {
        mapToString.add(line);
      }
      this.map = mapToString;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
		* Lit le fichier .xsb trouvé avec l'attribut file et en extrait la map d'origine.
	*/
  public String getCancelMapName() {
    String line = "";
    try {
      BufferedReader cancel = new BufferedReader (new FileReader (this.file));
      line = cancel.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return line;
  }

  /**
		* Lit le fichier .xsb trouvé avec l'attribut file et en extrait un tableau de chaînes de caractères.
	*/
  public void readingCancel() {
    try {
      BufferedReader cancel = new BufferedReader (new FileReader (this.file));
      ArrayList<String> mapToString = new ArrayList<>();
      String line;
      String firstLine = cancel.readLine();
      while ((line = cancel.readLine()) != null) {
        mapToString.add(line);
      }
      this.cancel = mapToString;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void readingSaveMap() {
    try {
      BufferedReader map = new BufferedReader (new FileReader (this.file));
      ArrayList<String> mapToString = new ArrayList<>();
      String line = map.readLine();
      this.file = "maps/" + line + ".xsb";
      while ((line = map.readLine()) != null) {
        mapToString.add(line);
      }
      this.map = mapToString;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
    * recupere le nom de la map dans file
  */
  public String getName() {
    String name = "";
    boolean point = false;
    int i = this.file.length() - 1;
    char ch = this.file.charAt(i);

    while (ch != '/') {
      if (point) {
        name = ch + name;
      }
      if (ch == '.') {
        point = true;
      }
      i -= 1;
      ch = this.file.charAt(i);
    }
    return name;
  }


	/**
		* Accesseur de l'attribut map.
		* @return La valeur de map.
	*/
  public ArrayList<String> getMap() {
		ArrayList<String> mapCopy = new ArrayList<> ();
    for (int i = 0; i<this.map.size(); i++) {
      mapCopy.add(this.map.get(i));
    }
    return mapCopy;
  }

  /**
		* Accesseur de l'attribut map pour charger une sauvegarde.
		* @return La valeur de map.
	*/
  public ArrayList<String> getSaveMap() {
    ArrayList<String> mapCopy = new ArrayList<> ();
    for (int i = 1; i<this.map.size(); i++) {
      mapCopy.add(this.map.get(i));
    }
    return mapCopy;
  }

  /**
    * Accesseur de l'attribut cancel.
    * @return La valeur de cancel.
  */
  public ArrayList<String> getCancel() {
		ArrayList<String> mapCopy = new ArrayList<> ();
    for (int i = 0; i<this.cancel.size(); i++) {
      mapCopy.add(this.cancel.get(i));
    }
    return mapCopy;
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
}
