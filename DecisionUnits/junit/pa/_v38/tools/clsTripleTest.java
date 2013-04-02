/**
 * CHANGELOG
 *
 * 11.08.2012 havlicek - File created
 *
 */
package pa._v38.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 11.08.2012, 10:15:07
 * 
 */
public class clsTripleTest {

	@Test
	public final void constructionTest() {
		clsTriple<Integer, Integer, Integer> oTestTriple1 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		// no exception expected
		assertEquals((Integer) 1, oTestTriple1.a);
		assertEquals((Integer) 2, oTestTriple1.b);
		assertEquals((Integer) 3, oTestTriple1.c);
		
		clsTriple<Integer, Integer, Integer> oTestTriple2 = new clsTriple<Integer, Integer, Integer>(10, 20, 30);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> oTestTriple3 = new clsTriple<Integer, Integer, Integer>(10, 20, 30);
		clsTriple<Double, Double, Double> oTestTriple4 = new clsTriple<Double, Double, Double>(10d, 20d, 30d);
		
		assertFalse(oTestTriple1.equals(oTestTriple2));		
		assertTrue(oTestTriple2.equals(oTestTriple3));
		assertFalse(oTestTriple2.equals(oTestTriple4));		
	}
	
	@Test
	public final void clonePositiveTest() throws CloneNotSupportedException {
		clsTriple<Integer, Integer, Integer> oTestTriple = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertEquals(oTestTriple, oTestTriple.clone());
	}

}
