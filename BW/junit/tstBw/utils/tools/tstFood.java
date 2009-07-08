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

import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodAmountBelowZero;
import bw.exceptions.exFoodNotFinalized;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.tools.clsFood;
import org.junit.Test;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstFood {

	/**
	 * Test method for {@link bw.utils.tools.clsFood#finalize()}.
	 */
	@Test
	public void testFinalize() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		
		try {
			@SuppressWarnings("unused")
			double rTemp = oFood.getNutritionAmount(1);
			fail("Food not finalised, but exception FoodNotFinalized not thrown.");
		} catch (exFoodNotFinalized e1) {
			//thrown exception expected 
		}
		
		try {
			oFood.addNutritionFraction(1, 1.0f);
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			@SuppressWarnings("unused")
			double rTemp = oFood.getNutritionAmount(1);
			fail("Food not finalised, but exception FoodNotFinalized not thrown.");
		} catch (exFoodNotFinalized e1) {
			//thrown exception expected 
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
			fail("Food not finalised, but exception FoodAlreadyNormalized is thrown.");
		}
		
		try {
			@SuppressWarnings("unused")
			double rTemp = oFood.getNutritionAmount(1);
		} catch (exFoodNotFinalized e1) {
			fail("Food finalised, but exception FoodNotFinalized still thrown.");
		}		
		
		try {
			oFood.finalize();
			fail("Food finalised, but exception FoodAlreadyNormalized is not thrown.");
		} catch (exFoodAlreadyNormalized e) {
			//thrown exception expected 
		}
		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#clsFood()}.
	 */
	@Test
	public void testClsFood() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		
		assertNotNull(oFood);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#setAmount(float)}.
	 */
	@Test
	public void testSetAmount() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		assertEquals(oFood.getAmount(), 0.0f, 0.00001f);
		try {
			oFood.setAmount(2.5f);
		} catch (exFoodAmountBelowZero e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oFood.getAmount(), 2.5f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmount(int)}.
	 */
	@Test
	public void testGetNutritionAmountInt() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setAmount(1.0f);
		} catch (exFoodAmountBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(1, 1.0f);
			oFood.addNutritionFraction(2, 2.0f);
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionAmount(1), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionAmount(2), 0.6666f, 0.01f);
		} catch (exFoodNotFinalized e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmount(java.lang.Integer)}.
	 */
	@Test
	public void testGetNutritionAmountInteger() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setAmount(1.0f);
		} catch (exFoodAmountBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionAmount(new Integer(1)), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionAmount(new Integer(2)), 0.6666f, 0.01f);
		} catch (exFoodNotFinalized e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmounts()}.
	 */
	@Test
	public void testGetNutritionAmounts() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setAmount(1.0f);
		} catch (exFoodAmountBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		HashMap<java.lang.Integer,clsMutableDouble> oMap = null;
		
		try {
			oMap = oFood.getNutritionAmounts();
		} catch (exFoodNotFinalized e) {
			fail("Food not finalised");
		}
		
		assertTrue(oMap.containsKey(new Integer(1)));
		assertTrue(oMap.containsKey(new Integer(2)));
		assertFalse(oMap.containsKey(new Integer(0)));
		assertEquals(oMap.size(), 2);
		
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#addFood(bw.utils.tools.clsFood)}.
	 */
	@Test
	public void testAddFood() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setAmount(1.0f);
		} catch (exFoodAmountBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			oFood.finalize();
			assertEquals(oFood.getNutritionAmount(1), 0.33f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(2), 0.66f, 0.01f);	
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");			
		}
		
		clsFood oFood2 = null;
		
		oFood2 = new clsFood();
		try {
			oFood2.setAmount(1.5f);
		} catch (exFoodAmountBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			oFood2.addNutritionFraction(new Integer(2), 2.0f);
			oFood2.addNutritionFraction(new Integer(3), 1.0f);
			oFood2.finalize();			
			assertEquals(oFood2.getNutritionAmount(2), 1.0f, 0.01f);		
			assertEquals(oFood2.getNutritionAmount(3), 0.5f, 0.01f);				
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");			
		}
		
		try {
			oFood.addFood(oFood2);
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");			
		}
		
		try {
			assertEquals(oFood.getAmount(), 2.5f, 0.00001f);
			assertEquals(oFood.getNutritionAmount(1), 0.3333f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(2), 1.6666f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(3), 0.5f, 0.000001f);
			
			
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");
		}
	}



}
