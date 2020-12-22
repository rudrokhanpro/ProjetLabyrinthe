package Maze;

import Graph.Graph;
import Graph.GraphUtils;

public class Maze {
	Graph graph;
	int start, end;
	int current; // cellule actuelle

	public Maze(Map map) {
		this.start = map.getStart();
		this.end = map.getEnd();
		this.current = Map.UNKNOWN;
		
		// creation d'un graphe a partir d'une carte
		this.graph = GraphUtils.fromMap(map);
	}
}
