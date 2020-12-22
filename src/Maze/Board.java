package Maze;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Classe affichant un labyrinthe a partir d'une carte
 * 
 * @author Rudro KHAN
 *
 */
public class Board {
	public final Dimension SCREEN_DIMENSIONS = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private int PADDING = 100;

	private class BoardPanel extends JPanel {
		public static final long serialVersionUID = 1L;

		public Map map;
		public int nlines;
		public int ncols;
		public int res; // resolution, taille de chaque case

		public BoardPanel(Map map) {
			this.map = map;
			this.nlines = map.getNlines();
			this.ncols = map.getNcols();

			// taille du cadre / proportionnelle au nombre de cases
			int width = SCREEN_DIMENSIONS.width - PADDING;
			int height = SCREEN_DIMENSIONS.height - PADDING;

			// resolution proportionnelle aux dimensions du labyrinthe
			res = Math.min(width / ncols, height / nlines);

			setSize(res * ncols, res * nlines);
			setPreferredSize(new Dimension(res * ncols, res * nlines));

			// fond d'ecran
			setBackground(Color.red);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2 = (Graphics2D) g;

			int posX, posY;
			char cell;

			for (int i = 0; i < nlines; i++) {
				for (int j = 0; j < ncols; j++) {
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

//					// DEBUG
//					g2.setColor(Color.pink);
//					g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 8));
//					g2.drawString(String.valueOf(num % ncols), posX, posY + 8);
				} // j
			} // i

		}
	}

	private JFrame frame; // fenetre
	private BoardPanel panel; // cadre pour le labyrinthe
	private Map map;
	private Maze maze;

	public Board(Map map) {
		this.map = map;
		this.maze = new Maze(map);

		// creer le cadre
		this.panel = new BoardPanel(map);

		// creer la fenetre
		this.frame = new JFrame();

		// ajout du cadre
		frame.add(panel);
		frame.pack();

		// affichage de la fenetre
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // arreter le programme lorsque qu'on ferme la fenetre
		frame.setLocationRelativeTo(null); // au centre de l'ecran
		frame.setVisible(true);
	}
}
