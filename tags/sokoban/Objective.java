package sokoban;

/**
	* Classe fille de Block représentant un objectif.
*/
public class Objective extends Block {

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param second
		* Abscisse de la case.
	*/
  public Objective(int x, int y) {
    super(x,y);
  }

	/**
		* Retourne la représentation de l'objectif.
		* @return Un caractère représentant l'objectif.
	*/
  @Override
  public String toString() {
    return ".";
  }

  public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
