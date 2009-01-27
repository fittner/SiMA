/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bw.utils.datatypes.clsMutableFloat;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstFood {

	/**
	 * Test method for {@link bw.utils.tools.clsFood#clsFood()}.
	 */
	@Test
	public void testClsFood() {
		clsFood oFood = new clsFood();
		
		assertNotNull(oFood);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#setAmount(float)}.
	 */
	@Test
	public void testSetAmount() {
		clsFood oFood = new clsFood();
		assertEquals(oFood.getAmount(), 0.0f, 0.00001f);
		oFood.setAmount(1.0f);
		assertEquals(oFood.getAmount(), 1.0f, 0.00001f);
		oFood.setAmount(2.0f);
		assertEquals(oFood.getAmount(), 2.0f, 0.00001f);
		
		oFood.addNutritionFraction(1, 2.0f);
		oFood.addNutritionFraction(2, 1.0f);
		oFood.setAmount(3.0f);				
		assertEquals(oFood.getNutritionAmount(1), 2.0f, 0.000001f);
		oFood.setAmount(6.0f);		
		assertEquals(oFood.getNutritionAmount(1), 4.0f, 0.000001f);
		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getAmount()}.
	 */
	@Test
	public void testGetAmount() {
		clsFood oFood = new clsFood();
		assertEquals(oFood.getAmount(), 0.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmount(int)}.
	 */
	@Test
	public void testGetNutritionAmountInt() {
		clsFood oFood = new clsFood();
		oFood.setAmount(1.5f);
		oFood.addNutritionFraction(1, 2.0f);
		oFood.addNutritionFraction(2, 1.0f);
		assertEquals(oFood.getNutritionAmount(1), 1.0f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmount(java.lang.Integer)}.
	 */
	@Test
	public void testGetNutritionAmountInteger() {
		clsFood oFood = new clsFood();
		oFood.setAmount(1.5f);
		oFood.addNutritionFraction(new Integer(1), new Float(2.0f));
		oFood.addNutritionFraction(new Integer(2), new Float(1.0f));
		assertEquals(oFood.getNutritionAmount(new Integer(1)), 1.0f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritonAmounts()}.
	 */
	@Test
	public void testGetNutritonAmounts() {
		clsFood oFood = new clsFood();
		oFood.setAmount(1.0f);
		oFood.addNutritionFraction(1, 2.0f);
		oFood.addNutritionFraction(2, 1.5f);		
		oFood.addNutritionFraction(3, 1.0f);		
		oFood.addNutritionFraction(4, 0.5f);
		
		HashMap<Integer, clsMutableFloat> oTemp = oFood.getNutritonAmounts();
		assertNotNull(oTemp);
		assertEquals(oTemp.get(new Integer(2)).floatValue(), 0.3f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#addFood(bw.utils.tools.clsFood)}.
	 */
	@Test
	public void testAddFood() {
		clsFood oFood = new clsFood();
		oFood.setAmount(1.0f);
		oFood.addNutritionFraction(1, 2.0f);
		oFood.addNutritionFraction(2, 1.5f);		
		oFood.addNutritionFraction(3, 1.0f);		
		oFood.addNutritionFraction(4, 0.5f);	
		
		clsFood oFood2 = new clsFood();
		oFood2.setAmount(2.0f);
		oFood2.addNutritionFraction(1, 1.0f);
		oFood2.addNutritionFraction(5, 1.0f);
		
		oFood.addFood(oFood2);
		assertEquals(oFood.getAmount(), 3.0f);
		assertEquals(oFood.getNutritionAmount(1), 4.0f, 0.000001f);
		assertEquals(oFood.getNutritionAmount(2), 1.5f, 0.000001f);
		assertEquals(oFood.getNutritionAmount(3), 1.0f, 0.000001f);
		assertEquals(oFood.getNutritionAmount(4), 0.5f, 0.000001f);
		assertEquals(oFood.getNutritionAmount(5), 2.0f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#addNutritionFraction(int, float)}.
	 */
	@Test
	public void testAddNutritionFractionIntFloat() {
		clsFood oFood = new clsFood();
		oFood.setAmount(1.0f);
		oFood.addNutritionFraction(1, 2.0f);
		oFood.addNutritionFraction(2, 1.5f);		
		oFood.addNutritionFraction(3, 1.0f);		
		oFood.addNutritionFraction(4, 0.5f);		
		assertEquals(oFood.getNutritionAmount(2), 0.3f, 0.000001f);
		oFood.addNutritionFraction(2, 0.5f);		
		assertEquals(oFood.getNutritionAmount(2), 0.125f, 0.000001f);
		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#addNutritionFraction(java.lang.Integer, bw.utils.datatypes.clsMutableFloat)}.
	 */
	@Test
	public void testAddNutritionFractionIntegerClsMutableFloat() {
		clsFood oFood = new clsFood();
		oFood.addNutritionFraction(new Integer(1), new clsMutableFloat(1.10f));
		assertEquals(oFood.getNutritionAmount(1), 1.10f, 0.000001f);
	}

}
