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

	/**
		* Constructeur de la classe.
		* Initialise le tableau destiné à contenir les chaînes de caractères lues.
		* @param file
		* Chemin d'un fichier .xsb.
	*/
  public MapReader(String file) {
    this.file = file;
    this.map = new ArrayList<> ();
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
