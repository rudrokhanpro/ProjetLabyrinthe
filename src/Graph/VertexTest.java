package Graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VertexTest {

	@Test
	void testNum() {
		int num = (int) Math.random() * Integer.MAX_VALUE;
		Vertex v = new Vertex(num);

		// verification de la valeur
		assertEquals(num, v.getNum());
	}

	@Test
	void testAdjacencyList() {
		Vertex v = new Vertex(1);
		
		// verification de l'allocation de memoire
		assertNotNull(v.getAdjacencyList());
		
		// verification de la taille initiale
		assertEquals(0, v.getAdjacencyList().size());
	}
}
