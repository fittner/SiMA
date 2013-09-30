/**
 * CHANGELOG
 *
 * 03.08.2012 havlicek - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import system.algorithm.planningHelpers.PlanningNode;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 03.08.2012, 15:58:48
 * 
 */
public class clsDomainTest {

	private clsDomain moDomain;

	@Before
	public void setUp() throws Exception {
		moDomain = new clsDomain();
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#clsDomain()}.
	 */
	@Test
	public final void constructorTest() {
		assertEquals(clsDomain.class, moDomain.getClass());
		assertEquals(new ArrayList<PlanningNode>(), moDomain.returnContent());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#pushPlanFragment(system.algorithm.planningHelpers.PlanningNode)}
	 * . Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#returnContent()}. Test
	 * method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#getPlanAtPos(int)}.
	 */
	@Test
	public final void planFragmentTest() {
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		moDomain.pushPlanFragment(planFragment);
	//	assertEquals(planFragment, moDomain.getPlanAtPos(0));
	//	assertEquals(planFragment, moDomain.returnContent().get(0));
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsDomain#getSize()}.
	 */
	@Test
	public final void getSizeTest() {
		// 0 element check
		assertEquals(0, moDomain.getSize());
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		moDomain.pushPlanFragment(planFragment);
		assertEquals(1, moDomain.getSize());
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsDomain#toString()}
	 * .
	 */
	@Test
	public final void toStringTest() {
		// 0 element check
		assertEquals("", moDomain.toString());
		// TODO increase size
	}

}
