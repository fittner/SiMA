/**
 * CHANGELOG
 *
 * 11.08.2012 ende - File created
 *
 */
package pa._v38.tools;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 11.08.2012, 10:15:07
 * 
 */
public class clsTripleTest {
	
	
	
	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 11.08.2012 10:15:07
	 *
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 11.08.2012 10:15:07
	 *
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 11.08.2012 10:15:07
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 11.08.2012 10:15:07
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void constructionTest() {
		clsTriple<Integer, Integer, Integer> testTriple1 = new clsTriple<>(1, 2, 3);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> testTriple2 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		// no exception expected
		
		clsTriple<Integer, Integer, Integer> testTriple3 = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertFalse(testTriple1.equals(testTriple2));
		assertTrue(testTriple2.equals(testTriple3));
		
	}
	
	@Test
	public final void equalTest() throws CloneNotSupportedException {
		clsTriple<Integer, Integer, Integer> testTriple = new clsTriple<Integer, Integer, Integer>(1, 2, 3);
		
		assertEquals(testTriple, testTriple.clone());
	}

}
