package Graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GraphTest {

	@Test
	void testGraph() {
		Graph g = new Graph();

		// verification de l'allocation
		assertNotNull(g.getVertices());

		// verification de l'initialisation
		assertEquals(0, g.getVertices().size());
	}

	@Test
	void testAddVertex() {
		int num = 1;
		Graph g = new Graph();
		Vertex a = g.addVertex(num);

		// verification de l'ajout
		assertEquals(num, g.getVertices().size());

		// verification du numero de sommet
		assertEquals(num, g.getVertices().get(num).getNum());

		// verification de l'objet sommet
		assertEquals(a, g.getVertices().get(num));

		// verification lorsqu'un sommet est deja existant
		Vertex b = g.addVertex(num);

		assertNotEquals(a, g.getVertices().get(num));
		assertEquals(b, g.getVertices().get(num));
	}

	@Test
	void testGet() {
		int num = 1;
		Graph g = new Graph();

		// verification lorsqu'il n'y a aucun sommet
		assertNull(g.get(num));

		// verification d'egalite
		Vertex v = g.addVertex(num);

		assertEquals(v, g.get(num));
	}

	@Test
	void testGetOrder() {
		int n = 5;
		Graph g = new Graph();

		// verification lorsqu'il n'y a aucun sommet
		assertEquals(0, g.getOrder());

		// verification du comptage
		for (int i = 1; i <= n; i++)
			g.addVertex(i);

		assertEquals(n, g.getOrder());
	}
}
