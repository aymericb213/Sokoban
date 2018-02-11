package sokoban;

import java.util.ArrayList;

/**
	* Classe représentant un niveau de Sokoban.
*/
public class Board {

  protected Block[][] grid;
  protected ArrayList<Block> listCrate;
  protected ArrayList<Block> listObjective;
  protected Block player;

	/**
		* Constructeur de la classe.
		* Initialise les listes de caisses et d'objectifs.
	*/
  public Board() {
		this.listCrate = new ArrayList<>();
		this.listObjective = new ArrayList<>();
  }

	/**
		* Retourne la représentation du niveau affichée en console.
		* @return La chaîne de caractère représentant le niveau.
	*/
  public String toString() {
    String result = "";
    for (int i=0; i < this.grid.length; i++) {
      for (int j=0; j < this.grid[0].length; j++) {
        if (this.grid[i][j] != null) {
          result += this.grid[i][j].toString();
        }
      }
      result += "\n";
    }
    return result;
  }

	/**
		* Teste si une caisse est bloquée.
		* @return Le résultat du test.
	*/
  public boolean isDead(Block c, int i, int j, boolean wallOnly) {
    if ((this.grid[i-1][j] instanceof Crate || this.grid[i+1][j] instanceof Crate) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
      return true;
    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Crate || this.grid[i][j+1] instanceof Crate)) {
      return true;
    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
      return true;
    } else if (!wallOnly && (this.grid[i-1][j] instanceof Crate || this.grid[i+1][j] instanceof Crate) &&
    (this.grid[i][j-1] instanceof Crate || this.grid[i][j+1] instanceof Crate)) {
      return true;
    }
    return false;
  }

	/**
		*	Teste si toutes les caisses sont placées sur les objectifs.
		* @return Le résultat du test.
	*/
  public boolean allPlaced() {
    for (Block c : this.listCrate) {
      if (!((Crate)c).isPlaced()) {
        return false;
      }
    }
    return true;
  }

  /**
    * Ajoute la coordonnées [j,i] dans la liste listCoord
    * @param c La caisse en [j,i]
    * @param i La coordonnée en Y
    * @param j La coordonnée en X
    * @param listCoord La liste des coordonnée dont on va ajouter [j,i]
    * @note Cette fonction sert uniquement pour <i>crateChain</i>.
  */
  private void addListCoord(Crate c, int i, int j, ArrayList<ArrayList<Integer>> listCoord) {
    ArrayList<Integer> listTest = new ArrayList<> ();
    listTest.add(j);
    listTest.add(i);
    if (!listCoord.contains(listTest)) {
      crateChain(c,i,j,listCoord);
    }
  }

  /**
    * Créer une liste des coordonnées d'une chaîne de caisses qui sont les unes à côté des autres
    * @param c La caisse étant en [j,i]
    * @param i La coordonnée en Y de la caisse
    * @param j La coordonnée en X de la caisse
    * @param listCoord Liste des coordonnée des caisses
    * @return La liste des coordonnée de la chaîne
  */
  public ArrayList<ArrayList<Integer>> crateChain (Crate c, int i, int j, ArrayList<ArrayList<Integer>> listCoord) {
    ArrayList<Integer> listTemp = new ArrayList<> ();
    listTemp.add(j);
    listTemp.add(i);
    listCoord.add(listTemp);

    if (this.isDead(c,i,j,false) && !((Crate)c).isPlaced()) {
      if (this.grid[i-1][j] instanceof Crate && this.isDead(this.grid[i-1][j],i-1,j,false)) {
        addListCoord(c,i-1,j,listCoord);
      }

      if (this.grid[i+1][j] instanceof Crate && this.isDead(this.grid[i+1][j],i+1,j,false)) {
        addListCoord(c,i+1,j,listCoord);
      }

      if (this.grid[i][j-1] instanceof Crate && this.isDead(this.grid[i][j-1],i,j-1,false)) {
        addListCoord(c,i,j-1,listCoord);
      }

      if (this.grid[i][j+1] instanceof Crate && this.isDead(this.grid[i][j+1],i,j+1,false)) {
        addListCoord(c,i,j+1,listCoord);
      }
    }
    return listCoord;
  }
  /**
    * Initialise la liste de coordonnées
    * @param c La caisse étant en [j,i]
    * @param i La coordonnée en Y de la caisse
    * @param j La coordonnée en X de la caisse
  */
  public ArrayList<ArrayList<Integer>> crateChain (Crate c, int i, int j) {
    return crateChain(c,i,j,new ArrayList<>());
  }

  public boolean testCube(ArrayList<ArrayList<Integer>> listChain, int i, int j, int decaleWidth, int decaleHeight) {
    ArrayList<Integer> coordTemp;
    coordTemp = new ArrayList<> ();
    coordTemp.add(i);
    coordTemp.add(j + decaleWidth);
    if (listChain.contains(coordTemp)) {
      coordTemp = new ArrayList<> ();
      coordTemp.add(i + decaleHeight);
      coordTemp.add(j);
      if (listChain.contains(coordTemp)) {
        coordTemp = new ArrayList<> ();
        coordTemp.add(i + decaleHeight);
        coordTemp.add(j + decaleWidth);
        if (listChain.contains(coordTemp)) {
          return true;
        }
      }

    }
    return false;
  }

  public boolean haveCube (ArrayList<ArrayList<Integer>> listChain) {
    for (ArrayList<Integer> coord : listChain) {
      int i = coord.get(0);
      int j = coord.get(1);
      if (testCube(listChain,i,j,-1,-1) || testCube(listChain,i,j,-1,1) || testCube(listChain,i,j,1,-1) ||testCube(listChain,i,j,1,1)) {
        return true;
      }
    }
    return false;
  }

	/**
		* Teste si la partie est terminée, c'est-à-dire si toutes les caisses sont rangées
		* ou si une caisse est bloquée sans être sur un objectif.
		* @return Le résultat du test.
	*/
  public boolean isFinished() {
    boolean test = false;
    if (this.allPlaced()) {
      return true;
    }

    for (Block c : this.listCrate) {
      int i = ((Crate)c).x;
      int j = ((Crate)c).y;

      ArrayList<ArrayList<Integer>> listChain = crateChain(((Crate)c),i,j);

      if (this.isDead(c,i,j,false)) {
        if (this.haveCube(listChain)) {
          System.out.println("CUBE");
          ((Crate)c).setDeadlock(true);
          return true;
        }
      }

      if (listChain.size() == 1 && this.isDead(c,i,j,true) && !((Crate)c).isPlaced()) {
        if (!(this.grid[i-1][j] instanceof Crate && !this.isDead(this.grid[i-1][j],i-1,j,true)) &&
        !(this.grid[i+1][j] instanceof Crate && !this.isDead(this.grid[i+1][j],i+1,j,true)) &&
        !(this.grid[i][j-1] instanceof Crate && !this.isDead(this.grid[i][j-1],i,j-1,true)) &&
        !(this.grid[i][j+1] instanceof Crate && !this.isDead(this.grid[i][j+1],i,j+1,true))) {
          System.out.println("euh...");
          ((Crate)c).setDeadlock(true);
          return true;
        }
      }
    }
    return false;
  }

	/** Initialise l'attribut grid à partir de la lecture du fichier pour obtenir une grille manipulable.
		* @param mapToString
		* Le tableau résultant de la lecture du fichier .xsb correspondant au niveau.
	*/
  public void createGrid(ArrayList<ArrayList<String>> mapToString){
    int[] gridSize = this.getSize(mapToString);
    this.grid = new Block[gridSize[1]][gridSize[0]];
    for (int i=0;i<mapToString.size();i++) {
      for (int j=0;j<mapToString.get(i).size();j++) {
        if (mapToString.get(i).get(j).equals(" ")){
					Block t = new FreeTile(i,j);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("#")) {
          Block t = new Wall(i,j);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("$")) {
          Block t = new Crate(i,j,false);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals(".")) {
          Block t = new Objective(i,j);
					this.listObjective.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("*")) {
          Block t = new Crate(i,j,true);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("@")) {
          Block t = new Player(i,j,false);
					this.player=t;
          this.grid[i][j] = t;
        }else if (mapToString.get(i).get(j).equals("+")) {
          Block t = new Player(i,j,true);
					this.player=t;
          this.grid[i][j] = t;
        }
      }
    }
  }

	/** Calcule les dimensions du niveau.
		* @param mapToString
		* Le tableau résultant de la lecture du fichier .xsb correspondant au niveau.
		* @return Un tableau de 2 entiers de type [largeur, hauteur].
	*/
  public int[] getSize(ArrayList<ArrayList<String>> map) {
    int[] size = new int[2];
    int maxHeight = 0;
    int maxWidth = 0;
    for (ArrayList<String> line : map) {
      maxHeight += 1;
      int widthTemp = 0;
      for (String val : line) {
        widthTemp += 1;
      }
      if (widthTemp>maxWidth) {
        maxWidth = widthTemp;
      }
    }
    size[0] = maxWidth;
    size[1] = maxHeight;
    return size;
  }
}
