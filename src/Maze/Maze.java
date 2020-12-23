package Maze;

import java.util.ArrayList;
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
	private int start, end, fire;
	private int current; // cellule actuelle
	private Map map;
	private ArrayList<Integer> burning; // celulles incendie

	// Affichage
	private MazeFrame frame; // fenetre d'affichage
	private MazePanel panel; // composant affichant le labyrinthe
	private final int DELAY = 100; // vitesse de l'animation

	public Maze(Map map) {
		this.map = map;
		this.start = map.getStart();
		this.end = map.getEnd();
		this.fire = map.getFire();
		this.current = Map.UNKNOWN;

		// creation d'un graphe a partir d'une carte
		this.graph = GraphUtils.fromMap(map);

		// incendie
		this.burning = new ArrayList<Integer>();

		// initialisation de la fenetre d'affichage
		this.frame = new MazeFrame();
		this.panel = new MazePanel(this.map);
	}

	/*
	 * METHODES RELATIVES A LA RESOLUTION DU LABYRINTHE
	 */
	public void solve() {
		LinkedList<Integer> path = aStar();

		// si chemin vide, alors pas de solution
		if (path.isEmpty())
			System.out.println("PAS DE SOLUTION");

		// sinon animer le chemin et l'incendie
		else {
			// Chemin incomplet qui sera complete a chaque tour de bouble
			LinkedList<Integer> stepedPath = new LinkedList<Integer>();
			
			// Animation du labyrinthe
			for (int u : path) {
				this.current = u;

				// demarrer le feu apres le depart de la 1ere case
				if (u != this.start)
					burnAround();

				if (!this.burning.contains(this.current)) {
					stepedPath.add(u);
					this.panel.setPath(stepedPath); // mise a jour de l'affichage
//					this.panel.setCurrent(Map.UNKNOWN); // DEBUG
					this.panel.setBurning(burning); // mise a jour des celulles en feu
					this.panel.repaint();

					delay();
				} else {
					System.out.println("Vous avez ete pris par les feux");
					break;
				}
			}

		}
	}

	/**
	 * Recherche du chemin le plus court optimise a l'aide de l'algorithme A* (A
	 * Etoile) Heuristique: Distance par rapport a la sortie
	 */
	private LinkedList<Integer> aStar() {
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

		this.burning.add(this.fire);

		// RECHERCHE DU CHEMIN
		// tant qu'il y a des sommet a visiter
		while (!q.isEmpty()) {
			// extraire de la file le sommet non visite et au cout minimal
			int u = q.poll();

			// ajout du sommet au potentiel chemin
			potentialPath.add(u);

			// si on est arrive a la fin alors sortir
			if (u == end)
				break;

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

		// retracage du parcours
		while (w != Map.UNKNOWN) {
			path.addFirst(w);

			w = pred.get(w);
		}

		return path;

//		this.panel.setCurrent(Map.UNKNOWN);
	}

	private void burnAround() {
		int v;

		ArrayList<Integer> _burning = new ArrayList<Integer>(burning); // copie

		// pour tous les sommet en feu
		for (int u : _burning) {
			for (Edge e : this.graph.get(u).getAdjacencyList()) {
				v = e.getTo();

				// mettre le feu au celulles pas encore en feu
				if (!burning.contains(v))
					this.burning.add(v);
			} // for e
		} // for _burning
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
	private void delay() {
		try {
			Thread.sleep(DELAY);
		}

		// en cas d'interruption (tres rare, sans consequences grave);
		catch (InterruptedException e) {
		}
	}
}
