/**
 * tssContextLoader.java: DecisionUnits - tstbfg.symbolization.brainimages
 * 
 * @author zeilinger
 * 08.10.2009, 10:18:13
 */
package tstbfg.symbolization.brainimages;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pa._v19.datatypes.clsAssociationContext;
import pa._v19.datatypes.clsThingPresentationSingle;
import pa._v19.loader.clsContextLoader;

/**
 * 
 * 
 * @author zeilinger
 * 08.10.2009, 10:18:13
 *  @deprecated
 */
public class tssContextLoader {
	/**
	 * Test method for the context loader
	 */
	@Test
	public void testDriveLoader() {
		String oEntityType = "CAKE";  
		ArrayList<clsAssociationContext<clsThingPresentationSingle>> oContextList = clsContextLoader.createContext(oEntityType);

		assertTrue(oContextList.size() > 0);
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
