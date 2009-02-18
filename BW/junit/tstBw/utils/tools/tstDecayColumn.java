/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;

import org.junit.Test;

import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.exceptions.ValueNotWithinRange;
import bw.utils.tools.clsDecayColumn;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstDecayColumn {

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#clsDecayColumn()}.
	 */
	@Test
	public void testClsDecayColumn() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn();
		} catch (ContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (ContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertNotNull(oDC);
		
		assertEquals(oDC.getDecayRate(), 0.01f, 0.0000001f);
		assertEquals(oDC.getIncreaseRate(), 0.1f, 0.0000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getContent(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#clsDecayColumn(float, float)}.
	 */
	@Test
	public void testClsDecayColumnFloatFloat() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn(0.2f, 0.02f);
		} catch (ContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (ContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertNotNull(oDC);
		
		assertEquals(oDC.getDecayRate(), 0.02f, 0.0000001f);
		assertEquals(oDC.getIncreaseRate(), 0.2f, 0.0000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getContent(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#clsDecayColumn(float, float, float, float)}.
	 */
	@Test
	public void testClsDecayColumnFloatFloatFloatFloat() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn(1.0f, 2.0f, 0.2f, 0.02f);
		} catch (ContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (ContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertNotNull(oDC);
		
		assertEquals(oDC.getDecayRate(), 0.02f, 0.0000001f);
		assertEquals(oDC.getIncreaseRate(), 0.2f, 0.0000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getContent(), 1.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 2.0f, 0.0000001f);
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#setIncreaseRate(float)}.
	 */
	@Test
	public void testSetIncreaseRate() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn();
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		} catch (ValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getIncreaseRate(), 0.1f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(0.5f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getIncreaseRate(), 0.5f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(1.0f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getIncreaseRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getIncreaseRate(), 1.0f, 0.0000001f);		
		
		try {
			oDC.setIncreaseRate(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getIncreaseRate(), 1.0f, 0.0000001f);		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#setDecayRate(float)}.
	 */
	@Test
	public void testSetDecayRate() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn();
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		} catch (ValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getDecayRate(), 0.01f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(0.5f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getDecayRate(), 0.5f, 0.0000001f);
		
		try {
			oDC.setDecayRate(1.0f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getDecayRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getDecayRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getDecayRate(), 1.0f, 0.0000001f);		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#inject(float)}.
	 */
	@Test
	public void testInject() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn();
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		} catch (ValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.000000f);
		
		try {
			oDC.inject(0.1f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}

		assertEquals(oDC.getInjectionValue(), 0.1f, 0.0000001f);
		
		try {
			oDC.inject(0.1f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);
		
		try {
			oDC.inject(1.1f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (ValueNotWithinRange e) {
			// expected exception
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);		
		
		try {
			oDC.inject(0.9f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (ValueNotWithinRange e) {
			// expected exception
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);			
	
		try {
			oDC.inject(-0.1f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (ValueNotWithinRange e) {
			// expected exception
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);			
	}

	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#setInjectionValue(float)}.
	 */
	@Test
	public void testSetInjectionValue() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn();
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		} catch (ValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(0.5f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getInjectionValue(), 0.5f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(1.0f);
		} catch (ValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getInjectionValue(), 1.0f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getInjectionValue(), 1.0f, 0.0000001f);		
		
		try {
			oDC.setInjectionValue(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (ValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getInjectionValue(), 1.0f, 0.0000001f);		
	}


	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#update()}.
	 */
	@Test
	public void testUpdate() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn(0.5f, 1.0f, 0.2f, 0.1f);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		} catch (ValueNotWithinRange e) {
		}
		
		oDC.update();	
		assertEquals(oDC.getContent(), 0.45f, 0.0000001f);
		oDC.update();	
		assertEquals(oDC.getContent(), 0.405f, 0.0000001f);
		oDC.update();	
		assertEquals(oDC.getContent(), 0.3645f, 0.0000001f);
		
		try {
			oDC.setContent(0.0f);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
		
		try {
			oDC.inject(0.5f);
		} catch (ValueNotWithinRange e) {
		}
		
		oDC.update();
		assertEquals(oDC.getContent(), 0.09f, 0.0000001f);
		
		for (int i=0; i<20; i++) {
			oDC.update();
		}
		
		assertEquals(oDC.getContent(), 0.09f, 0.001f);
		
		assertFalse(oDC.isZero());
		try {
			oDC.setZeroDelta(0.01f);
		} catch (ValueNotWithinRange e1) {
		}
		
		for (int i=0; i<30; i++) {
			oDC.update();
		}
		
		assertTrue(oDC.isZero());
		assertEquals(oDC.getContent(), 0.0f, 0.00000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.00000001f);
		
		try {
			oDC.inject(0.01f);
		} catch (ValueNotWithinRange e) {
		}
		
		assertFalse(oDC.isZero());		
		
		try {
			oDC.setZeroDelta(-0.01f);
			fail("ValueNotWithinRange not thrown");
		} catch (ValueNotWithinRange e) {
			//expected exception
		}
		
		try {
			oDC.setZeroDelta(0.11f);
			fail("ValueNotWithinRange not thrown");
		} catch (ValueNotWithinRange e) {
			//expected exception
		}		
	}

}
