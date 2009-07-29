/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.config.clsBWProperties;
import bw.utils.tools.clsFillLevel;

import org.junit.Test;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstFillLevel {

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel()}.
	 */
	@Test
	public void testClsFillLevel() {
		clsFillLevel oFill = new clsFillLevel();
		assertNotNull(oFill);

		assertEquals(oFill.getMaxContent(), java.lang.Double.MAX_VALUE);
		assertEquals(oFill.getContent(), 0.0f, 0.00001f);
		assertEquals(oFill.getChange(), 0.0f, 0.00001f);
	}

	@Test 
	public void testPropertyConstructor() {
		clsBWProperties oProp = clsFillLevel.getDefaultProperties("");
		clsFillLevel oColumn = null;
	
		oColumn = new clsFillLevel("", oProp);
	
		assertNotNull(oColumn);
		assertEquals(oColumn.getContent(), 0.0, 0.000001);
		assertEquals(oColumn.getMaxContent(), java.lang.Double.MAX_VALUE, 0.000001);
		assertEquals(oColumn.getLowerBound(), java.lang.Double.MAX_VALUE/3 , 0.000001);
		assertEquals(oColumn.getUpperBound(), (java.lang.Double.MAX_VALUE/3) * 2 , 0.000001);
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel(double, double)}.
	 */
	@Test
	public void testClsFillLevelFloatFloat() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(oFill);
		
		assertEquals(oFill.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oFill.getContent(), 1.5f, 0.00001f);
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
		assertEquals(oFill.getChange(), 0.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel(double, double, double)}.
	 */
	@Test
	public void testClsFillLevelFloatFloatFloat() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 0.1f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(oFill);
		
		assertEquals(oFill.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oFill.getContent(), 1.5f, 0.00001f);
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
		assertEquals(oFill.getChange(), 0.1f, 0.00001f);
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel(double, double, double, double)}.
	 */
	@Test
	public void testClsFillLevelFloatFloatFloatFloat() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 0.1f, 1.0f, 2.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(oFill);

		assertEquals(oFill.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oFill.getContent(), 1.5f, 0.00001f);
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
		assertEquals(oFill.getChange(), 0.1f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setBounds(double, double)}.
	 */
	@Test
	public void testSetBounds() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(oFill);

		oFill.setBounds(1.1f, 2.1f);
		assertEquals(oFill.getLowerBound(), 1.1f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.1f, 0.00001f);
		
		oFill.setBounds(-1.1f, 2.1f);
		assertEquals(oFill.getLowerBound(), 0.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.1f, 0.00001f);

		oFill.setBounds(1.1f, 5.1f);
		assertEquals(oFill.getLowerBound(), 1.1f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 3.0f, 0.00001f);
		
		oFill.setBounds(-1.1f, -0.1f);
		assertEquals(oFill.getLowerBound(), 0.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 0.0f, 0.00001f);
		
		oFill.setBounds(1.1f, 0.1f);
		assertEquals(oFill.getLowerBound(), 1.1f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 1.1f, 0.00001f);
		
		oFill.setBounds(2.5f, 2.1f);
		assertEquals(oFill.getLowerBound(), 2.5f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.5f, 0.00001f);
		
		oFill.setBounds(5.1f, 5.1f);
		assertEquals(oFill.getLowerBound(), 3.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 3.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#isLow()}.
	 */
	@Test
	public void testIsLow() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertFalse(oFill.isLow());
		
		try {
			oFill.setContent(0.3f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(oFill.isLow());
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#isHigh()}.
	 */
	@Test
	public void testIsHigh() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertFalse(oFill.isHigh());
		
		try {
			oFill.setContent(2.3f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(oFill.isHigh());
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#getLowerBound()}.
	 */
	@Test
	public void testGetLowerBound() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setLowerBound(double)}.
	 */
	@Test
	public void testSetLowerBound() {
		clsFillLevel oFill = null;
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 0.0f, 1.0f, 2.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
		
		oFill.setLowerBound(0.5f);
		assertEquals(oFill.getLowerBound(), 0.5f, 0.00001f);
		
		oFill.setLowerBound(-0.5f);
		assertEquals(oFill.getLowerBound(), 0.0f, 0.00001f);
		
		oFill.setLowerBound(5.0f);
		assertEquals(oFill.getLowerBound(), 3.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 3.0f, 0.00001f);
		
		oFill.setLowerBound(2.5f);
		assertEquals(oFill.getLowerBound(), 2.5f, 0.00001f);		

	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#getUpperBound()}.
	 */
	@Test
	public void testGetUpperBound() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setUpperBound(double)}.
	 */
	@Test
	public void testSetUpperBound() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 0.0f, 1.0f, 2.0f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getLowerBound(), 1.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 2.0f, 0.00001f);
		
		oFill.setUpperBound(1.5f);
		assertEquals(oFill.getUpperBound(), 1.5f, 0.00001f);
		
		oFill.setUpperBound(-0.5f);
		assertEquals(oFill.getUpperBound(), 0.0f, 0.00001f);
		
		oFill.setUpperBound(0.5f);
		assertEquals(oFill.getLowerBound(), 0.0f, 0.00001f);
		assertEquals(oFill.getUpperBound(), 0.5f, 0.00001f);		
		
		oFill.setUpperBound(5.0f);
		assertEquals(oFill.getUpperBound(), 3.0f, 0.00001f);	
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setChange(float}. 
	 */
	@Test
	public void testSetChange() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 1.1f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getChange(), 1.1f, 0.00001f);
		
		oFill.setChange(-1.3f);
		assertEquals(oFill.getChange(), -1.3f, 0.00001f);

	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#getChange(}. 
	 */
	@Test
	public void testGetChange() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 1.1f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getChange(), 1.1f, 0.00001f);
		
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#update(}. 
	 */
	@Test
	public void testUpdate() {
		clsFillLevel oFill = null;
		
		try {
			oFill = new clsFillLevel(1.5f, 3.0f, 0.5f);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(oFill.getContent(), 1.5f, 0.00001f);
		assertEquals(oFill.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oFill.getChange(), 0.5f, 0.00001f);	
		
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oFill.getContent(), 2.0f, 0.00001f);
		
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oFill.getContent(), 3.0f, 0.00001f);
		
		oFill.setChange(-0.25f);
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oFill.getContent(), 2.0f, 0.00001f);
		
		oFill.setChange(-0.98f);
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			oFill.update();
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oFill.getContent(), 0.0f, 0.00001f);
	}
		

}
