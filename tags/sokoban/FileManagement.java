package sokoban;

public class FileManagement {

  public FileManagement() {

  }

  public void setSave(Board b, String playerName, PlayerReader playerLevel) {
    Save save = new Save (b.createArrayList(),playerName,("map"+playerLevel.getLevel()));
    save.saveMap();
    System.out.println("\033[H\033[2J");
    System.out.println("Fichier sauvegardé");
  }

  public MapReader loadMap(String playerName, MapReader map) {
    map.setFile("save/"+playerName+".xsb");
    map.readingMap();
    return map;
  }

  public MapReader loadCancel(String playerName, MapReader map) {
    map.setFile("save/cancel_"+playerName+".xsb");
    map.readingMap();
    return map;
  }

  public void setCancel(Board b, String playerName, MapReader map, PlayerReader playerLevel) {
    Save cancel = new Save (b.createArrayList(),("cancel_"+playerName),("map"+playerLevel.getLevel()));
    cancel.saveMap();
  }

}
