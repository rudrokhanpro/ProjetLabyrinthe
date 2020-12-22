package Graph;

import Maze.Map;

public class GraphUtils {
	/**
	 * Retourne un graphe a partir d'une carte de labyrithe.
	 * 
	 * Proprietes:
	 * - 4 Voisins possible dans l'ordre suivant: HAUT, DROITE, BAS, GAUCHE
	 * - Le mur ne sont ignores
	 * - La distance entre chaque voisin est de 1. 
	 * 
	 * @param map
	 * @return
	 */
	public static Graph fromMap(Map map) {
		if (map.getMap().length == 0)
			return null;

		Graph g = new Graph();

		int nlines = map.getNlines();
		int ncols = map.getNcols();

		int source, dest;

		for (int i = 0; i < nlines; i++) {
			for (int j = 0; j < ncols; j++) {
				// numero de sommet
				source = (i * ncols) + j;

				// ignorer si c'est un mur
				if (map.isWall(source))
					continue;

				// Ajout d'aretes vers les voisins
				// situes en HAUT, a DROITE, en BAS et puis a GAUCHE.
				// Si le voisin est un mur alors ne pas creer d'arete
				Vertex v = g.addVertex(source);

				if (i > 0) { // NORD
					dest = (i - 1) * ncols + j;

					if (!map.isWall(dest))
						v.getAdjacencyList().add(new Edge(source, dest, 1));
				}

				if (j + 1 < ncols) { // EST
					dest = i * ncols + (j + 1);

					if (!map.isWall(dest))
						v.getAdjacencyList().add(new Edge(source, dest, 1));
				}

				if (i + 1 < nlines) { // SUD
					dest = (i + 1) * ncols + j;

					if (!map.isWall(dest))
						v.getAdjacencyList().add(new Edge(source, dest, 1));
				}

				if (j > 0) { // OUEST
					dest = i * ncols + (j - 1);

					if (!map.isWall(dest))
						v.getAdjacencyList().add(new Edge(source, dest, 1));
				}

//				// DEBUG
//				printAdjacency(g, source);
			}
		}

		return g;
	}

	/**
	 * FONCTION UTILITAIRES
	 */

	/**
	 * Affiche la liste d'adjacence d'un sommet dans un graphe
	 * @param g Graphe
	 * @param n Sommet a etudier
	 */
	public static void printAdjacency(Graph g, int n) {
		Vertex v = g.get(n);

		// en cas d'echec
		if (v == null) {
			System.out.println("Vertex = null");

			return;
		}

		System.out.println("Vertex = " + n);

		for (Edge e : g.get(n).getAdjacencyList()) {
			System.out.println("\t" + e.getFrom() + " ==[" + e.getWeight() + "]==> " + e.getTo());
		}
		System.out.println();
	}
}
