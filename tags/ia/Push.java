package ia;

public class Push {

	public enum Direction {
		UP (-1,0),
		LEFT (0,-1),
		DOWN (1,0),
		RIGHT (0,1);

		private final int x;
		private final int y;

		Direction(int x, int y) {
			this.x=x;
			this.y=y;
		}
	}

	private int x;
	private int y;
	private Direction dir;

	public Push() {
		this.x=-1;
		this.y=-1;
		this.dir=dir.UP;
	}

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
	* @return Une chaîne de caractères comportant les informations sur le coup..
	*/
	public String toString(){
		return "("+this.x+","+this.y+","+this.dir+")";
	}
}
