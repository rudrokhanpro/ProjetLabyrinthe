package Graph;

import java.util.HashMap;

/**
 * Classe representant un graphe constitue de sommets et aretes
 * 
 * @author Rudro KHAN
 *
 */
public class Graph {
	// Hashmap des sommets accessibles par leur numero
	private HashMap<Integer, Vertex> vertices;

	public Graph() {
		this.vertices = new HashMap<Integer, Vertex>();
	}

	/**
	 * Ajoute un sommet a la liste de sommet
	 * 
	 * @param v Numero du sommet a ajouter
	 */
	public Vertex addVertex(int num) {
		Vertex v = new Vertex(num);
		
		this.vertices.put(num, v);
		
		return v;
	}

	/**
	 * Retourne un sommet du graphe a l'aide de son numero
	 * @param num Numero de sommet
	 * @return Sommet avec le numero correspondant
	 */
	public Vertex get(int num) {
		return this.vertices.get(num);
	}
	
	/**
	 * Retourne l'ordre du graphe, c'est a dire le nombre de sommets
	 * 
	 * @return Nombre de sommets
	 */
	public int getOrder() {
		return this.vertices.size();
	}

	public HashMap<Integer, Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(HashMap<Integer, Vertex> vertices) {
		this.vertices = vertices;
	}
}
