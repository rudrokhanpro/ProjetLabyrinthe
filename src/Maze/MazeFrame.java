package Maze;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MazeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// dimensions de l'ecran
	public static final Dimension SCREEN = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int PADDING = 100; // marge interieur
	
	public MazeFrame() {
		this.setResizable(false); // ne pas redimensionner
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
