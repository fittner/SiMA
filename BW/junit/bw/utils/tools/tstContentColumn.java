/**
 * 
 */
package bw.utils.tools;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author deutsch
 *
 */
public class tstContentColumn {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDefaultConstructor(){
		clsContentColumn oColumn = new clsContentColumn();
		
		assertFalse(oColumn.getContent() < 0);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0);
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);
	}
	
	@Test
	public void testParamConstructor(){
		int nContent = 50;
		int nMaxContent = 100;
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);
		
		assertFalse(oColumn.getContent() < 0);
		assertTrue(oColumn.getContent() ==  nContent);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0);
		assertTrue(oColumn.getMaxContent() == nMaxContent);
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);		
	}
	
	@Test
	public void testGetterSetter() {
		int nContent = 50;
		int nMaxContent = 100;
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);
		
		oColumn.setContent(-1);
		assertFalse(oColumn.getContent() < 0);
		assertTrue(oColumn.getContent() == 0);	
		assertFalse(oColumn.getContent() > nMaxContent);		
		
		oColumn.setContent(nMaxContent + 1);
		assertFalse(oColumn.getContent() < 0);
		assertTrue(oColumn.getContent() == nMaxContent);	
		assertFalse(oColumn.getContent() > nMaxContent);	
		
		nContent = 75;
		oColumn.setContent(nContent);
		assertFalse(oColumn.getContent() < 0);
		assertTrue(oColumn.getContent() == nContent);		
		assertFalse(oColumn.getContent() > nMaxContent);			
		
		oColumn.setMaxContent(-1);
		assertFalse(oColumn.getMaxContent() < 0);
		assertTrue(oColumn.getMaxContent() == 0);	
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);
		
		oColumn.setMaxContent(java.lang.Integer.MAX_VALUE);
		assertFalse(oColumn.getMaxContent() < 0);
		assertTrue(oColumn.getMaxContent() == java.lang.Integer.MAX_VALUE);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);	
	}
}















