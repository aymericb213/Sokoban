package ia;

public class Push {

	private int x;
	private int y;
	private String direction;

	public Push() {
		this.x=-1;
		this.y=-1;
		this.direction = "";
	}

	public Push(int x, int y, String direction) {
		this();
		this.x=x;
		this.y=y;
		this.direction=direction;
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
	public String getDir() {
		return this.direction;
	}

/**
	* Mutateur de la direction.
	* @param newDir
	* La nouvelle direction.
	*/
	public void setDir(String newDir) {
		this.direction=newDir;
	}

/**
	* Retourne la description du coup.
	* @return Une chaîne de caractères comportant les informations sur le coup..
	*/
	public String toString(){
		return "("+this.x+","+this.y+","+this.direction+")";
	}
}
