/**
 * @author langr
 * 22.04.2009, 10:39:13
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstbfg.symbolization.brainimages;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsSensorData;
import enums.eSensorExtType;

import bfg.symbolization.brainimages.clsContainerAbstractImages;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.ruletree.clsRuleCompareResult;
import bfg.tools.xmltools.clsXMLConfiguration;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 22.04.2009, 10:39:13
 * 
 */
public class tssImageAbstract {

	
	/**
	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#createImageAbstractList(java.util.Vector, int)}.
	 */
	@Test
	public void testCreateImageAbstractList() {
		
		//tests the loading of the AbstractImage Database (due to invalid characters, this could be not so easy...)
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		clsContainerAbstractImages oTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
		
		assertTrue(oTestImages.moAbstractImageList.size() > 0);
	}
	
	/**
	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#createImageAbstractList(java.util.Vector, int)}.
	 */
	@Test
	public void testEvaluateTreeBump() {
		//The list of abstract images that are matching
		ArrayList<clsRuleCompareResult> oMatch = null;

		//load the abstract images for the bubble of type PSY_10 (incl. AI's from parent xml's) into memory
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		clsContainerAbstractImages oTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
		
		//create the container for the simulated sensor data (normaly this comes into the DecisionUnitInterface) 
		clsSensorData oSensorData = new clsSensorData();
		//add bump info
		oSensorData.addSensorExt(eSensorExtType.BUMP, new clsBump(true));
		
		//add vision info (agent at 10/10)
		//clsPerceptionVisionEntity oVision = new clsPerceptionVisionEntity(new clsPoint(10, 10), 1, 1, true, false, 0);

		//add other info for test purposes (maybe you want to write your own JUnit and leave this as an example...)
		//clsPerceptionVisionEntity oVision = new clsPerceptionVisionEntity(new clsPoint(10, 10), 1, 1, true, false, 0);

		//trigger the comparison between defined AbstractImages and the created incoming data
		oMatch = oTestImages.associate(oSensorData, new clsIdentity());

		//print it in the system-output (it is only for the bump sensor, which is on the 5th place... I know it...)
		System.out.println(oMatch.get(0).toString());
		
		//complete the JUnit test 
		assertTrue( (oMatch.get(0).moMatch.get() == 1) );
}
	
//	/**
//	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#clsImageAbstract(int, java.lang.String, java.lang.String)}.
//	 */
//	@Test
//	public void testClsImageAbstract() {
//		fail("Not yet implemented");
//	}

	/**
	 * configuration for xml-loader
	 *
	 * @author langr
	 * 22.04.2009, 10:39:13
	 *
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TODO - (langr): Add the OS-independent path here!!! 
		clsXMLConfiguration.moConfigurationPath = "S:\\ARS\\PA\\BWv1\\DecisionUnits\\src\\bfg\\xml";
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 22.04.2009, 10:39:13
	 *
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 22.04.2009, 10:39:13
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 22.04.2009, 10:39:13
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
