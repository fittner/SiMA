/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.body.internalSystems;

import static org.junit.Assert.*;
import bw.body.internalSystems.clsNutritionLevel;

import org.junit.Test;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstNutritionLevel {

	/**
	 * Test method for {@link bw.body.internalSystems.clsNutritionLevel#clsNutritionLevel(float, float, float, float, float)}.
	 */
	@Test
	public void testClsNutritionLevel() {
		clsNutritionLevel oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.5f);
		
		assertNotNull(oNL);
		assertEquals(oNL.getContent(), 1.0f, 0.00001f);
		assertEquals(oNL.getMaxContent(), 3.0f, 0.00001f);
		assertEquals(oNL.getLowerBound(), 0.5f, 0.00001f);
		assertEquals(oNL.getUpperBound(), 1.5f, 0.00001f);
		assertEquals(oNL.getDecreasePerStep(), 0.5f, 0.00001f);
		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsNutritionLevel#getDecreasePerStep()}.
	 */
	@Test
	public void testGetDecreasePerStep() {
		clsNutritionLevel oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		assertEquals(oNL.getDecreasePerStep(), 0.01f, 0.00001f);

	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsNutritionLevel#setDecreasePerStep(float)}.
	 */
	@Test
	public void testSetDecreasePerStep() {
		clsNutritionLevel oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
		assertEquals(oNL.getDecreasePerStep(), 0.01f, 0.00001f);

		oNL.setDecreasePerStep(0.1f);
		assertEquals(oNL.getDecreasePerStep(), 0.1f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsNutritionLevel#step()}.
	 */
	@Test
	public void testStep() {
		clsNutritionLevel oNL = new clsNutritionLevel(1.0f, 3.0f, 0.5f, 1.5f, 0.01f);
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
