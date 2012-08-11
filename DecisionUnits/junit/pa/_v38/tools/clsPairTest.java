/**
 * CHANGELOG
 *
 * 11.08.2012 ende - File created
 *
 */
package pa._v38.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 11.08.2012, 11:05:10
 * 
 */
public class clsPairTest {

	@SuppressWarnings("static-access")
	@Test
	public final void constructorTest() {
		clsPair<Integer, Integer> pair = new clsPair<Integer, Integer>(1, 2);
		// no exception expected
		
		clsPair.create(1, 2);
		// no exception expected
		
		assertEquals(pair, pair.create(1, 2));
	}
	
	@Test
	public final void cloneTest() {
		fail("Not yet implemented"); //TODO implement testcase
	}

}
