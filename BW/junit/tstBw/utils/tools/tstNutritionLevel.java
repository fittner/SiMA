/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;
import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.utils.tools.clsNutritionLevel;

import org.junit.Test;

/**
 * TODO (deutsch) - insert description 
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
		} catch (ContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(oNL);
		assertEquals(oNL.getContent(), 1.0f, 0.00001f);
		assertEquals(oNL.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oNL.getLowerBound(), 0.5f, 0.00001f);
		assertEquals(oNL.getUpperBound(), 1.5f, 0.00001f);
		assertEquals(oNL.getDecreasePerStep(), 0.5f, 0.00001f);
		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsNutritionLevel#getDecreasePerStep()}.
	 */
	@Test
	public void testGetDecreasePerStep() {
		clsNutritionLevel oNL = null;
		
		try {
			oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		} catch (ContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
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
		} catch (ContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
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
		} catch (ContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
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
