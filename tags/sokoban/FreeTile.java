package sokoban;

/**
	* Classe fille de Block représentant un mur.
*/
public class FreeTile extends Block {

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param second
		* Abscisse de la case.
	*/
  public FreeTile(int x, int y) {
    super(x,y);
  }

	/**
		* Retourne la représentation de la case vide.
		* @return Un caractère représentant la case vide.
	*/
  @Override
  public String toString() {
    return " ";
  }
}
