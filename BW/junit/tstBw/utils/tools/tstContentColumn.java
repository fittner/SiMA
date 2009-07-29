/**
 * 
 */
package tstBw.utils.tools;


import static org.junit.Assert.*;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.config.clsBWProperties;
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
		assertFalse(oColumn.getMaxContent() > java.lang.Double.MAX_VALUE);
	}
	
	@Test
	public void test2ParamConstructor(){
		double nContent = 50.0f;
		double nMaxContent = 100.0f;
		
		clsContentColumn oColumn = null;
		
		try {
			oColumn = new clsContentColumn(nContent, nMaxContent);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		
		assertFalse(oColumn.getContent() < 0.0f);
		assertEquals(oColumn.getContent(), nContent, 0.00001f);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertEquals(oColumn.getMaxContent(), nMaxContent, 0.00001f);
		assertFalse(oColumn.getMaxContent() > java.lang.Double.MAX_VALUE);		
	}
	
	@Test 
	public void testPropertyConstructor() {
		clsBWProperties oProp = clsContentColumn.getDefaultProperties("");
		clsContentColumn oColumn = null;
		oColumn = new clsContentColumn("", oProp);
		assertNotNull(oColumn);
		assertEquals(oColumn.getContent(), 0.0, 0.000001);
		assertEquals(oColumn.getMaxContent(), java.lang.Double.MAX_VALUE, 0.000001);
	}
	
	@Test
	public void test3ParamConstructor(){
		double nContent = 50.0f;
		double nMaxContent = 100.0f;
		
		clsContentColumn oColumn = null;
		
		try {
			oColumn = new clsContentColumn(nContent, nMaxContent);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		
		assertFalse(oColumn.getContent() < 0.0f);
		assertEquals(oColumn.getContent(), nContent, 0.00001f);
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertEquals(oColumn.getMaxContent(), nMaxContent, 0.00001f);
		assertFalse(oColumn.getMaxContent() > java.lang.Double.MAX_VALUE);		
	}	
	
	@Test
	public void testGetterSetter() {
		double nContent = 50.0f;
		double nMaxContent = 100.0f;

		
		clsContentColumn oColumn = null;
		
		try {
			oColumn = new clsContentColumn(nContent, nMaxContent);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		
		try {
			oColumn.setContent(-1.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertFalse(oColumn.getContent() < 0.0f);
		assertEquals(oColumn.getContent(), 0.0f, 0.00001f);
		assertFalse(oColumn.getContent() > nMaxContent);		
		
		try {
			oColumn.setContent(nMaxContent + 1.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertFalse(oColumn.getContent() < 0.0f);
		assertEquals(oColumn.getContent(), nMaxContent, 0.00001f);	
		assertFalse(oColumn.getContent() > nMaxContent);	
		
		nContent = 75.0f;
		try {
			oColumn.setContent(nContent);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertFalse(oColumn.getContent() < 0.0f);
		assertEquals(oColumn.getContent(), nContent, 0.00001f);
		assertFalse(oColumn.getContent() > nMaxContent);
		
		try {
			oColumn.setMaxContent(-1.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertFalse(oColumn.getMaxContent() < 0.0f);
		assertEquals(oColumn.getMaxContent(), 0.0f, 0.00001f);	
		assertFalse(oColumn.getContent() > oColumn.getMaxContent());
		assertFalse(oColumn.getMaxContent() > java.lang.Double.MAX_VALUE);

	}

	@Test
	public void testIncDec() {
		double nContent = 50.0f;
		double nMaxContent = 100.0f;

		clsContentColumn oColumn = null;
		
		try {
			oColumn = new clsContentColumn(nContent, nMaxContent);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}

		try {
			oColumn.decrease(5);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertEquals(oColumn.getContent(), nContent-5.0f, 0.00001f);		
		try {
			oColumn.increase(5);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertEquals(oColumn.getContent(), nContent, 0.00001f);		
	}
}















