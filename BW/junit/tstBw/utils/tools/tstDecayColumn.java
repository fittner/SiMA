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

import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.exceptions.exValueNotWithinRange;
import bw.utils.config.clsBWProperties;
import bw.utils.tools.clsDecayColumn;

/**
 * DOCUMENT (deutsch) - insert description 
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
		} catch (exContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (exContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertNotNull(oDC);
		
		assertEquals(oDC.getDecayRate(), 0.01f, 0.0000001f);
		assertEquals(oDC.getIncreaseRate(), 0.1f, 0.0000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getContent(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
	}

	
	
	@Test 
	public void testPropertyConstructor() {
		clsBWProperties oProp = clsDecayColumn.getDefaultProperties("");
		clsDecayColumn oColumn = null;
		try {
			oColumn = new clsDecayColumn("", oProp);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		assertNotNull(oColumn);
		assertEquals(oColumn.getContent(), 0.0, 0.000001);
		assertEquals(oColumn.getMaxContent(), java.lang.Double.MAX_VALUE, 0.000001);
		assertEquals(oColumn.getDecayRate(), 0.1, 0.000001);
		assertEquals(oColumn.getIncreaseRate(), 0.1, 0.000001);
		assertEquals(oColumn.getInjectionValue(), 0.0, 0.000001);
	}
	
	/**
	 * Test method for {@link bw.utils.tools.clsDecayColumn#clsDecayColumn(float, float)}.
	 */
	@Test
	public void testClsDecayColumnFloatFloat() {
		clsDecayColumn oDC = null;
		
		try {
			oDC = new clsDecayColumn(0.2f, 0.02f);
		} catch (exContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (exContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
			fail(e.toString());
		} catch (exContentColumnMinContentUnderrun e) {
			fail(e.toString());
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getIncreaseRate(), 0.1f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(0.5f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getIncreaseRate(), 0.5f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(1.0f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getIncreaseRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setIncreaseRate(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getIncreaseRate(), 1.0f, 0.0000001f);		
		
		try {
			oDC.setIncreaseRate(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getDecayRate(), 0.01f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(0.5f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getDecayRate(), 0.5f, 0.0000001f);
		
		try {
			oDC.setDecayRate(1.0f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getDecayRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getDecayRate(), 1.0f, 0.0000001f);
		
		try {
			oDC.setDecayRate(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.000000f);
		
		try {
			oDC.inject(0.1f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}

		assertEquals(oDC.getInjectionValue(), 0.1f, 0.0000001f);
		
		try {
			oDC.inject(0.1f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);
		
		try {
			oDC.inject(1.1f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (exValueNotWithinRange e) {
			// expected exception
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);		
		
		try {
			oDC.inject(0.9f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (exValueNotWithinRange e) {
			// expected exception
		}		
		
		assertEquals(oDC.getInjectionValue(), 0.2f, 0.0000001f);			
	
		try {
			oDC.inject(-0.1f);
			fail("ValueNotWithinRange not thrown!");			
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getMaxContent(), 1.0f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(0.5f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getInjectionValue(), 0.5f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(1.0f);
		} catch (exValueNotWithinRange e) {
			fail(e.toString());
		}
		
		assertEquals(oDC.getInjectionValue(), 1.0f, 0.0000001f);
		
		try {
			oDC.setInjectionValue(1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
			//this exception is expected!!
		}
		
		assertEquals(oDC.getInjectionValue(), 1.0f, 0.0000001f);		
		
		try {
			oDC.setInjectionValue(-1.5f);
			fail("ValueNotWithinRange not thrown!");
		} catch (exValueNotWithinRange e) {
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
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		oDC.update();	
		assertEquals(oDC.getContent(), 0.45f, 0.0000001f);
		oDC.update();	
		assertEquals(oDC.getContent(), 0.405f, 0.0000001f);
		oDC.update();	
		assertEquals(oDC.getContent(), 0.3645f, 0.0000001f);
		
		try {
			oDC.setContent(0.0f);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		
		try {
			oDC.inject(0.5f);
		} catch (exValueNotWithinRange e) {
		}
		
		oDC.update();
		assertEquals(oDC.getContent(), 0.09f, 0.0000001f);
		
		for (int i=0; i<20; i++) {
			oDC.update();
		}
		
		assertEquals(oDC.getContent(), 0.09f, 0.001f);
		
		
		for (int i=0; i<30; i++) {
			oDC.update();
		}
		
		assertTrue(oDC.isZero());
		assertEquals(oDC.getContent(), 0.0f, 0.00000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.00000001f);
		
		try {
			oDC.inject(0.01f);
		} catch (exValueNotWithinRange e) {
		}
		
		assertFalse(oDC.isZero());			
	}

}
