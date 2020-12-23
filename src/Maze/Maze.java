package Maze;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Graph.Edge;
import Graph.Graph;
import Graph.GraphUtils;

public class Maze {
	// Labyrinthe
	private Graph graph;
	private int start, end;
	private int current; // cellule actuelle
	private Map map;
	private int nlines, ncols;
	private LinkedList<Integer> path;

	// Affichage
	private MazeFrame frame; // fenetre d'affichage
	private MazePanel panel; // composant affichant le labyrinthe
	private final int DELAY = 2;

	public Maze(Map map) {
		this.map = map;
		this.start = map.getStart();
		this.end = map.getEnd();
		this.current = Map.UNKNOWN;
		this.nlines = map.getNlines();
		this.ncols = map.getNcols();

		// chemin recent
		this.path = new LinkedList<Integer>();

		// creation d'un graphe a partir d'une carte
		this.graph = GraphUtils.fromMap(map);

		// initialisation de la fenetre d'affichage
		this.frame = new MazeFrame();
		this.panel = new MazePanel(map);
	}

	/*
	 * METHODES RELATIVES A LA RESOLUTION DU LABYRINTHE
	 */
	public void solve() {
//		dijkstra();
		aStar();
	}

	/**
	 * Recherche du chemin le plus court a l'aide de l'algorithme de Dijkstra
	 */
	private void dijkstra() {
		HashMap<Integer, Double> cost = new HashMap<Integer, Double>();
		HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
		HashMap<Integer, Integer> pred = new HashMap<Integer, Integer>();

		// initialisation
		for (int u : graph.getVertices().keySet()) {
			visited.put(u, false); // non visite
			cost.put(u, Double.POSITIVE_INFINITY); // infini
			pred.put(u, -1); // inconnu
		}

		// file prioritaire, retirant le sommet au cout minimal
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				if (cost.get(a) < cost.get(b))
					return -1;
				else if (cost.get(a) == cost.get(b))
					return 0;
				else
					return 1;
			}
		});

		// initialisation
		cost.put(this.start, 0.0);
		q.add(this.start);

		// tant qu'il y a des sommet a visiter
		while (!q.isEmpty()) {
			// extraire de la file le sommet non visite et au cout minimal
			int u = q.poll();
			while (visited.get(u)) {
				if (!q.isEmpty())
					u = q.poll();
				// si la file est vide alors sortir
				else
					break;
			}

			// si on est arrive a la fin alors sortir
			if (u == this.end)
				break;

			// mise a jour de l'affichage
			this.panel.updateCurrent(u);
			delay(DELAY);

			// pour tout sommet adjacent
			for (Edge e : graph.get(u).getAdjacencyList()) {
				int v = e.getTo();

				// relachement
				if (!visited.get(v)) {
					if (cost.get(u) + e.getWeight() < cost.get(v)) {
						cost.put(v, cost.get(u) + e.getWeight());
						pred.put(v, u);
					}
					q.add(v);
				} // if
			} // for

			// marquer ce sommet comme visite
			visited.put(u, true);
		}

		// reconstitution du chemin, de la fin vers le debut
		LinkedList<Integer> path = new LinkedList<Integer>();
		int w = pred.get(end); // point de depart

		while (w != Map.UNKNOWN) {
			path.add(w);

			w = pred.get(w);
		}

		// si chemin vide, alors pas de solution
		if (path.isEmpty())
			System.out.println("PAS DE SOLUTION");
		else {
			System.out.println("Chemin (de la fin vers le debut au debut):");
			System.out.println(path);

			this.panel.updatePath(path); // mise a jour de l'affichage
		}

		this.panel.updateCurrent(Map.UNKNOWN);
	}

	/**
	 * Recherche du chemin le plus court optimise a l'aide de l'algorithme A* (A
	 * Etoile) Heuristique: Distance par rapport a la sortie
	 */
	private void aStar() {
		HashMap<Integer, Double> cost = new HashMap<Integer, Double>();
		HashMap<Integer, Double> heuristic = new HashMap<Integer, Double>();
		HashMap<Integer, Integer> pred = new HashMap<Integer, Integer>();
		LinkedList<Integer> potentialPath = new LinkedList<Integer>();

		// initialisation
		for (int u : graph.getVertices().keySet()) {
			cost.put(u, Double.POSITIVE_INFINITY);
			heuristic.put(u, GraphUtils.getDistance(u, this.end, this.map)); // infini
			pred.put(u, -1); // inconnu
		}

		// file prioritaire, retirant le sommet au cout minimal
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(new Comparator<Integer>() {

			/**
			 * Retourne le sommet possedant le cout total minimal, c'est a dire
			 */
			@Override
			public int compare(Integer a, Integer b) {
				double fCostA = cost.get(a) + heuristic.get(a);
				double fCostB = cost.get(b) + heuristic.get(b);

				return (int) (fCostA - fCostB);
			}
		});

		// initialisation
		cost.put(this.start, 0.0);
		q.add(this.start);

		boolean qContains;

		// tant qu'il y a des sommet a visiter
		while (!q.isEmpty()) {
			// extraire de la file le sommet non visite et au cout minimal
			int u = q.poll();

			// ajout du sommet au potentiel chemin
			potentialPath.add(u);

			// si on est arrive a la fin alors sortir
			if (u == end)
				break;

			// mise a jour de l'affichage
			this.panel.updateCurrent(pred.get(u));
			delay(DELAY);

			// pour tout sommet adjacent
			for (Edge e : this.graph.get(u).getAdjacencyList()) {
				int v = e.getTo();

				// si ce sommet ne fait pas partie du chemin
				if (potentialPath.contains(v))
					continue;

				qContains = q.contains(v);

				// si ce sommet n'est pas encore dans la file
				// ou une distance plus courte a ete trouvee
				if (!qContains || cost.get(u) + e.getWeight() < cost.get(v)) {
					cost.put(v, cost.get(u) + e.getWeight());
					pred.put(v, u);
				} // if

				// si pas encore dans la file
				if (!qContains)
					q.add(v);
			} // for

		}

		// reconstitution du chemin, de la fin vers le debut
		LinkedList<Integer> path = new LinkedList<Integer>();
		int w = pred.get(end); // point de depart

		while (w != Map.UNKNOWN) {
			path.add(w);

			w = pred.get(w);
		}

		// si chemin vide, alors pas de solution
		if (path.isEmpty())
			System.out.println("PAS DE SOLUTION");
		else {
			System.out.println("Chemin (de la fin vers le debut au debut):");
			System.out.println(path);

			this.panel.updatePath(path); // mise a jour de l'affichage
		}

		this.panel.updateCurrent(Map.UNKNOWN);
	}

	/*
	 * METHODES RELATIVES A L'AFFICHAGE
	 */
	public void show() {
		this.frame.add(this.panel);
		this.frame.pack(); // taille relative aux elements a l'interieur de la fenetre
		this.frame.setVisible(true);
	}

	/**
	 * Permet de mettre en pause le programme pendant un certain nombre de ms,
	 * Controle la vitesse de l'animation
	 * 
	 * @param ms
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		}

		// en cas d'interruption (tres rare, sans consequences grave);
		catch (InterruptedException e) {
		}
	}
}
