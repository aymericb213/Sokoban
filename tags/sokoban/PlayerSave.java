package sokoban;

import java.util.ArrayList;
import java.io.*;

/**
  * Classe permettant de sauvegarder un joueur.
  */
public class PlayerSave {

  private String path;
  private String pathSave;
  private String pathCancel;
  private int level;

  /**
    * Constructeur de la classe.
    * Initialise les chemins des fichiers correspondant au joueur.
    * @param name
		* Nom du joueur.
    * @param level
		* Dernier niveau que le joueur a débloqué.
    */
  public PlayerSave (String name, int level) {
    this.path = "save/players/" + name + ".txt";
    this.pathSave = "save/" + name + ".xsb";
    this.pathCancel = "save/cancel_" + name + ".xsb";
    this.level = level;
  }

  /**
    * Constructeur de la classe.
    * Initialise les chemins des fichiers correspondant au joueur
		* @param name
		* Nom du joueur.
    */
  public PlayerSave (String name) {
    this(name,1);
  }

  /**
    * Getter du chemin de retour au dernier coup.
    * @return le chemin du fichier de retour.
    */
  public String getPathCancel() {
    return this.pathCancel;
  }

  /**
    * Sauvegarde le joueur au fichier this.path.
    */
  public void savePlayer() {
    try {
      File file = new File(this.path);

      if (file.exists()){
        file.delete();
      }
      FileWriter fw = new FileWriter(this.path,false);

      BufferedWriter output = new BufferedWriter(fw);
      String temp = "" + this.level;
      output.write(temp);

      output.flush();

      output.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /**
    * Supprime le fichier donné si il existe.
    * @param file
		* Fichier à supprimer.
    */
  public void deleteFile(File file) {
    if (file.exists()){
      file.delete();
    }
  }

  /**
    * Supprime le fichier du joueur, la sauvegarde et le retour si
    * ils existent.
    */
  public void deletePlayer() {
      File file = new File(this.path);
      File fileSave = new File(this.pathSave);
      File fileCancel = new File(this.pathCancel);
      deleteFile(file);
      deleteFile(fileSave);
      deleteFile(fileCancel);
  }

}
