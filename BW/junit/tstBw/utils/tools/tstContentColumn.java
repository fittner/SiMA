/**
 * 
 */
package tstBw.utils.tools;


import static org.junit.Assert.*;
import bw.utils.tools.clsContentColumn;

import org.junit.Test;

/**
 * @author deutsch
 *
 */
public class tstContentColumn {
	

	@Test
	public void testDefaultConstructor(){
		clsContentColumn oColumn = new clsContentColumn();
		
		assertFalse(oColumn.getContent() < 0.0f);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertFalse(oColumn.getMaxContent() > java.lang.Float.MAX_VALUE);
	}
	
	@Test
	public void test2ParamConstructor(){
		float nContent = 50.0f;
		float nMaxContent = 100.0f;
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);
		
		assertFalse(oColumn.getContent() < 0.0f);
		assertTrue(oColumn.getContent() ==  nContent);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertTrue(oColumn.getMaxContent() == nMaxContent);
		assertFalse(oColumn.getMaxContent() > java.lang.Float.MAX_VALUE);		
	}
	
	@Test
	public void test3ParamConstructor(){
		float nContent = 50.0f;
		float nMaxContent = 100.0f;
		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);
		
		assertFalse(oColumn.getContent() < 0.0f);
		assertTrue(oColumn.getContent() ==  nContent);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertTrue(oColumn.getMaxContent() == nMaxContent);
		assertFalse(oColumn.getMaxContent() > java.lang.Float.MAX_VALUE);		
	}	
	
	@Test
	public void testGetterSetter() {
		float nContent = 50.0f;
		float nMaxContent = 100.0f;

		
		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);
		
		oColumn.setContent(-1.0f);
		assertFalse(oColumn.getContent() < 0.0f);
		assertTrue(oColumn.getContent() == 0.0f);
		assertFalse(oColumn.getContent() > nMaxContent);		
		
		oColumn.setContent(nMaxContent + 1.0f);
		assertFalse(oColumn.getContent() < 0.0f);
		assertTrue(oColumn.getContent() == nMaxContent);	
		assertFalse(oColumn.getContent() > nMaxContent);	
		
		nContent = 75.0f;
		oColumn.setContent(nContent);
		assertFalse(oColumn.getContent() < 0.0f);
		assertTrue(oColumn.getContent() == nContent);
		assertFalse(oColumn.getContent() > nMaxContent);
		
		oColumn.setMaxContent(-1.0f);
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertTrue(oColumn.getMaxContent() == 0.0f);	
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		assertFalse(oColumn.getMaxContent() > java.lang.Float.MAX_VALUE);

	}

	@Test
	public void testIncDec() {
		float nContent = 50.0f;
		float nMaxContent = 100.0f;

		clsContentColumn oColumn = new clsContentColumn(nContent, nMaxContent);

		oColumn.decrease(5);
		assertTrue(oColumn.getContent() == nContent-5.0f);		
		oColumn.increase(5);
		assertTrue(oColumn.getContent() == nContent);		
	}
}















