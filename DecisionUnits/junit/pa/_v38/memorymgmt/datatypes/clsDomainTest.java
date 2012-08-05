/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
 * @author ende 03.08.2012, 15:58:48
 * 
 */
public class clsDomainTest {

	private clsDomain _domain;

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:58:48
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:58:48
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:58:48
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_domain = new clsDomain();
	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:58:48
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#clsDomain()}.
	 */
	@Test
	public final void testClsDomain() {
		assertEquals(clsDomain.class, _domain.getClass());
		assertEquals(new ArrayList<PlanningNode>(), _domain.returnContent());
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#pushPlanFragment(pa._v38.tools.planningHelpers.PlanningNode)}
	 * . Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#returnContent()}. Test
	 * method for
	 * {@link pa._v38.memorymgmt.datatypes.clsDomain#getPlanAtPos(int)}.
	 */
	@Test
	public final void testPlanFragment() {
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		_domain.pushPlanFragment(planFragment);
		assertEquals(planFragment, _domain.getPlanAtPos(0));
		assertEquals(planFragment, _domain.returnContent().get(0));
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsDomain#getSize()}.
	 */
	@Test
	public final void testGetSize() {
		// 0 element check
		assertEquals(0, _domain.getSize());
		clsPlanFragment planFragment = mock(clsPlanFragment.class);
		_domain.pushPlanFragment(planFragment);
		assertEquals(1, _domain.getSize());
	}

	/**
	 * Test method for {@link pa._v38.memorymgmt.datatypes.clsDomain#toString()}
	 * .
	 */
	@Test
	public final void testToString() {
		// 0 element check
		assertEquals("", _domain.toString());
		// TODO increase size
	}

}
