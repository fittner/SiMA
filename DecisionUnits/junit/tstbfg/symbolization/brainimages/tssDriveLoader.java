/**
 * tssDriveLoader.java: DecisionUnits - tstbfg.symbolization.brainimages
 * 
 * @author langr
 * 28.09.2009, 17:54:38
 */
package tstbfg.symbolization.brainimages;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enums.pa.eDriveContent;

import pa.loader.clsDriveLoader;
import pa.loader.clsTemplateDrive;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 17:54:38
 * 
 */
public class tssDriveLoader {

	/**
	 * Test method for the drive loader
	 */
	@Test
	public void testDriveLoader() {

		HashMap<eDriveContent, clsTemplateDrive> oDriveList = clsDriveLoader.createDriveList("1", "PSY_10");
		
		assertTrue(oDriveList.size() > 0);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
}
