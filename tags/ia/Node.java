package ia;

public class Node {

	private int x;
	private int y;
	private Node pred;

	public Node(int x, int y) {
		this.x=x;
		this.y=y;
		this.pred = null;
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
}
