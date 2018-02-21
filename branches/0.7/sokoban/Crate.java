package sokoban;

/**
	* Classe fille de Block représentant une caisse.
*/
public class Crate extends Block {

  protected boolean deadLock;
  protected boolean placed;

	/**
		* Constructeur de la classe.
		* Initialise deadLock. Au début d'un niveau, toutes les caisses sont déplaçables, deadLock est donc initialisé à False.
		* @param x
		* Ordonnée de la case.
		* @param second
		* Abscisse de la case.
		* @param placed
		* Booléen indiquant si la caisse se trouve sur un objectif.
	*/
  public Crate(int x, int y,boolean placed) {
    super(x,y);
    this.deadLock=false;
    this.placed=placed;
  }

	/**
		* Accesseur permettant de tester si la caisse se trouve sur un objectif.
		* @return La valeur de l'attribut placed.
	*/
  public boolean isPlaced() {
    return this.placed;
  }

	/**
		* Accesseur permettant de tester si la caisse est bloquée.
		* @return La valeur de l'attribut deadLock.
	*/
	public boolean isBlocked() {
		return this.deadLock;
	}

	/**
		* Mutateur de l'attribut placed.
		* @param newState
		* La nouvelle valeur de placed.
	*/
	public void setPlaced(boolean newState) {
		this.placed=newState;
	}

	/**
		* Mutateur de l'attribut deadLock.
		* @param newState
		* La nouvelle valeur de deadLock.
	*/
	public void setDeadlock(boolean newState) {
		this.deadLock=newState;
	}

	/**
		* Accesseur de l'ordonnée de la caisse.
		* @return La valeur de x.
		*/
		public int getX() {
			return this.x;
		}

	/**
		* Accesseur de l'abscisse de la caisse.
		* @return La valeur de y.
		*/
		public int getY() {
			return this.y;
		}

	/**
		* Retourne la représentation de la caisse.
		* @return Un caractère représentant la caisse et son état (placée sur un objectif ou non).
	*/
  @Override
  public String toString() {
    if (this.placed) {
      return "*";
    } else {
      return "$";
    }
  }
}
