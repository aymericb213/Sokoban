package ia;

import java.util.HashMap;
import java.util.ArrayList;
import sokoban.*;

/**
	* Classe implémentant l'algorithme A*.
*/
public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private ArrayList<Node> neighboursList;
	private Node start;
	private Node goal;
	private String path;
	private Board level;

/**
	* Constructeur de la classe.
*/
	public Astar() {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		this.path="";
		this.start=new Node(0,0);
		this.goal=new Node(0,0);
		this.level=new Board();
	}

/**
	* Algorithme A*.
	* @return
*/
	public ArrayList<Node> pathSearch() {//passer en String une fois A* fonctionnel
		PathCost evals= new PathCost();
		this.waitingList.add(this.start);
		evals.setExplMap(evals.initMap(this.level.getSize()));
		evals.setFullMap(evals.initMap(this.level.getSize()));
		evals.putFullValue(this.start, evals.manhattan(this.start,this.goal));
		int cpt=0;
		while (!(this.waitingList.isEmpty())) {
			cpt++;
			System.out.println("Tour " + cpt);
			Node current= minimumCost(evals.getFullMap());
			System.out.println(current);
			System.out.println(this.goal);
			if (current.equals(this.goal)) {
				System.out.println("current==goal");
				this.goal=current;
				return buildFullPath();
			}
			this.waitingList.remove(current);
			this.exploredList.add(current);
			System.out.println("Voisins de " + current + " : " + neighbours(current).toString());
			for (Node n : neighbours(current)) {
				if (this.exploredList.contains(n)) {
					continue;
				}
				if (!(this.waitingList.contains(n))) {
					this.waitingList.add(n);
				}

				double testG = evals.currentDist(this.start,current) + 1.0;//distance au départ du noeud courant + distance du voisin au noeud courant (toujours 1)
				System.out.println(n + " = " + evals.getExplMap().get(n));
				System.out.println(evals.getExplMap());
				if (testG>=evals.getExplMap().get(n)) {
					continue;
				} else {
					n.setPred(current);
					evals.putExplValue(n, testG);
					evals.putFullValue(n, evals.getExplMap().get(n)+evals.manhattan(n,this.goal));
				}
			}
		}
		return null;
	}

/**
	* Détermine le noeud de coût total minimal dans la liste d'attente de A*.
	* @param fullPathMap
	* La HashMap des coûts de chemins complets utilisée pour le calcul.
	* @return le noeud de moindre coût.
*/
	public Node minimumCost(HashMap<Node,Double> fullPathMap) {
		double min=Double.POSITIVE_INFINITY;
		Node minNode=new Node();
		for (Node n : this.waitingList) {
			if (fullPathMap.get(n) < min) {
				min=fullPathMap.get(n);
				minNode=n;
			}
		}
		return minNode;
	}

/**
	* Crée le tableau des voisins d'un noeud.
	* On ne compte que 4 voisins puisque le Sokoban ne se joue qu'avec 4 directions.
	* @param n
	* Le noeud dont on veut connaître les voisins.
	* @return Le tableau des noeuds voisins.
*/
	public ArrayList<Node> neighbours(Node n) {
		ArrayList<Node> tempList= new ArrayList<Node>();
		ArrayList<Node> neighboursList= new ArrayList<Node>();
		tempList.add(new Node(n.getX()-1,n.getY()));//up
		tempList.add(new Node(n.getX(),n.getY()+1));//right
		tempList.add(new Node(n.getX()+1,n.getY()));//down
		tempList.add(new Node(n.getX(),n.getY()-1));//left
		for (Node m : tempList) {
			if (!(this.level.getGrid()[m.getX()][m.getY()] instanceof Wall) && !( this.level.getGrid()[m.getX()][m.getY()] instanceof Crate)) {
				neighboursList.add(m);
			}
		}
		return neighboursList;
	}

/**
	* Reconstruit le chemin optimal du noeud de départ au noeud d'arrivée d'A*.
	* @return
*/
	public ArrayList<Node> buildFullPath() {//passer en String une fois A* fonctionnel
		Node n=this.goal;
		ArrayList<Node> fullPath = new ArrayList<Node>();
		System.out.println(n.toString());
		while (n!=this.start) {
			fullPath.add(0,n);
			n=n.getPred();
		}
		fullPath.add(0,this.start);
		return fullPath;
	}

/**
	* Accesseur du noeud de départ.
	* @return L'attribut start.
*/
	public Node getStart() {
		return this.start;
	}

/**
	* Accesseur du noeud d'arrivée.
	* @return L'attribut goal.
*/
	public Node getGoal() {
		return this.goal;
	}

/**
	* Accesseur du niveau sur lequel on applique A*..
	* @return L'attribut level.
*/
	public Board getLevel() {
		return this.level;
	}

/**
	* Mutateur du noeud de départ.
	* @param start
	* Le nouveau noeud de départ.
*/
	public void setStart(Node start) {
		this.start=start;
	}

/**
	* Mutateur du noeud d'arrivée.
	* @param start
	* Le nouveau noeud d'arrivée.
*/
	public void setGoal(Node goal){
		this.goal=goal;
	}

/**
	* Mutateur du niveau.
	* @param newBoard
	* Le nouveau niveau.
*/
	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}
}
