/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pa._v38.tools.planningHelpers.PlanningNode;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 03.08.2012, 15:59:02
 * 
 */
public class clsConceptTest {

	
	private clsConcept _concept;
	
	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 03.08.2012 15:59:02
	 *
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 03.08.2012 15:59:02
	 *
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 03.08.2012 15:59:02
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_concept = new clsConcept();
	}

	/**
	 * DOCUMENT (ende) - insert description
	 *
	 * @since 03.08.2012 15:59:02
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#clsConcept()}.
	 */
	@Test
	public final void testClsConcept() {		
		assertEquals(clsConcept.class, _concept.getClass());
		assertEquals(new ArrayList<PlanningNode>(), _concept.returnContent());
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#pushPlanFragment(pa._v38.tools.planningHelpers.PlanningNode)}.
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#getPlanAtPos(int)}.
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#returnContent()}.
	 */
	@Test
	public final void testPlanFragment() {		
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#getSize()}.
	 */
	@Test
	public final void testSizes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}
}
