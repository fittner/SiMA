/**
 * CHANGELOG
 *
 * 03.08.2012 havlicek - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 03.08.2012, 20:38:45
 * 
 */
public class clsWordPresentationMeshTest {

	private clsWordPresentationMesh _wpm;

	private ExpectedException _thrown;

	private clsTriple<Integer, eDataType, eContentType> _poDataStructureIdentifier;

	@Before
	public void setUp() {
		_thrown = ExpectedException.none();
		_poDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(
				1, eDataType.EMOTION, eContentType.GOAL);
	}

	@Test
	public final void constructorTest() {
		_thrown = ExpectedException.none();
		clsWordPresentationMesh wpm = new clsWordPresentationMesh(
				_poDataStructureIdentifier, new ArrayList<clsAssociation>(),
				new String());
		// no exception expected
	}

	@Test
	public final void constructorObjectCastTest() {
		clsWordPresentationMesh wpm = new clsWordPresentationMesh(
				_poDataStructureIdentifier, new ArrayList<clsAssociation>(),
				new Object());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsWordPresentationMesh#toString()}.
	 */
	@Test
	public final void toStringTest() {
		clsWordPresentationMesh wpm = new clsWordPresentationMesh(
				_poDataStructureIdentifier, new ArrayList<clsAssociation>(),
				new String());
		assertNotNull(wpm.toString());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsWordPresentationMesh#isNullObject()}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public final void isNullObjectTest() {
		clsWordPresentationMesh wpm = new clsWordPresentationMesh(null, null,
				null);
	}

}
