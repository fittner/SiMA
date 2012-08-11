/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import pa._v38.tools.planningHelpers.PlanningNode;

/**
 * DOCUMENT (ende) - insert description
 * 
 * @author ende 03.08.2012, 15:59:02
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
	@Before
	public void setUp() throws Exception {
		_concept = new clsConcept();
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#clsConcept()}.
	 */
	@Test
	public final void constructorTest() {
		assertEquals(clsConcept.class, _concept.getClass());
		assertEquals(new ArrayList<PlanningNode>(), _concept.returnContent());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#pushPlanFragment(pa._v38.tools.planningHelpers.PlanningNode)}
	 * . Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#getPlanAtPos(int)}. Test
	 * method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#returnContent()}.
	 */
	@Test
	public final void planFragmentTest() {
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		_concept.pushPlanFragment(planFragment);
		assertEquals(planFragment, _concept.getPlanAtPos(0));
		assertEquals(planFragment, _concept.returnContent().get(0));
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#getSize()}
	 * .
	 */
	@Test
	public final void sizesTest() {
		assertEquals(0, _concept.getSize());
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		_concept.pushPlanFragment(planFragment);
		assertEquals(1, _concept.getSize());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#toString()}.
	 */
	@Test
	public final void toStringTest() {
		assertEquals("", _concept.toString());
		// TODO increase size
	}
}
