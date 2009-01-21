/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;
import bw.utils.tools.clsFillLevel;

import org.junit.Test;

/**
 * TODO (deutsch) - insert description 
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

		assertEquals(oFill.getMaxContent(), java.lang.Float.MAX_VALUE);
		assertEquals(oFill.getContent(), 0.0f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel(float, float)}.
	 */
	@Test
	public void testClsFillLevelFloatFloat() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);
		assertNotNull(oFill);
		
		assertEquals(oFill.getMaxContent(), 3.0f);
		assertEquals(oFill.getContent(), 1.5f);
		assertEquals(oFill.getLowerBound(), 1.0f);
		assertEquals(oFill.getUpperBound(), 2.0f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#clsFillLevel(float, float, float, float)}.
	 */
	@Test
	public void testClsFillLevelFloatFloatFloatFloat() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f, 1.0f, 2.0f);
		assertNotNull(oFill);

		assertEquals(oFill.getMaxContent(), 3.0f);
		assertEquals(oFill.getContent(), 1.5f);
		assertEquals(oFill.getLowerBound(), 1.0f);
		assertEquals(oFill.getUpperBound(), 2.0f);		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setBounds(float, float)}.
	 */
	@Test
	public void testSetBounds() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);
		assertNotNull(oFill);

		oFill.setBounds(1.1f, 2.1f);
		assertEquals(oFill.getLowerBound(), 1.1f);
		assertEquals(oFill.getUpperBound(), 2.1f);
		
		oFill.setBounds(-1.1f, 2.1f);
		assertEquals(oFill.getLowerBound(), 0.0f);
		assertEquals(oFill.getUpperBound(), 2.1f);

		oFill.setBounds(1.1f, 5.1f);
		assertEquals(oFill.getLowerBound(), 1.1f);
		assertEquals(oFill.getUpperBound(), 3.0f);
		
		oFill.setBounds(-1.1f, -0.1f);
		assertEquals(oFill.getLowerBound(), 0.0f);
		assertEquals(oFill.getUpperBound(), 0.0f);
		
		oFill.setBounds(1.1f, 0.1f);
		assertEquals(oFill.getLowerBound(), 1.1f);
		assertEquals(oFill.getUpperBound(), 1.1f);
		
		oFill.setBounds(2.5f, 2.1f);
		assertEquals(oFill.getLowerBound(), 2.1f);
		assertEquals(oFill.getUpperBound(), 2.1f);
		
		oFill.setBounds(5.1f, 5.1f);
		assertEquals(oFill.getLowerBound(), 3.0f);
		assertEquals(oFill.getUpperBound(), 3.0f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#isLow()}.
	 */
	@Test
	public void testIsLow() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);

		assertFalse(oFill.isLow());
		
		oFill.setContent(0.3f);
		
		assertTrue(oFill.isLow());
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#isHigh()}.
	 */
	@Test
	public void testIsHigh() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);

		assertFalse(oFill.isHigh());
		
		oFill.setContent(2.3f);
		
		assertTrue(oFill.isHigh());
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#getLowerBound()}.
	 */
	@Test
	public void testGetLowerBound() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);
		
		assertEquals(oFill.getLowerBound(), 1.0f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setLowerBound(float)}.
	 */
	@Test
	public void testSetLowerBound() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f, 1.0f, 2.0f);
		
		assertEquals(oFill.getLowerBound(), 1.0f);
		assertEquals(oFill.getUpperBound(), 2.0f);
		
		oFill.setLowerBound(0.5f);
		assertEquals(oFill.getLowerBound(), 0.5f);
		
		oFill.setLowerBound(-0.5f);
		assertEquals(oFill.getLowerBound(), 0.0f);
		
		oFill.setLowerBound(5.0f);
		assertEquals(oFill.getLowerBound(), 3.0f);
		assertEquals(oFill.getUpperBound(), 3.0f);
		
		oFill.setLowerBound(2.5f);
		assertEquals(oFill.getLowerBound(), 2.0f);		

	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#getUpperBound()}.
	 */
	@Test
	public void testGetUpperBound() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f);
		
		assertEquals(oFill.getUpperBound(), 2.0f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFillLevel#setUpperBound(float)}.
	 */
	@Test
	public void testSetUpperBound() {
		clsFillLevel oFill = new clsFillLevel(1.5f, 3.0f, 1.0f, 2.0f);
		
		assertEquals(oFill.getLowerBound(), 1.0f);
		assertEquals(oFill.getUpperBound(), 2.0f);
		
		oFill.setUpperBound(1.5f);
		assertEquals(oFill.getUpperBound(), 1.5f);
		
		oFill.setUpperBound(-0.5f);
		assertEquals(oFill.getUpperBound(), 0.0f);
		
		oFill.setUpperBound(0.5f);
		assertEquals(oFill.getLowerBound(), 0.5f);
		assertEquals(oFill.getUpperBound(), 0.5f);		
		
		oFill.setUpperBound(5.0f);
		assertEquals(oFill.getUpperBound(), 3.0f);	
	}

}
