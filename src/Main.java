import java.util.*;

import Maze.Map;
import Maze.Board;

public class Main {
	static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);

		// lire labyrinthe
		Map map = readMap();

		// afficher le labyrinthe
		Board board = new Board(map);
		
//		// DEBUG
//		Board board = new Board(Exemple.map);
		
		sc.close();
	}

	/**
	 * Saisie d'une carte representant un labyrinthe et extraction de l'entree et la sortie
	 * @return map Un objet Map contenant les informations relatives a une carte de labyrinthe
	 */
	public static Map readMap() {
		String line;
		sc = new Scanner(System.in);

		// saisir dimensions: H L
		System.out.print("H L ? ");
		line = sc.nextLine();
		int nlines = Integer.parseInt(line.split(" ")[0]);
		int ncols = Integer.parseInt(line.split(" ")[1]);
		
		// saisir lignes
		String map[] = new String[nlines];
		int start = Map.UNKNOWN;
		int end = Map.UNKNOWN;

		System.out.println("CARTE ? (D = depart, S = sortie, # = mur, . = libre)");
		for (int i = 0; i < nlines; i++) {
			map[i] = sc.nextLine();

			// recherche de l'entree
			if (map[i].indexOf(Map.START) != Map.UNKNOWN) {
				start = i * ncols + map[i].indexOf(Map.START);
			}

			// recherche de la sortie
			if (map[i].indexOf(Map.END) != Map.UNKNOWN) {
				end = i * ncols + map[i].indexOf(Map.END);
			}
		}

//		// DEBUG
//		System.out.println("Lines = " + nlines + ", Cols = " + ncols);
//		System.out.println("Start = " + start + ", End = " + end);

		// creation d'une classe map
		return new Map(map, nlines, ncols, start, end);
	}
}
