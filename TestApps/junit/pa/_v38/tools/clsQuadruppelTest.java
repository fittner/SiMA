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

import datatypes.helpstructures.clsQuadruppel;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 11.08.2012, 13:09:03
 * 
 */
public class clsQuadruppelTest {

    @Test
    public final void constructionTest() {
        clsQuadruppel<Integer, Integer, Integer, Integer> oQuadruppel1 = new clsQuadruppel<Integer, Integer, Integer, Integer>(1, 2, 3, 4);
        assertEquals((Integer) 1, oQuadruppel1.a);
        assertEquals((Integer) 2, oQuadruppel1.b);
        assertEquals((Integer) 3, oQuadruppel1.c);
        assertEquals((Integer) 4, oQuadruppel1.d);

        clsQuadruppel<Integer, Integer, Integer, Integer> oQuadruppel2 = new clsQuadruppel<Integer, Integer, Integer, Integer>(10, 20, 30, 40);
        clsQuadruppel<Integer, Integer, Integer, Integer> oQuadruppel3 = new clsQuadruppel<Integer, Integer, Integer, Integer>(10, 20, 30, 40);
        clsQuadruppel<Double, Double, Double, Double> oQuadruppel4 = new clsQuadruppel<Double, Double, Double, Double>(10d, 20d, 30d, 40d);

        assertFalse(oQuadruppel1.equals(oQuadruppel2));
        assertTrue(oQuadruppel2.equals(oQuadruppel3));
        assertFalse(oQuadruppel2.equals(oQuadruppel4));
    }

    @Test
    public final void cloneTest() throws CloneNotSupportedException {
        clsQuadruppel<Integer, Integer, Integer, Integer> oQuadruppel = new clsQuadruppel<Integer, Integer, Integer, Integer>(1, 2, 3, 4);
        assertEquals(oQuadruppel, oQuadruppel.clone());
    }

}
