package ia;

/**
	* Classe représentant un noeud d'un graphe.
	*/
public class Node {

	private int x;
	private int y;
	private Node pred;

	/**
		* Constructeur sans argument de la classe.
	*/
	public Node() {
		this.x=-1;
		this.y=-1;
		this.pred = null;
	}

	/**
		* Constructeur avec arguments.
		* Les coordonnées correspondent aux coordonnées d'une grille représentant un niveau de Sokoban.
		* @param x
		* Ordonnée du noeud.
		* @param second
		* Abscisse de la case.
	*/
	public Node(int x, int y) {
		this();
		this.x=x;
		this.y=y;
	}

/**
	* Accesseur de l'ordonnée du noeud.
	* @return La valeur de x.
	*/
	public int getX() {
		return this.x;
	}

/**
	* Accesseur de l'abscisse du noeud.
	* @return La valeur de y.
	*/
	public int getY() {
		return this.y;
	}

/**
	* Accesseur du prédecesseur.
	* @return Le noeud précédant le noeud actuel sur un chemin de graphe..
	*/
	public Node getPred() {
		return this.pred;
	}

/**
	* Mutateur du prédecesseur.
	* @param newPred
	* Le nouveau prédecesseur.
	*/
	public void setPred(Node newPred) {
		this.pred=newPred;
	}

/**
	* Retourne la description du noeud par ses attributs.
	* @return Une chaîne de caractères comportant les coordonnées et le prédecesseur du noeud.
	*/
	public String toString(){
		return "("+this.x+","+this.y+","+this.pred+")";
	}
}
