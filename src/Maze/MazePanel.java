package Maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import Graph.GraphUtils;

public class MazePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Labyrinthe
	private Map map; // carte du labyrinthe
	private int start, end; // numero du sommet de depart et d'arrive
	private int current = Map.UNKNOWN; // numero du sommet courant
	LinkedList<Integer> path;
	ArrayList<Integer> checked;

	// Affichage
	private int width, height, res; // largeur, hauteur, taille d'une case
	private int nlines, ncols; // nombre de lignes et colonnes

	public MazePanel(Map map) {
		this.map = map;
		this.start = map.getStart();
		this.end = map.getEnd();
		this.nlines = map.getNlines();
		this.ncols = map.getNcols();
		this.path = new LinkedList<Integer>();
		this.checked = new ArrayList<Integer>();

		// ajustement de la taille
		this.width = MazeFrame.SCREEN.width - MazeFrame.PADDING;
		this.height = MazeFrame.SCREEN.height - MazeFrame.PADDING;

		// resolution proportionnelle aux dimensions du labyrinthe
		this.res = Math.min(width / ncols, height / nlines);

		this.setSize(res * ncols, res * nlines);
		this.setPreferredSize(new Dimension(res * ncols, res * nlines));

		// fond d'ecran
		this.setBackground(Color.lightGray);
	}

	/**
	 * Fonction primitive responsable du dessin
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		int num;
		int posX, posY;
		char cell;

		for (int i = 0; i < nlines; i++) {
			for (int j = 0; j < ncols; j++) {
				num = i * ncols + j;
				posX = j * res;
				posY = i * res;
				cell = map.getCase(i, j);

				switch (cell) {
				// Si case d'entree
				case Map.START:
					g2.setColor(Color.red);
					g2.fillRect(posX, posY, res, res);
					break;

				// si case de sortie
				case Map.END:
					g2.setColor(Color.green);
					g2.fillRect(posX, posY, res, res);
					break;

				// si mur
				case Map.WALL:
					g2.setColor(Color.black);
					g2.fillRect(posX, posY, res, res);
					break;

				// si case libre
				default:
					g2.setColor(Color.white);
					g2.fillRect(posX, posY, res, res);
					break;
				} // switch

				// sommets visites
				if (checked.contains(num) && num != start && num != end) {
					g2.setColor(Color.yellow);
					g2.fillRect(posX, posY, res, res);
				}

				// chemin
				if (path.contains(num) && num != start && num != end) {
					g2.setColor(Color.magenta);
					g2.fillRect(posX, posY, res, res);
				}

				// case courante
				if (current == num) {
					g2.setColor(Color.magenta);
					g2.fillRect(posX, posY, res, res);
				}

//			// DEBUG
//			g2.setColor(Color.red);
//			g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
//			g2.drawString(String.valueOf(GraphUtils.getDistance(num, end, map)), posX, posY + 16);
			} // j
		} // i
	} // paint

	/**
	 * Met a jour la celulle actuelle, et redessine la fenetre
	 * 
	 * @param current
	 */
	public void updateCurrent(int current) {
		this.checked.add(current);
		this.current = current;

		repaint();
	}

	/**
	 * Met a jour le chemin, et redessine la fenetre
	 * 
	 * @param path
	 */
	public void updatePath(LinkedList<Integer> path) {
		this.path = path;

		repaint();
	}
}
