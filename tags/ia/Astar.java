package ia;

import java.util.HashMap;
import java.util.ArrayList;
import sokoban.*;

/**
	* Classe implémentant l'algorithme A* pour le déplacement automatique du joueur.
*/
public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private Node start;
	private Node goal;
	private Board level;
	private ArrayList<Node> path;

/**
	* Constructeur de la classe.
*/
	public Astar() {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		this.path=new ArrayList<Node>();
		this.start=new Node(0,0);
		this.goal=new Node(0,0);
		this.level=new Board();
	}

/**
	* Algorithme A*.
*/
	public void pathSearch() {
		PathCost evals= new PathCost();
		this.waitingList.add(this.start);
		evals.setExplMap(evals.initMap(this.level.getSize()));
		evals.setFullMap(evals.initMap(this.level.getSize()));
		evals.putFullValue(this.start, evals.manhattan(this.start,this.goal));
		while (!(this.waitingList.isEmpty())) {
			Node current= minimumCost(evals.getFullMap());
			if (current.equals(this.goal)) {
				this.goal=current;
				buildFullPath();
				break;
			}
			this.waitingList.remove(current);
			this.exploredList.add(current);
			for (Node n : neighbours(current)) {
				if (this.exploredList.contains(n)) {
					continue;
				}
				if (!(this.waitingList.contains(n))) {
					this.waitingList.add(n);
				}
				double testG = evals.currentDist(this.start,current) + 1.0;//distance au départ du noeud courant + distance du voisin au noeud courant (toujours 1)
				if (testG>=evals.getExplMap().get(n)) {
					continue;
				} else {
					n.setPred(current);
					evals.putExplValue(n, testG);
					evals.putFullValue(n, evals.getExplMap().get(n)+evals.manhattan(n,this.goal));
				}
			}
		}
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
*/
	public void buildFullPath() {
		Node n=this.goal;
		while (n!=this.start) {
			this.path.add(0,n);
			n=n.getPred();
		}
		this.path.add(0,this.start);
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
	* Accesseur du niveau sur lequel on applique A*.
	* @return L'attribut level.
*/
	public Board getLevel() {
		return this.level;
	}

	/**
		* Accesseur du chemin trouvé par A*.
		* @return L'attribut path.
	*/
	public ArrayList<Node> getPath() {
		return this.path;
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

	/**
		* Représentation de l'itinéraire trouvé par A*.
		* @return La séquence de directions entre le départ et l'arrivée de l'instance.
		*/
	public String toString() {
		String res="";
		for (int i=0; i+1<this.path.size(); i++) {
			String ch="";
			int diffX=this.path.get(i+1).getX()-this.path.get(i).getX();
			int diffY=this.path.get(i+1).getY()-this.path.get(i).getY();
			if (diffX==-1) {
				ch="u";
			} else if (diffX==1) {
				ch="d";
			} else if (diffY==-1) {
				ch="l";
			} else if (diffY==1) {
				ch="r";
			}
			res+=ch;
		}
		return res;
	}
}
