package ia;

import java.util.Map;
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

	public Node PathSearch() {
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

				return this.goal;
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


				/* gscore de current + currentDist(current,neighbours) ???? */

				/* this.start se transforme en current ??? */

				/* 1.0 = current,neighbours ??? dans ce cas currentDist(this.start,n) = gscore[current] ??? */
				/* j'ai changé this.start en current */
				double testG = evals.currentDist(current,n) + 1.0;
				System.out.println(evals.getExplMap().get(n));

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

	public Node minimumCost(HashMap<Node,Double> m) {
		double min=Double.POSITIVE_INFINITY;
		Node n=new Node();
		for (Map.Entry<Node,Double> x : m.entrySet()) {
			if (x.getValue()<min) {
				min=x.getValue();
				n=x.getKey();
			}
		}
		return n;
	}

	public Node[] neighbours(Node n) {
		Node[] neighboursList= new Node[4];
		neighboursList[0]=new Node(n.getX()-1,n.getY());//up
		neighboursList[1]=new Node(n.getX(),n.getY()+1);//right
		neighboursList[2]=new Node(n.getX()+1,n.getY());//down
		neighboursList[3]=new Node(n.getX(),n.getY()-1);//left
		return neighboursList;
	}

	public Node getStart() {
		return this.start;
	}

	public Node getGoal() {
		return this.goal;
	}

	public void setStart(Node start) {
		this.start=start;
	}

	public void setGoal(Node goal){
		this.goal=goal;
	}

	public Board getLevel() {
		return this.level;
	}

	public void setLevel(Board newBoard) {
		this.level=newBoard;
	}
}
