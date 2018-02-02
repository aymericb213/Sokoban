package sokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;

public class MapReader {

  private String file;
  protected ArrayList<ArrayList<String>> map;

  public MapReader(String file) {
    this.file = file;
    this.map = new ArrayList<> ();
  }

  public void readingMap() throws IOException {
    BufferedReader map = new BufferedReader (new FileReader (this.file));
    ArrayList<ArrayList<String>> mapToString = new ArrayList<>();
    String line;
    while ((line = map.readLine()) != null) {
      ArrayList<String> lineToString = new ArrayList<>();
      String[] line2 = line.split("");
      for (int i = 0; i<line2.length; i++){
        lineToString.add(line2[i]);
      }
      mapToString.add(lineToString);
    }
    this.map = mapToString;
  }

  public ArrayList<ArrayList<String>> getMap() {
    ArrayList<ArrayList<String>> mapCopy = new ArrayList<> ();
    for (int i = 0; i<this.map.size(); i++) {
      mapCopy.add(new ArrayList<String>());
      for (int j = 0; j<this.map.get(i).size(); j++) {
        mapCopy.get(i).add(this.map.get(i).get(j));
      }
    }
    return mapCopy;
  }

	public void setFile(String newfile) {
		this.file=newfile;
	}
}
