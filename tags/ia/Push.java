package ia;

import java.util.ArrayList;

/**
	* Modélisation de la poussée d'une caisse.
	*/
public class Push {

/**
	* Enum représentant les quatre directions de poussée possibles.
 	*/
	public enum Direction {
		UP (-1,0),
		LEFT (0,-1),
		DOWN (1,0),
		RIGHT (0,1);

		private final int x;
		private final int y;

		/**
			* Constructeur de l'enum.
			*/
		Direction(int x, int y) {
			this.x=x;
			this.y=y;
		}

		/**
			* Accesseur des attributs de l'enum.
			* @return Les coordonnées de l'instance de l'enum.
			*/
		public ArrayList<Integer> getCoords() {
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(this.x);
			l.add(this.y);
			return l;
		}
	}

	private int x;
	private int y;
	private Direction dir;

	/**
		* Constructeur sans argument de la classe.
		*/
	public Push() {
		this.x=-1;
		this.y=-1;
		this.dir=dir.UP;
	}

	/**
		* Constructeur avec arguments de la classe.
		* @param x Ordonnée de la position du joueur lors de la poussée.
		* @param y Abscisse de la position du joueur lors de la poussée.
		*/
	public Push(int x, int y) {
		this.x=x;
		this.y=y;
	}

/**
	* Accesseur de l'ordonnée du coup.
	* @return La valeur de x.
	*/
	public int getX() {
		return this.x;
	}

/**
	* Accesseur de l'abscisse du coup.
	* @return La valeur de y.
	*/
	public int getY() {
		return this.y;
	}

/**
	* Accesseur de la direction.
	* @return La direction vers laquelle se produit la poussée.
	*/
	public Direction getDir() {
		return this.dir;
	}

/**
	* Mutateur de la direction.
	* @param newDir
	* La nouvelle direction.
	*/
	public void setDir(int x, int y) {
		if(x==-1 && y==0) {
			this.dir=dir.UP;
		} else if (x==0 && y==-1) {
			this.dir=dir.LEFT;
		} else if (x==1 && y==0) {
			this.dir=dir.DOWN;
		} else {
			this.dir=dir.RIGHT;
		}
	}

/**
	* Retourne la description du coup.
	* @return Une chaîne de caractères comportant les informations sur le coup.
	*/
	public String toString(){
		return "("+this.x+","+this.y+","+this.dir+")";
	}
}
