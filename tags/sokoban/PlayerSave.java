package sokoban;

import java.util.ArrayList;
import java.io.*;


public class PlayerSave {

  private String path;
  private int level;

  public PlayerSave (String name, int level) {
    this.path = "save/players/" + name + ".txt";
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

}
