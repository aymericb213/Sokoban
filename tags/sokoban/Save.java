package sokoban;

import java.util.ArrayList;
import java.io.*;


public class Save {

  private ArrayList<String> map = new ArrayList<>();
  private String path;
  private String name;


  public Save(ArrayList<String> mapToSave, String name) {
    this.map = mapToSave;
    this.path = "save/save.xsb";
    this.name = name;
  }

  public Save(ArrayList<String> mapToSave, String file, String name) {
    this.map = mapToSave;
    this.path = "save/" + file + ".xsb";
    this.name = name;
  }

  public void saveMap () {
    try {
      File file = new File(this.path);

      if (file.exists()){
        file.delete();
      }
      FileWriter fw = new FileWriter(this.path,false);

      BufferedWriter output = new BufferedWriter(fw);
      String temp = "";
      temp += this.name + "\n";
      for (String line : this.map) {
        temp += line + "\n";
      }
      
      output.write(temp);

      output.flush();

      output.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

}
