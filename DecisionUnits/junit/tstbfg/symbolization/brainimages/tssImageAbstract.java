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

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bfg.symbolization.brainimages.clsContainerAbstractImages;
import bfg.symbolization.brainimages.clsContainerPerceptions;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsPerceptionVisionEntity;
import bfg.tools.cls0to1;
import bfg.tools.shapes.clsPoint;
import bfg.tools.xmltools.clsXMLConfiguration;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 22.04.2009, 10:39:13
 * 
 */
public class tssImageAbstract {

	
	clsContainerAbstractImages moTestImages;
	
	/**
	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#createImageAbstractList(java.util.Vector, int)}.
	 */
	@Test
	public void testCreateImageAbstractList() {
		
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		clsContainerAbstractImages moTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
		
		assertTrue(moTestImages.moAbstractImageList.size() > 0);
	}
	
	/**
	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#createImageAbstractList(java.util.Vector, int)}.
	 */
	@Test
	public void testEvaluateTree() {
		
		cls0to1 oMatch = new cls0to1();
		
		clsContainerPerceptions oPercCont = new clsContainerPerceptions(); 
		clsImagePerception oPerception = new clsImagePerception();
		
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		clsContainerAbstractImages moTestImages = clsImageAbstract.createImageAbstractList(oFilePaths, 1);
		
		
		//add bump info
		oPerception.moBumped.set(true);
		
		//add vision info (agent at 10/10)
		clsPerceptionVisionEntity oVision = new clsPerceptionVisionEntity(new clsPoint(10, 10), 1, 1, true, false, 0);
		oPerception.moVisionEntitiesList.moEntities.add(oVision);
		
		oPercCont.moPerceptions.add(oPerception);
		try
		{
			oMatch = moTestImages.moAbstractImageList.get(105).evaluateTree(oPerception, oPercCont, new clsIdentity());
		}catch(Exception e)
		{
			e.getMessage();
		}
		
		assertTrue( (oMatch.get() > 0.1) );
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
		clsImageAbstract moTestImage = new clsImageAbstract(1, "TEAM_PSY10", "Description");
		clsXMLConfiguration.moConfigurationPath = "S:\\ARS\\PA\\BFG\\xml";
	}

	/**
	 * TODO (langr) - insert description
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
	 * TODO (langr) - insert description
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
	 * TODO (langr) - insert description
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
