/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (ende) - unit test for clsWorldPresentation
 * 
 * @author ende 03.08.2012, 20:39:21
 * 
 */
public class clsWordPresentationTest {

	private clsTriple<Integer, eDataType, eContentType> _clsTriple;

	@Before
	public void setUp() {
		_clsTriple = new clsTriple<Integer, eDataType, eContentType>(100,
				eDataType.ACT, eContentType.ACTION);
	}

	/**
	 * 
	 * DOCUMENT (ende) - Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsWordPresentation#clsWordPresentation(clsTriple, Object)
	 * (pa._v38.memorymgmt.datatypes.clsDataStructurePA)}
	 * 
	 * @since 12.08.2012 09:06:19
	 * 
	 */
	@Test
	public final void constructorTest() {
		clsWordPresentation wp;
		wp = new clsWordPresentation(_clsTriple, "something");
		// no exception expected

		assertEquals(eContentType.ACTION, wp.moContentType);
		assertEquals(0, wp.moDSInstance_ID);

		wp = new clsWordPresentation(_clsTriple, null);
		// no exception expected
	}

	/**
	 * 
	 * DOCUMENT (ende) - Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsWordPresentation#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)}
	 * 
	 * @since 12.08.2012 09:07:09
	 * 
	 */
	@Test
	public final void compareToTest() {
		clsWordPresentation wp1 = new clsWordPresentation(_clsTriple,
				"something");
		clsWordPresentation wp2 = new clsWordPresentation(_clsTriple,
				"something");
		clsTriple<Integer, eDataType, eContentType> triple2 = new clsTriple<Integer, eDataType, eContentType>(
				200, eDataType.EMOTION, eContentType.AFFECT);
		clsWordPresentation wp3 = new clsWordPresentation(triple2, null);

		assertEquals(1.0, wp1.compareTo(wp2), 0.0); // same return 1
		assertEquals(0.0, wp1.compareTo(wp3), 0.0); // different return 0
	}

	/**
	 * 
	 * DOCUMENT (ende) - Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsWordPresentation#clone()
	 * (pa._v38.memorymgmt.datatypes.clsDataStructurePA)}.
	 * 
	 * @since 12.08.2012 09:05:26
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Test
	public final void cloneTest() throws CloneNotSupportedException {
		clsWordPresentation wp = new clsWordPresentation(_clsTriple,
				"something");
		clsWordPresentation wpClone = (clsWordPresentation) wp.clone();
		assertNotNull(wpClone);
		assertTrue(wpClone instanceof clsWordPresentation);
		assertEquals(wp, wp.clone());
	}
}
