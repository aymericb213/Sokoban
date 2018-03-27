package sokoban;

/**
	* Classe fille de Block représentant un objectif.
*/
public class Objective extends Block {

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
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

	/**
		* Accesseur de l'ordonnée de l'objectif.
		* @return La valeur de x.
		*/
		public int getX() {
			return this.x;
		}

	/**
		* Accesseur de l'abscisse de l'objectif.
		* @return La valeur de y.
		*/
		public int getY() {
			return this.y;
		}

}
