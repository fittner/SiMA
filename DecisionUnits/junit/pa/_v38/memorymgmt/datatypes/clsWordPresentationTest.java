/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 03.08.2012, 20:39:21
 * 
 */
public class clsWordPresentationTest {
		
	private clsTriple<Integer, eDataType, eContentType> _clsTriple;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		_clsTriple = new clsTriple<Integer, eDataType, eContentType>(100, eDataType.ACT, eContentType.ACTION);
	}
	
	@Test
	public final void constructorTest() {
		clsWordPresentation wp = new clsWordPresentation(_clsTriple, "something");
		//no exception expected
		assertEquals(eContentType.ACTION, wp.moContentType);
		assertEquals(0,wp.moDSInstance_ID);
	}
	
	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsWordPresentation#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)}.
	 */
	@Test
	public final void compareToTest() {
		fail("Not yet implemented"); //TODO implement testcase
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsWordPresentation#toString()}.
	 */
	@Test
	public final void toStringTest() {
		fail("Not yet implemented"); //TODO implement testcase
	}

}
