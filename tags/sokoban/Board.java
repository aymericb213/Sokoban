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
  public boolean isDead(Block c, int i, int j) {
    if ((this.grid[i-1][j] instanceof Crate || this.grid[i+1][j] instanceof Crate) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
      return true;
    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Crate || this.grid[i][j+1] instanceof Crate)) {
      return true;
    } else if ((this.grid[i-1][j] instanceof Wall || this.grid[i+1][j] instanceof Wall) &&
    (this.grid[i][j-1] instanceof Wall || this.grid[i][j+1] instanceof Wall)) {
      return true;
    } else if ((this.grid[i-1][j] instanceof Crate || this.grid[i+1][j] instanceof Crate) &&
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

    if (this.isDead(c,i,j) && !((Crate)c).isPlaced()) {
      if (this.grid[i-1][j] instanceof Crate) {
        addListCoord(c,i-1,j,listCoord);
      }

      if (this.grid[i+1][j] instanceof Crate) {
        addListCoord(c,i+1,j,listCoord);
      }

      if (this.grid[i][j-1] instanceof Crate) {
        addListCoord(c,i,j-1,listCoord);
      }

      if (this.grid[i][j+1] instanceof Crate) {
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

      if (listChain.size() != 1) {
        boolean testDeadChain = true;
        for (ArrayList<Integer> coord : listChain) {
          if (!this.isDead(this.grid[coord.get(0)][coord.get(1)],coord.get(1),coord.get(0))) {
            testDeadChain = false;
          }
        }
        if (testDeadChain) {
          ((Crate)c).setDeadlock(true);
          return true;
        }
      }

      if (listChain.size() == 1 && this.isDead(c,i,j) && !((Crate)c).isPlaced()) {
        if (!(this.grid[i-1][j] instanceof Crate && !this.isDead(this.grid[i-1][j],i-1,j)) &&
        !(this.grid[i+1][j] instanceof Crate && !this.isDead(this.grid[i+1][j],i+1,j)) &&
        !(this.grid[i][j-1] instanceof Crate && !this.isDead(this.grid[i][j-1],i,j-1)) &&
        !(this.grid[i][j+1] instanceof Crate && !this.isDead(this.grid[i][j+1],i,j+1))) {

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
  public void createGrid(ArrayList<String> mapToString){
		int[] gridSize = this.getSize(mapToString);
    this.grid = new Block[gridSize[0]][gridSize[1]];
    for (int i=0;i<mapToString.size();i++) {
			String[] s = mapToString.get(i).split("");
			int j=0;
      for (String ch: s) {
        if (ch.equals(" ")){
					Block t = new FreeTile(i,j);
          this.grid[i][j] = t;
        }else if (ch.equals("#")) {
          Block t = new Wall(i,j);
          this.grid[i][j] = t;
        }else if (ch.equals("$")) {
          Block t = new Crate(i,j,false);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (ch.equals(".")) {
          Block t = new Objective(i,j);
					this.listObjective.add(t);
          this.grid[i][j] = t;
        }else if (ch.equals("*")) {
          Block t = new Crate(i,j,true);
					this.listCrate.add(t);
          this.grid[i][j] = t;
        }else if (ch.equals("@")) {
          Block t = new Player(i,j,false);
					this.player=t;
          this.grid[i][j] = t;
        }else if (ch.equals("+")) {
          Block t = new Player(i,j,true);
					this.player=t;
          this.grid[i][j] = t;
        }
				j++;
      }
    }
  }

	/** Calcule les dimensions du niveau.
		* @param mapToString
		* Le tableau résultant de la lecture du fichier .xsb correspondant au niveau.
		* @return Un tableau de 2 entiers de type [largeur, hauteur].
	*/
  public int[] getSize(ArrayList<String> map) {
		int[] size = new int[2];
    int maxHeight = 0;
    int maxWidth = 0;
    for (String line : map) {
			String[] s = line.split("");
      maxHeight += 1;
      int widthTemp = 0;
      for (String ch : s) {
        widthTemp += 1;
      }
      if (widthTemp>maxWidth) {
        maxWidth = widthTemp;
      }
    }
    size[0] = maxHeight;
    size[1] = maxWidth;
    return size;
  }
}
