/**
 * CHANGELOG
 *
 * 11.08.2012 havlicek - File created
 *
 */
package pa._v38.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 11.08.2012, 11:05:10
 * 
 */
public class clsPairTest {

	@SuppressWarnings("static-access")
	@Test
	public final void constructorTest() {
		clsPair<Integer, Integer> oPair = new clsPair<Integer, Integer>(1, 2);
		// no exception expected
		assertEquals((Integer) 1, oPair.a);
		assertEquals((Integer) 2, oPair.b);
		
		clsPair.create(1, 2);
		// no exception expected
		
		assertEquals(oPair, clsPair.create(1, 2));
		assertEquals(oPair, oPair.create(1, 2));
	}
	
	@Test
	public final void cloneTest() throws CloneNotSupportedException {
		clsPair<Integer, Integer> oPair = new clsPair<Integer, Integer>(1, 2);
		
		assertEquals(oPair, oPair.clone());
	}

}
