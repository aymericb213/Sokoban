package ia;

import java.util.HashMap;
import java.lang.Math;
import sokoban.*;

/**
 	* Classe de calcul de coût d'un chemin sur un niveau de Sokoban.
*/
public class PathCost {

	private HashMap<Node,Double> exploredMap;
	private HashMap<Node,Double> fullPathMap;

	/**
		* Constructeur de la classe.
	*/
	public PathCost() {
	  this.exploredMap = new HashMap<Node,Double>();
	  this.fullPathMap = new HashMap<Node,Double>();
	}

/**
	* Calcule la distance au départ d'un noeud n.
	* @param start
	* Le noeud de départ.
	* @param n
	* Le noeud dont on veut connaître la distance au départ.
	* @return La distance au départ.
*/
	public double currentDist(Node start, Node n) {
		/* j'ai rajouté || n==null */
		if (n==start || n==null) {
			return 0.;
		} else {
			System.out.println("start : "+ start +" getpred : " + n);
			return 1. + currentDist(start, n.getPred());
		}
	}

/**
	* Calcule la distance de Manhattan entre le noeud n et l'arrivée.
	* Fonction heuristique pour A*.
	* @param n
	* Le noeud dont on veut connaître la distance de Manhattan.
	* @param goal
	* Le noeud d'arrivée.
	* @return La distance de Manhattan.
*/
	public double manhattan(Node n, Node goal) {
		return Math.abs(goal.getX() - n.getX()) + Math.abs(goal.getY() - n.getY());
	}

/**
	* Crée une HashMap avec un nombre de clés défini selon les dimensions d'une grille ayant +∞ en valeur initiale.
	* @param size
	* Tableau des dimensions d'une grille.
	* @return La HashMap initialisée.
*/
	public HashMap<Node,Double> initMap(int[] size) {
		int nbKeys=size[0]*size[1];
		double inf=Double.POSITIVE_INFINITY;
		HashMap<Node,Double> map=new HashMap<>(nbKeys);
		for (int i=0; i<size[0]; i++) {
			for (int j=0; j<size[1]; j++) {
				map.put(new Node(j,i), inf);
			}
		}
		return map;
	}

/**
	* Accesseur de la HashMap des distances au départ.
	* @return L'attribut exploredMap.
*/
	public HashMap<Node,Double> getExplMap() {
		return this.exploredMap;
	}

/**
	* Mutateur d'une valeur de exploredMap.
	* @param key
	* La clé à laquelle on va affecter une valeur.
	* @param newval
	* La valeur à affecter.
*/
	public void putExplValue(Node key, double newval) {
		this.exploredMap.put(key,newval);
	}

/**
	* Mutateur de exploredMap.
	* @param m
	* La HashMap dont les couples seront affectés à exploredMap.
*/
	public void setExplMap(HashMap<Node,Double> m) {
		this.exploredMap=new HashMap<Node,Double>(m);
	}

/**
	* Accesseur de la HashMap des coûts de chemins complets.
	* @return L'attribut exploredMap.
*/
	public HashMap<Node,Double> getFullMap() {
		return this.fullPathMap;
	}

/**
	* Mutateur d'une valeur de exploredMap.
	* @param key
	* La clé à laquelle on va affecter une valeur.
	* @param newval
	* La valeur à affecter.
*/
	public void putFullValue(Node key, double newval) {
		this.fullPathMap.put(key,newval);
	}

/**
	* Mutateur de exploredMap.
	* @param m
	* La HashMap dont les couples seront affectés à exploredMap.
*/
	public void setFullMap(HashMap<Node,Double> m) {
		this.fullPathMap=new HashMap<Node,Double>(m);
	}
}
