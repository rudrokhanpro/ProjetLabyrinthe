import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Maze.Map;
import Maze.Maze;

public class Main {
	static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);

		// lire labyrinthe
		Map map = readMap();

		// afficher le labyrinthe
		Maze maze = new Maze(map);

//		// DEBUG
//		Maze maze = new Maze(Exemple.map);

		// afficher le labyrinthe
		maze.show();

		// resoudre le labyrinthe
		maze.solve();

		// Si le prisonier reussit a s'echaper
		if (maze.getHasEscape()) {

			// sauvegarder le chemein dans un fichier
			System.out.println("SAUVE !");

			savePath(maze);
		}

		sc.close();
	}

	/**
	 * Saisie d'une carte representant un labyrinthe et extraction de l'entree et la
	 * sortie
	 * 
	 * @return map Un objet Map contenant les informations relatives a une carte de
	 *         labyrinthe
	 */
	public static Map readMap() {
		String line;

		// saisir dimensions: H L
		System.out.print("H L ? ");
		line = sc.nextLine();
		int nlines = Integer.parseInt(line.split(" ")[0]);
		int ncols = Integer.parseInt(line.split(" ")[1]);

		// saisir lignes
		String map[] = new String[nlines];
		int start = Map.UNKNOWN;
		int end = Map.UNKNOWN;
		int fire = Map.UNKNOWN;

		System.out.println("CARTE ? (D = depart, S = sortie, F = feu, # = mur, . = libre)");
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

			// recherche du feu
			if (map[i].indexOf(Map.FIRE) != Map.UNKNOWN) {
				fire = i * ncols + map[i].indexOf(Map.FIRE);
			}
		}

//		// DEBUG
//		System.out.println("Lines = " + nlines + ", Cols = " + ncols);
//		System.out.println("Start = " + start + ", End = " + end);

		// creation d'une classe map
		return new Map(map, nlines, ncols, start, end, fire);
	}

	public static void savePath(Maze maze) {
		System.out.print("Fichier de sauvegarde ? ");
		String filename = sc.nextLine();
		File file = new File(filename);
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			Map map = maze.getMap();
			int nlines = map.getNlines();
			int ncols = map.getNcols();
			int num;

			for (int i = 0; i < nlines; i++) {
				for (int j = 0; j < ncols; j++) {
					num = i * nlines + j;

					if (maze.getPath().contains(num) && num != map.getStart())
						writer.write('*');
					else
						writer.write(map.getCase(num));
				}
				writer.write("\n");
			}

			writer.close();
			
			System.out.println("La chemin a ete sauvegarde dans " + filename);
		} catch (IOException e) {
			System.out.println("Erreur interne");
			e.printStackTrace();
		}

	}
}
