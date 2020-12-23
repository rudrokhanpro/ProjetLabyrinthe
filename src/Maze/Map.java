package Maze;

/**
 * Classe contenant les informations relatives a une carte de labyrinthe (plan,
 * entree, sortie)
 * 
 * @author Rudro KHAN
 *
 */
public class Map {
	// CONSTANTES
	public static final int UNKNOWN = -1;
	public static final char START = 'D';
	public static final char END = 'S';
	public static final char FIRE = 'F';
	public static final char WALL = '#';
	
	private String map[];
	private int nlines = 0;
	private int ncols = 0;
	private int start = UNKNOWN;
	private int end = UNKNOWN;
	private int fire = UNKNOWN;

	public Map(String map[], int nlines, int ncols, int start, int end, int fire) {
		this.map = map;
		this.nlines = nlines;
		this.ncols = ncols;
		this.start = start;
		this.end = end;
		this.fire = fire;
	}

	/*********************************************************************
	 * METHODES
	 *********************************************************************/
	
	public char getCase(int i, int j) {
		return map[i].charAt(j);
	}
	
	public char getCase(int n) {
		int i = n / ncols;
		int j = n % ncols;
		
		return getCase(i, j);
	}
	
	/**
	 * Verifie si une case est un mur dans la carte du labyrithe
	 * a partir de deux indices
	 * @param i Numero de la ligne
	 * @param j Numero de la colonne
	 * @return
	 */
	public boolean isWall(int i, int j) {
		return getCase(i, j) == WALL;
	}
	
	/**
	 * Verifie si une case est un mur dans la carte du labyrinthe
	 * a partir de son numero
	 * @param n NUmero de la case
	 * @return
	 */
	public boolean isWall(int n) {
		return getCase(n) == WALL;
	}

	/*********************************************************************
	 * GETTERS & SETTERS
	 *********************************************************************/
	public String[] getMap() {
		return map;
	}

	public void setMap(String[] map) {
		this.map = map;
	}

	public int getNlines() {
		return nlines;
	}

	public void setNlines(int nlines) {
		this.nlines = nlines;
	}

	public int getNcols() {
		return ncols;
	}

	public void setNcols(int ncols) {
		this.ncols = ncols;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getFire() {
		return fire;
	}

	public void setFire(int fire) {
		this.fire = fire;
	}
}