/**
 * CHANGELOG
 *
 * 03.08.2012 havlicek - File created
 *
 */
package pa._v38.modules;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.longtermmemory.clsLongTermMemoryHandler;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.clsTriple;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 03.08.2012, 15:59:38
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
	private itfModuleMemoryAccess moLongTermMemory;
	@Mock
	private clsShortTermMemory poShortTermMemory;
	@Mock
	private clsEnvironmentalImageMemory poTempLocalizationStorage;

	/**
	 * DOCUMENT (havlicek) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * DOCUMENT (havlicek) - insert description
	 * 
	 * @since 03.08.2012 15:59:38
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (havlicek) - insert description
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

		poModuleList = mock(HashMap.class); // (HashMap<Integer, clsModuleBase>)
											// mock(anyMapOf(Integer.class,
											// clsModuleBase.class).getClass());
		poInterfaceData = mock(SortedMap.class); // (SortedMap<eInterfaces,
													// ArrayList<Object>>)
													// mock(anyMapOf(eInterfaces.class,
													// anyListOf(Object.class).getClass()).getClass());
		moLongTermMemory = mock(clsLongTermMemoryHandler.class);
		poShortTermMemory = mock(clsShortTermMemory.class);
		poTempLocalizationStorage = mock(clsEnvironmentalImageMemory.class);

		when(poProp.getPropertyString(anyString())).thenReturn("BASIC");

		_f21 = new F21_ConversionToSecondaryProcessForPerception(poPrefix,
				poProp, poModuleList, poInterfaceData, moLongTermMemory,
				poShortTermMemory, poTempLocalizationStorage, poTempLocalizationStorage, 
				new DT3_PsychicEnergyStorage());
	}

	/**
	 * DOCUMENT (havlicek) - insert description
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
	 * {@link pa._v38.modules.F21_ConversionToSecondaryProcessForPerception#F21_ConversionToSecondaryProcessForPerception(java.lang.String, config.clsProperties, java.util.HashMap, java.util.SortedMap, pa._v38.memorymgmt.clsKnowledgeBaseHandler, pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory, pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory)}
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
	@Test
	public final void testGenerateConcept() {

		clsConcept expectedResult = new clsConcept();

		clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(
				1, eDataType.EMOTION, eContentType.GOAL);
		ArrayList<clsSecondaryDataStructure> poAssociatedWordPresentations = new ArrayList<clsSecondaryDataStructure>();
		clsWordPresentationMesh wpm1 = new clsWordPresentationMesh(
				poDataStructureIdentifier, new ArrayList<clsAssociation>(),
				"NOTHING");
		poAssociatedWordPresentations.add(wpm1);
		String poContent = "HELLO";

		clsAct act = new clsAct(poDataStructureIdentifier,
				poAssociatedWordPresentations, poContent);

		clsPlanFragment planFragment = new clsPlanFragment(act, new clsImage(),
				new clsImage());

//	expectedResult.pushPlanFragment(planFragment);

		List<clsWordPresentationMesh> wpms = new ArrayList<clsWordPresentationMesh>();
		wpms.add(wpm1);

	//	clsConcept realResult = _f21.generateConcept(wpms);

	//	assertNotNull(realResult);
	//	assertEquals(expectedResult, realResult);
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