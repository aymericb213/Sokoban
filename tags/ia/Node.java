package ia;

public class Node {

	private int x;
	private int y;
	private Node pred;

	public Node() {
		this.x=-1;
		this.y=-1;
		this.pred = null;
	}
	
	public Node(int x, int y) {
		this();
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Node getPred() {
		return this.pred;
	}

	public void setPred(Node newPred) {
		this.pred=newPred;
	}

	public String toString(){
		return "("+this.x+","+this.y+","+this.pred+")";
	}
}
