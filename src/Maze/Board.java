package Maze;

import java.awt.*;
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

	class BoardPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public Map map;
		public int nlines;
		public int ncols;
		public int res; // resolution, taille de chaque case

		public BoardPanel(Map map) {
			this.map = map;
			this.nlines = map.getNlines();
			this.ncols = map.getNcols();

			System.out.println(SCREEN_DIMENSIONS);

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

			for (int i = 0; i < nlines; i++) {
				for (int j = 0; j < ncols; j++) {
					posX = j * res;
					posY = i * res;

					char cell = map.getCase(i, j);

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
					}
				} // j
			} // j
		}
	}

	JFrame frame; // fenetre
	BoardPanel panel; // cadre pour le labyrinthe
	Map map;

	public Board(Map map) {
		this.map = map;

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
