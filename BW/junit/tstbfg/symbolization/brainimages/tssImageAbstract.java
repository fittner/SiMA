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
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.tools.xmltools.clsXMLConfiguration;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 22.04.2009, 10:39:13
 * 
 */
public class tssImageAbstract {

	
	clsImageAbstract moTestImage;
	
	/**
	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#createImageAbstractList(java.util.Vector, int)}.
	 */
	@Test
	public void testCreateImageAbstractList() {
		
		Vector<String> oFilePaths = new Vector<String>(); 
		oFilePaths.add("PSY_10");
		clsContainerAbstractImages oImages = moTestImage.createImageAbstractList(oFilePaths, 1);
		
		assertTrue(oImages.moAbstractImageList.size() > 0);
	}
	
//	/**
//	 * Test method for {@link bfg.symbolization.brainimages.clsImageAbstract#clsImageAbstract(int, java.lang.String, java.lang.String)}.
//	 */
//	@Test
//	public void testClsImageAbstract() {
//		fail("Not yet implemented");
//	}

	/**
	 * TODO (langr) - insert description
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
