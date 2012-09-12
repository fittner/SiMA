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
 * 11.08.2012, 13:09:03
 * 
 */
public class clsQuadruppelTest {

	@Test
	public final void constructionTest() {
		clsQuadruppel<Integer, Integer, Integer, Integer> quadruppel1 = new clsQuadruppel<Integer, Integer, Integer, Integer>(1, 2, 3, 4);
		assertEquals((Integer) 1, quadruppel1.a);
		assertEquals((Integer) 2, quadruppel1.b);
		assertEquals((Integer) 3, quadruppel1.c);
		assertEquals((Integer) 4, quadruppel1.d);
		
		clsQuadruppel<Integer, Integer, Integer, Integer> quadruppel2 = new clsQuadruppel<Integer, Integer, Integer, Integer>(10,20,30,40);
		clsQuadruppel<Integer, Integer, Integer, Integer> quadruppel3 = new clsQuadruppel<Integer, Integer, Integer, Integer>(10,20,30,40);
		clsQuadruppel<Double, Double, Double, Double> quadruppel4 = new clsQuadruppel<Double, Double, Double, Double>(10d, 20d, 30d, 40d);
		
		assertFalse(quadruppel1.equals(quadruppel2));
		assertTrue(quadruppel2.equals(quadruppel3));
		assertFalse(quadruppel2.equals(quadruppel4));
	}
	
	@Test
	public final void cloneTest() throws CloneNotSupportedException {
		clsQuadruppel<Integer, Integer, Integer, Integer> quadruppel = new clsQuadruppel<Integer, Integer, Integer, Integer>(1, 2, 3, 4);
		assertEquals(quadruppel, quadruppel.clone());
	}

}
