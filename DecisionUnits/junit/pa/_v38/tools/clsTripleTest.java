/**
 * CHANGELOG
 *
 * 11.08.2012 ende - File created
 *
 */
package pa._v38.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 11.08.2012, 10:15:07
 * 
 */
public class clsTripleTest {

	@Test
	public final void constructionTest() {
		clsTriple<Integer, Integer, Integer> testTriple1 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		// no exception expected
		assertEquals((Integer) 1, testTriple1.a);
		assertEquals((Integer) 2, testTriple1.b);
		assertEquals((Integer) 3, testTriple1.c);
		
		clsTriple<Integer, Integer, Integer> testTriple2 = new clsTriple<Integer, Integer, Integer>(10, 20, 30);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> testTriple3 = new clsTriple<Integer, Integer, Integer>(10, 20, 30);
		clsTriple<Double, Double, Double> testTriple4 = new clsTriple<Double, Double, Double>(10d, 20d, 30d);
		
		assertFalse(testTriple1.equals(testTriple2));		
		assertTrue(testTriple2.equals(testTriple3));
		assertFalse(testTriple2.equals(testTriple4));		
	}
	
	@Test
	public final void clonePositiveTest() throws CloneNotSupportedException {
		clsTriple<Integer, Integer, Integer> testTriple = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertEquals(testTriple, testTriple.clone());
	}

}
