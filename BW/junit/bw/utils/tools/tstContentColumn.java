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
	

	@Test
	public void testDefaultConstructor(){
		clsContentColumn oColumn = new clsContentColumn();
		
		assertFalse(oColumn.getContent() < 0);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0);
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);
	}
	
	@Test
	public void test2ParamConstructor(){
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
	public void test3ParamConstructor(){
		int nContent = 50;
		int nMaxContent = 100;
		int nPrecision = 2;
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent, nPrecision);
		
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
		int nPrecision = 2;		
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent, nPrecision);
		
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
		
		oColumn.setPrecision(0);
		oColumn.setMaxContent(java.lang.Integer.MAX_VALUE);
		assertFalse(oColumn.getMaxContent() < 0);
		assertTrue(oColumn.getMaxContent() == (int)(java.lang.Integer.MAX_VALUE));
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		assertFalse(oColumn.getMaxContent() > java.lang.Integer.MAX_VALUE);	
		
		oColumn.setPrecision(2);
		nMaxContent = oColumn.setMaxContent(100);
		nContent = oColumn.setContent(50);
		oColumn.setPrecision(3);
		assertTrue(oColumn.getContent() == nContent);	
		assertTrue(oColumn.getMaxContent() == nMaxContent);	
		oColumn.setPrecision(0);
		assertTrue(oColumn.getContent() == nContent);	
		assertTrue(oColumn.getMaxContent() == nMaxContent);	
	}

	@Test
	public void testIncDec() {
		int nContent = 50;
		int nMaxContent = 100;

		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);

		oColumn.decrease(5);
		assertTrue(oColumn.getContent() == nContent-5);		
		oColumn.increase(5);
		assertTrue(oColumn.getContent() == nContent);		
	}
}















