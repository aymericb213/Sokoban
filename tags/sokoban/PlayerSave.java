package sokoban;

import java.util.ArrayList;
import java.io.*;


public class PlayerSave {

  private String path;
  private String pathSave;
  private String pathCancel;
  private int level;

  public PlayerSave (String name, int level) {
    this.path = "save/players/" + name + ".txt";
    this.pathSave = "save/" + name + ".xsb";
    this.pathCancel = "save/cancel_" + name + ".xsb";
    this.level = level;
  }

  public PlayerSave (String name) {
    this(name,1);
  }

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

  public void deleteFile(File file) {
    if (file.exists()){
      file.delete();
    }
  }

  public void deletePlayer() {
      File file = new File(this.path);
      File fileSave = new File(this.pathSave);
      File fileCancel = new File(this.pathCancel);
      deleteFile(file);
      deleteFile(fileSave);
      deleteFile(fileCancel);
  }

}
