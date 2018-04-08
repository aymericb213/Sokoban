package sokoban;

/**
	* Classe abstraite représentant un objet du plateau d'un niveau de Sokoban.
*/
public abstract class Block {

  protected int x;
  protected int y;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Block(int x, int y) {
    this.x = x;
    this.y = y;
  }

	/**
		* Retourne la représentation de la case.
		* @return Un caractère représentant le type de la case.
	*/
  public abstract String toString();
}
