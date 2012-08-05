/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.modules;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.storage.clsEnvironmentalImageMemory;
import pa._v38.storage.clsShortTermMemory;

import config.clsProperties;

/**
 * DOCUMENT (ende) - insert description
 * 
 * @author ende 03.08.2012, 15:59:38
 * 
 */
public class F21_ConversionToSecondaryProcessForPerceptionTest {

	private F21_ConversionToSecondaryProcessForPerception _f21;

	@Mock
	private String poPrefix;
	@Mock
	private clsProperties poProp;
	@Mock
	private HashMap<Integer, clsModuleBase> poModuleList;
	@Mock
	private SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData;
	@Mock
	private clsKnowledgeBaseHandler poKnowledgeBaseHandler;
	@Mock
	private clsShortTermMemory poShortTermMemory;
	@Mock
	private clsEnvironmentalImageMemory poTempLocalizationStorage;

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		poPrefix = "123";
		poProp = mock(clsProperties.class);

		poModuleList = mock(HashMap.class); //(HashMap<Integer, clsModuleBase>) mock(anyMapOf(Integer.class, clsModuleBase.class).getClass());
		poInterfaceData = mock(SortedMap.class); // (SortedMap<eInterfaces, ArrayList<Object>>) mock(anyMapOf(eInterfaces.class, anyListOf(Object.class).getClass()).getClass());
		poKnowledgeBaseHandler = mock(clsKnowledgeBaseHandler.class);
		poShortTermMemory = mock(clsShortTermMemory.class);
		poTempLocalizationStorage = mock(clsEnvironmentalImageMemory.class);
		

		when(poProp.getPropertyString(anyString())).thenReturn("BASIC");
		
		
		_f21 = new F21_ConversionToSecondaryProcessForPerception(poPrefix,
				poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler,
				poShortTermMemory, poTempLocalizationStorage);
	}

	/**
	 * DOCUMENT (ende) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link pa._v38.modules.F21_ConversionToSecondaryProcessForPerception#F21_ConversionToSecondaryProcessForPerception(java.lang.String, config.clsProperties, java.util.HashMap, java.util.SortedMap, pa._v38.memorymgmt.clsKnowledgeBaseHandler, pa._v38.storage.clsShortTermMemory, pa._v38.storage.clsEnvironmentalImageMemory)}
	 * .
	 */
	@Test
	public final void testF21_ConversionToSecondaryProcessForPerception() {
		assertNotNull(_f21.getDescription());
		assertNotNull(_f21.stateToTEXT());
		assertNotNull(_f21.hashCode());
	}

	/**
	 * Test method for
	 * {@link pa._v38.modules.F51_RealityCheckWishFulfillment#generateConcept(java.util.ArrayList)}
	 * .
	 */
	@Ignore
	public final void testGenerateConcept() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore
	public final void testClsPair() {
		fail("Not yet implemented"); // TODO
	}

	@Ignore
	public final void testConvertCompleteTPMtoWPM() {
		fail("Not yet implemented"); // TODO
	}
}
