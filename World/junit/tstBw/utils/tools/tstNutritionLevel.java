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
import bw.utils.tools.clsNutritionLevel;

import org.junit.Test;

import config.clsBWProperties;

/**
 * 
 * 
 * @author deutsch
 * 
 */
public class tstNutritionLevel {

	/**
	 * Test method for {@link bw.utils.tools.clsNutritionLevel#clsNutritionLevel(float, float, float, float, float)}.
	 */
	@Test
	public void testClsNutritionLevel() {
		clsNutritionLevel oNL = null;
		
		try {
			oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.5f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		
		assertNotNull(oNL);
		assertEquals(oNL.getContent(), 1.0f, 0.00001f);
		assertEquals(oNL.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oNL.getLowerBound(), 0.5f, 0.00001f);
		assertEquals(oNL.getUpperBound(), 1.5f, 0.00001f);
		assertEquals(oNL.getDecreasePerStep(), 0.5f, 0.00001f);
		
	}
	
	@Test 
	public void testPropertyConstructor() {
		clsBWProperties oProp = clsNutritionLevel.getDefaultProperties("");
		clsNutritionLevel oColumn = null;
	
		oColumn = new clsNutritionLevel("", oProp);
	
		assertNotNull(oColumn);
		assertEquals(oColumn.getContent(), 0.75, 0.000001);
		assertEquals(oColumn.getMaxContent(), 1.2, 0.000001);
		assertEquals(oColumn.getLowerBound(), 0.3 , 0.000001);
		assertEquals(oColumn.getUpperBound(), 1.0 , 0.000001);
		assertEquals(oColumn.getChange(), 0.05 , 0.000001);		
	}
	

	/**
	 * Test method for {@link bw.utils.tools.clsNutritionLevel#getDecreasePerStep()}.
	 */
	@Test
	public void testGetDecreasePerStep() {
		clsNutritionLevel oNL = null;
		
		try {
			oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertEquals(oNL.getDecreasePerStep(), 0.01f, 0.00001f);

	}

	/**
	 * Test method for {@link bw.utils.tools.clsNutritionLevel#setDecreasePerStep(float)}.
	 */
	@Test
	public void testSetDecreasePerStep() {
		clsNutritionLevel oNL = null;
		try {
			oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertEquals(oNL.getDecreasePerStep(), 0.01f, 0.00001f);

		oNL.setDecreasePerStep(0.1f);
		assertEquals(oNL.getDecreasePerStep(), 0.1f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsNutritionLevel#step()}.
	 */
	@Test
	public void testStep() {
		clsNutritionLevel oNL = null;
		try {
			oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		} catch (exContentColumnMaxContentExceeded e) {
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			e.printStackTrace();
		}
		assertEquals(oNL.getDecreasePerStep(), 0.01f, 0.00001f);
		assertEquals(oNL.getContent(), 1.0f, 0.00001f);
		
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		oNL.step();
		
		assertEquals(oNL.getContent(), 0.9f, 0.00001f);
	}

}
