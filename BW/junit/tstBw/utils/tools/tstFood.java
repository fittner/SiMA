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
import bw.exceptions.exFoodWeightBelowZero;
import bw.exceptions.exFoodNotFinalized;
import bw.utils.config.clsBWProperties;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFood;

import org.junit.Test;

/**
 * DOCUMENT (deutsch) - insert description 
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
			double rTemp = oFood.getNutritionWeight(eNutritions.CARBOHYDRATE);
			fail("Food not finalised, but exception FoodNotFinalized not thrown.");
		} catch (exFoodNotFinalized e1) {
			//thrown exception expected 
		}
		
		try {
			oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0) );
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			@SuppressWarnings("unused")
			double rTemp = oFood.getNutritionWeight(eNutritions.CARBOHYDRATE);
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
			double rTemp = oFood.getNutritionWeight(eNutritions.CARBOHYDRATE);
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

	@Test 
	public void testPropertyConstructor() {
		clsBWProperties oProp = clsFood.getDefaultProperties("");
		clsFood oFood = null;
	
		oFood = new clsFood("", oProp);
	
		assertNotNull(oFood);
		assertEquals(oFood.getWeight(), 5.0, 0.000001);

		double weight = 0;
		
		try {
			weight = oFood.getNutritionWeight(eNutritions.PROTEIN);
			assertEquals(0.1, weight, 0.0000001);

			weight = oFood.getNutritionWeight(eNutritions.FAT);
			assertEquals(1.0, weight, 0.0000001);
			
			weight = oFood.getNutritionWeight(eNutritions.VITAMIN);
			assertEquals(0.1, weight, 0.0000001);
			
			weight = oFood.getNutritionWeight(eNutritions.CARBOHYDRATE);
			assertEquals(1.0, weight, 0.0000001);
			
			weight = oFood.getNutritionWeight(eNutritions.WATER);
			assertEquals(2.0, weight, 0.0000001);
			
			weight = oFood.getNutritionWeight(eNutritions.UNDIGESTABLE);
			assertEquals(0.8, weight, 0.0000001);
			
			try {
			weight = oFood.getNutritionWeight(eNutritions.MINERAL);
			 	fail("eNutritons.MINERAL does not exist");
			} catch (java.lang.NullPointerException e) {
				// wanted
			}
			
		} catch (exFoodNotFinalized e) {
			fail(e.toString());
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
	 * Test method for {@link bw.utils.tools.clsFood#setWeight(float)}.
	 */
	@Test
	public void testSetAmount() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		assertEquals(oFood.getWeight(), 0.0f, 0.00001f);
		try {
			oFood.setWeight(2.5f);
		} catch (exFoodWeightBelowZero e) {
			e.printStackTrace();
		}
		assertEquals(oFood.getWeight(), 2.5f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionWeight(int)}.
	 */
	@Test
	public void testGetNutritionAmountInt() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setWeight(1.0f);
		} catch (exFoodWeightBelowZero e1) {
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0f));
			oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(2.0f));
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionWeight(eNutritions.CARBOHYDRATE), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionWeight(eNutritions.FAT), 0.6666f, 0.01f);
		} catch (exFoodNotFinalized e) {
		}
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionWeight(java.lang.Integer)}.
	 */
	@Test
	public void testGetNutritionAmountInteger() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setWeight(1.0f);
		} catch (exFoodWeightBelowZero e1) {
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0f));
			oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(2.0f));
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionWeight(eNutritions.CARBOHYDRATE), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionWeight(eNutritions.FAT), 0.6666f, 0.01f);
		} catch (exFoodNotFinalized e) {
		}
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionWeights()}.
	 */
	@Test
	public void testGetNutritionAmounts() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		try {
			oFood.setWeight(1.0f);
		} catch (exFoodWeightBelowZero e1) {
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0f));
			oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(2.0f));
		} catch (exFoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
		}
		
		HashMap<eNutritions,clsMutableDouble> oMap = null;
		
		try {
			oMap = oFood.getNutritionWeights();
		} catch (exFoodNotFinalized e) {
			fail("Food not finalised");
		}
		
		assertTrue(oMap.containsKey(eNutritions.CARBOHYDRATE));
		assertTrue(oMap.containsKey(eNutritions.FAT));
		assertFalse(oMap.containsKey(eNutritions.MINERAL));
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
			oFood.setWeight(1.0f);
		} catch (exFoodWeightBelowZero e1) {
			e1.printStackTrace();
		}

		try {
			oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0f));
			oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(2.0f));
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			oFood.finalize();
			assertEquals(oFood.getNutritionWeight(eNutritions.CARBOHYDRATE), 0.33f, 0.01f);		
			assertEquals(oFood.getNutritionWeight(eNutritions.FAT), 0.66f, 0.01f);	
		} catch (exFoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");			
		}
		
		clsFood oFood2 = null;
		
		oFood2 = new clsFood();
		try {
			oFood2.setWeight(1.5f);
		} catch (exFoodWeightBelowZero e1) {
			e1.printStackTrace();
		}

		try {
			oFood2.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(2.0f));
			oFood2.addNutritionFraction(eNutritions.MINERAL, new clsMutableDouble(1.0f));
			oFood2.finalize();			
			assertEquals(oFood2.getNutritionWeight(eNutritions.FAT), 1.0f, 0.01f);		
			assertEquals(oFood2.getNutritionWeight(eNutritions.MINERAL), 0.5f, 0.01f);				
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
			assertEquals(oFood.getWeight(), 2.5f, 0.00001f);
			assertEquals(oFood.getNutritionWeight(eNutritions.CARBOHYDRATE), 0.3333f, 0.01f);		
			assertEquals(oFood.getNutritionWeight(eNutritions.FAT), 1.6666f, 0.01f);		
			assertEquals(oFood.getNutritionWeight(eNutritions.MINERAL), 0.5f, 0.000001f);
			
			
		} catch (exFoodNotFinalized e) {
			fail("FoodNotFinalized");
		}
	}



}
