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
  private boolean partyFinished;

	/**
		* Constructeur de la classe.
		* Initialise les listes de caisses et d'objectifs.
	*/
  public Board() {
		this.listCrate = new ArrayList<>();
		this.listObjective = new ArrayList<>();
    this.partyFinished = false;
  }

  /**
  * Accesseur de la liste de caisses.
  * @return La valeur de l'attribut listCrate.
  */
  public ArrayList<Block> getCrates() {
    return this.listCrate;
  }

  /**
  * Accesseur de la liste d'objectifs.
  * @return La valeur de l'attribut listObjective.
  */
  public ArrayList<Block> getObjectives(){
    return this.listObjective;
  }

  /**
  * Accesseur des coordonnées du joueur.
  * @return La valeur de l'attribut listObjective.
  */
  public Block getPlayer() {
    return this.player;
  }

  public Block[][] getGrid() {
    return this.grid;
  }

  /**
  * Calcule les dimensions du niveau à partir de l'ArrayList obtenue en lisant un fichier .xsb.
  * Nécessaire à l'initialisation de l'attribut grid.
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

  /**
  * Retourne les dimensions de grid.
  * Méthode sans argument pour faciliter l'utilisation dans le package ia.
  * @return Un tableau contenant la largeur et la hauteur de grid.
  */
  public int[] getSize() {
    int[] tab= new int[2];
    tab[0]=this.grid[0].length;
    tab[1]=this.grid.length;
    return tab;
  }

  /**
    * Geter de la variable qui indique que la partie est finie.
    */
  public boolean getPartyFinished () {
    return this.partyFinished;
  }

  /**
    * Modifie la variable qui indique si la partie est finie.
    */
  public void setPartyFinished (boolean bool) {
    this.partyFinished = bool;
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
		* Teste si une caisse est bloquée, c'est-à-dire qu'elle ne peut pas être déplacée directement..
		* @param c
		* La caisse en [j,i].
		* @param i
		* La coordonnée en Y.
		* @param j
		* La coordonnée en X.
		* @param wallOnly
		* à true il regarde si la caisse est bloquée qu'avec au moins 1 mur, et false avec au moins 0 mur
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
    * @param c
		* La caisse en [j,i]
    * @param i
		* La coordonnée en Y
    * @param j
		* La coordonnée en X
    * @param listCoord
		* La liste des coordonnée dont on va ajouter [j,i]
    * @param deadMode
		* mode de propagation voir <i>crateChain</i>
    * @note Cette fonction sert uniquement pour <i>crateChain</i>.
  */
  private void addListCoord(Crate c, int i, int j, ArrayList<ArrayList<Integer>> listCoord, boolean deadMode) {
    ArrayList<Integer> listTest = new ArrayList<> ();
    listTest.add(j);
    listTest.add(i);
    if (!listCoord.contains(listTest)) {
      crateChain(c,i,j,listCoord,deadMode);
    }
  }

	/**
    * Crée une liste des coordonnées d'une chaîne de caisses placées les unes à côté des autres.
    * @param c
		* La caisse étant en [j,i].
    * @param i
		* La coordonnée en Y de la caisse.
    * @param j
		* La coordonnée en X de la caisse.
    * @param listCoord
		* Liste des coordonnée des caisses.
    * @param deadMode
		* Mode de propagation selon des caisses bloquée à true et false selon n'importe quelle caisses
    * @return La liste des coordonnée de la chaîne
  */
  public ArrayList<ArrayList<Integer>> crateChain (Crate c, int i, int j, ArrayList<ArrayList<Integer>> listCoord, boolean deadMode) {
    ArrayList<Integer> listTemp = new ArrayList<> ();
    listTemp.add(j);
    listTemp.add(i);
    listCoord.add(listTemp);

    if (this.isDead(c,i,j,false) && !((Crate)c).isPlaced()) {
      if (this.grid[i-1][j] instanceof Crate) {
        if (deadMode) {
          if (this.isDead(this.grid[i-1][j],i-1,j,false)) {
            addListCoord(c,i-1,j,listCoord,deadMode);
          }
        } else {
          addListCoord(c,i-1,j,listCoord,deadMode);
        }
      }

      if (this.grid[i+1][j] instanceof Crate) {
        if (deadMode) {
          if (this.isDead(this.grid[i+1][j],i+1,j,false)) {
            addListCoord(c,i+1,j,listCoord,deadMode);
          }
        } else {
            addListCoord(c,i+1,j,listCoord,deadMode);
        }
      }

      if (this.grid[i][j-1] instanceof Crate) {
        if (deadMode) {
          if (this.isDead(this.grid[i][j-1],i,j-1,false)) {
            addListCoord(c,i,j-1,listCoord,deadMode);
          }
        } else {
          addListCoord(c,i,j-1,listCoord,deadMode);
        }
      }

      if (this.grid[i][j+1] instanceof Crate) {
        if (deadMode) {
          if (this.isDead(this.grid[i][j+1],i,j+1,false)) {
            addListCoord(c,i,j+1,listCoord,deadMode);
          }
        } else {
          addListCoord(c,i,j+1,listCoord,deadMode);
        }
      }
    }
    return listCoord;
  }

	/**
    * Teste si dans une liste de coordonnées de caisses, [j,i] est une partie d'un carré.
    * @param listChain
		* Liste des coordonnées des caisses d'une chaine à tester.
    * @param i
		* La coordonnée en Y de la caisse.
    * @param j
		* La coordonnée en X de la caisse.
    * @param decaleWidth
		* décalage en Y pour avoir le coin.
    * @param decaleHeight
		* décalage en X pour avoir le coin.
    * @return Le résultat du test.
  */
  public boolean testCube(ArrayList<ArrayList<Integer>> listChain, int i, int j, int decaleWidth, int decaleHeight) {
    ArrayList<Integer> coordTemp;
    coordTemp = new ArrayList<> ();
    coordTemp.add(i);
    coordTemp.add(j + decaleWidth);
    if (listChain.contains(coordTemp) || this.grid[coordTemp.get(1)][coordTemp.get(0)] instanceof Wall) {
      coordTemp = new ArrayList<> ();
      coordTemp.add(i + decaleHeight);
      coordTemp.add(j);
      if (listChain.contains(coordTemp) || this.grid[coordTemp.get(1)][coordTemp.get(0)] instanceof Wall) {
        coordTemp = new ArrayList<> ();
        coordTemp.add(i + decaleHeight);
        coordTemp.add(j + decaleWidth);
        if (listChain.contains(coordTemp) || this.grid[coordTemp.get(1)][coordTemp.get(0)] instanceof Wall) {
          return true;
        }
      }

    }
    return false;
  }

	/**
    * Teste si un carré est formé par des caisses de la liste.
    * @param listChain
		* Liste des coordonnées des caisses de la chaine.
    * @return Le résultat du test.
  */
  public boolean hasSquare (ArrayList<ArrayList<Integer>> listChain) {
    for (ArrayList<Integer> coord : listChain) {
      int i = coord.get(0);
      int j = coord.get(1);
      if (!((Crate)this.grid[j][i]).isPlaced() && (testCube(listChain,i,j,-1,-1) || testCube(listChain,i,j,-1,1) || testCube(listChain,i,j,1,-1) ||testCube(listChain,i,j,1,1))) {
        return true;
      }
    }
    return false;
  }


	/**
		* Teste si une caisse est bloquée par une autre le long d'un mur.
		* Exemple : <p>$$<br>##</p>
		* @param coord
		* Coordonnées de la caisse à tester.
		* @return Le résultat du test
	*/
  public boolean hasDeadWall (ArrayList<Integer> coord) {
      int i = coord.get(1);
      int j = coord.get(0);

      if (this.grid[i-1][j] instanceof Crate && this.isDead(this.grid[i-1][j],i-1,j,true)) {
        if ((this.grid[i-1][j-1] instanceof Wall && this.grid[i][j-1] instanceof Wall) ||
        (this.grid[i-1][j+1] instanceof Wall && this.grid[i][j+1] instanceof Wall)) {
          return true;
        }
      }

      if (this.grid[i][j-1] instanceof Crate && this.isDead(this.grid[i][j-1],i,j-1,true)) {
        if ((this.grid[i+1][j-1] instanceof Wall && this.grid[i+1][j] instanceof Wall) ||
        (this.grid[i-1][j-1] instanceof Wall && this.grid[i-1][j] instanceof Wall)) {
          return true;
        }
      }
      return false;
    }

	/**
		* Détermine la fin de partie, c'est-à-dire si l'état du plateau ne permet aucun mouvement ultérieur,
		* ou si toutes les caisses sont rangées.
		* @return Le résultat du test.
	*/
  public boolean isFinished() {
    boolean test = true;
    if (this.allPlaced()) {
      return true;
    }

    for (Block c : this.listCrate) {
      int i = ((Crate)c).x;
      int j = ((Crate)c).y;

      ArrayList<ArrayList<Integer>> listChain = crateChain(((Crate)c),i,j,new ArrayList<> (), false);

      if (this.isDead(c,i,j,false) && !((Crate)c).isPlaced()) {
        if (this.hasSquare(listChain)) {
          ((Crate)c).setDeadlock(true);
          return true;
        } else {
          test = true;
          for (ArrayList<Integer> coord : listChain) {
            if (!this.isDead(c,coord.get(1),coord.get(0),false)) {
              test = false;
            }
            if (this.hasDeadWall(coord)) {
              ((Crate)c).setDeadlock(true);
              return true;
            }
          }
          if (test) {
            ((Crate)c).setDeadlock(true);
            return true;
          }
        }
      }
    }
    return false;
  }

	/** Initialise l'attribut grid à partir de la lecture du fichier pour obtenir une grille manipulable.
		* @param mapToString
		* Le tableau résultant de la lecture du fichier .xsb correspondant au niveau.
	*/
	public void createGrid(ArrayList<String> mapToString) {
    this.listCrate = new ArrayList<>();
    this.listObjective = new ArrayList<>();
    int[] gridSize = this.getSize(mapToString);
    this.grid = new Block[gridSize[0]][gridSize[1]];
    for (int i = 0; i < mapToString.size(); i++) {
      String[] s = mapToString.get(i).split("");
      int j = 0;
      for (String ch: s) {
        if (ch.equals(" ")) {
          Block t = new FreeTile(i,j);
          this.grid[i][j] = t;
        } else if (ch.equals("#")) {
          Block t = new Wall(i,j);
          this.grid[i][j] = t;
        } else if (ch.equals("$")) {
          Block t = new Crate(i,j,false);
          this.listCrate.add(t);
          this.grid[i][j] = t;
        } else if (ch.equals(".")) {
          Block t = new Objective(i,j);
          this.listObjective.add(t);
          this.grid[i][j] = t;
        } else if (ch.equals("*")) {
          Block t = new Crate(i,j,true);
          this.listCrate.add(t);
          this.grid[i][j] = t;
        } else if (ch.equals("@")) {
          Block t = new Player(i,j,false);
          this.player = t;
          this.grid[i][j] = t;
        } else if (ch.equals("+")) {
          Block t = new Player(i,j,true);
          this.player = t;
          this.grid[i][j] = t;
        }
        j++;
      }
    }
  }

  public ArrayList<String> createArrayList () {
    ArrayList<String> res = new ArrayList<> ();
    for (Block[] line : this.grid) {
      String ch = "";
      for (Block c : line) {
        if (c != null) {
          ch += c.toString();
        }
      }
      res.add(ch);
    }
    return res;
  }

}
