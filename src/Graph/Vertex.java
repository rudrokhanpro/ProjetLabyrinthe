package Graph;

import java.util.ArrayList;

/**
 * Classe represantant un sommet
 */
public class Vertex {
	private int num;
	private ArrayList<Edge> adjacencyList; // liste d'arete partant de ce sommet

	public Vertex(int num) {
		super();
		this.num = num;
		this.adjacencyList = new ArrayList<Edge>();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public ArrayList<Edge> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(ArrayList<Edge> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

} // Vertex