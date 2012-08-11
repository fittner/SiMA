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
		clsTriple<Integer, Integer, Integer> testTriple1 = new clsTriple<>(10, 20, 30);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> testTriple2 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> testTriple3 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertFalse(testTriple1.equals(testTriple2));
		assertTrue(testTriple2.equals(testTriple3));
		
	}
	
	@Test
	public final void clonePositiveTest() throws CloneNotSupportedException {
		clsTriple<Integer, Integer, Integer> testTriple = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertEquals(testTriple, testTriple.clone());
	}

}
