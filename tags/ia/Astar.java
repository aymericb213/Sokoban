package ia;

import java.util.HashMap;
import java.util.ArrayList;
import sokoban.*;

public class Astar {

	private ArrayList<Node> exploredList;
	private ArrayList<Node> waitingList;
	private Node start;
	private Node goal;
	private String path;
	private Board level;

	public Astar() {
		this.exploredList = new ArrayList<Node>();
		this.waitingList = new ArrayList<Node>();
		this.path="";
		this.start=new Node(0,0);
		this.goal=new Node(0,0);
		this.level=new Board();
	}

	public Node pathSearch() {//passer en String une fois A* fonctionnel
		PathCost evals= new PathCost();
		this.waitingList.add(this.start);
		evals.setExplMap(evals.initMap(this.level.getSize()));
		evals.setFullMap(evals.initMap(this.level.getSize()));
		evals.putFullValue(this.start, evals.manhattan(this.start,this.goal));
		while (!(this.waitingList.isEmpty())) {

			System.out.println("nouveau tour");

			Node current= minimumCost(evals.getFullMap());
			if (current==this.goal) {

				System.out.println("current==goal");

				return buildFullPath();
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
				System.out.println(n.toString() + " = " + evals.getExplMap().get(n));
				System.out.println(evals.getExplMap().toString());
				/* evals.getExplMap().get(n) retourne null peut etre a cause du fait que exploredMap soit un hashmap */
				/* ça retourne null car la clef n n'est pas trouvé */
				/* on peut pas donné un objet en tant que clef apparamant */

				/* solution : peut etre rajouter une clef aux noeuds
											ou utiliser les positions concatenée comme clef du dico exploredMap
				*/
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

	public Node[] neighbours(Node n) {
		Node[] neighboursList= new Node[4];
		neighboursList[0]=new Node(n.getX()-1,n.getY());//up
		neighboursList[1]=new Node(n.getX(),n.getY()+1);//right
		neighboursList[2]=new Node(n.getX()+1,n.getY());//down
		neighboursList[3]=new Node(n.getX(),n.getY()-1);//left
		return neighboursList;
	}

	public Node buildFullPath() {//passer en String une fois A* fonctionnel
		Node n=this.goal;
		while (n!=this.start) {
			System.out.println(n.toString());
			n=n.getPred();
		}
		return n;
	}

	public Node getStart() {
		return this.start;
	}

	public Node getGoal() {
		return this.goal;
	}

	public Board getLevel() {
		return this.level;
	}

	public void setStart(Node start) {
		this.start=start;
	}

	public void setGoal(Node goal){
		this.goal=goal;
	}

	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}
}
