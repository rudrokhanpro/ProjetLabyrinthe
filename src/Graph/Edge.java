package Graph;

/**
 * Classe representant une arete
 */
public class Edge {
	private int from; // numero du sommet de depart
	private int to; // numero du sommet d'arrivee
	private double weight; // poids de l'arete

	public Edge(int from, int to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
} // Edge