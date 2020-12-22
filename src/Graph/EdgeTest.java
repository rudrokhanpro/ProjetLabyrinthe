package Graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EdgeTest {

	@Test
	void testEdge() {
		int a = 0;
		int b = 2;
		double w = 8;
		
		Edge e = new Edge(a, b, w);
		
		// verification des valeurs
		assertEquals(a, e.getFrom());
		assertEquals(b, e.getTo());
		assertEquals(w, e.getWeight());
	}
}
