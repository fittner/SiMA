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

import bw.exceptions.FoodAlreadyNormalized;
import bw.exceptions.FoodNotFinalized;
import bw.utils.datatypes.clsMutableFloat;
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
			float rTemp = oFood.getNutritionAmount(1);
			fail("Food not finalised, but exception FoodNotFinalized not thrown.");
		} catch (FoodNotFinalized e1) {
			//thrown exception expected 
		}
		
		try {
			oFood.addNutritionFraction(1, 1.0f);
		} catch (FoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			@SuppressWarnings("unused")
			float rTemp = oFood.getNutritionAmount(1);
			fail("Food not finalised, but exception FoodNotFinalized not thrown.");
		} catch (FoodNotFinalized e1) {
			//thrown exception expected 
		}
		
		try {
			oFood.finalize();
		} catch (FoodAlreadyNormalized e) {
			fail("Food not finalised, but exception FoodAlreadyNormalized is thrown.");
		}
		
		try {
			@SuppressWarnings("unused")
			float rTemp = oFood.getNutritionAmount(1);
		} catch (FoodNotFinalized e1) {
			fail("Food finalised, but exception FoodNotFinalized still thrown.");
		}		
		
		try {
			oFood.finalize();
			fail("Food finalised, but exception FoodAlreadyNormalized is not thrown.");
		} catch (FoodAlreadyNormalized e) {
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
		oFood.setAmount(2.5f);
		assertEquals(oFood.getAmount(), 2.5f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.utils.tools.clsFood#getNutritionAmount(int)}.
	 */
	@Test
	public void testGetNutritionAmountInt() {
		clsFood oFood = null;
		
		oFood = new clsFood();
		oFood.setAmount(1.0f);

		try {
			oFood.addNutritionFraction(1, 1.0f);
			oFood.addNutritionFraction(2, 2.0f);
		} catch (FoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (FoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionAmount(1), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionAmount(2), 0.6666f, 0.01f);
		} catch (FoodNotFinalized e) {
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
		oFood.setAmount(1.0f);

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (FoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (FoodAlreadyNormalized e) {
		}
		
		try {
			assertEquals(oFood.getNutritionAmount(new Integer(1)), 0.3333f, 0.01f);
			assertEquals(oFood.getNutritionAmount(new Integer(2)), 0.6666f, 0.01f);
		} catch (FoodNotFinalized e) {
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
		oFood.setAmount(1.0f);

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (FoodAlreadyNormalized e) {
		}
		
		try {
			oFood.finalize();
		} catch (FoodAlreadyNormalized e) {
		}
		
		HashMap<java.lang.Integer,clsMutableFloat> oMap = null;
		
		try {
			oMap = oFood.getNutritionAmounts();
		} catch (FoodNotFinalized e) {
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
		oFood.setAmount(1.0f);

		try {
			oFood.addNutritionFraction(new Integer(1), 1.0f);
			oFood.addNutritionFraction(new Integer(2), 2.0f);
		} catch (FoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		}
		
		try {
			oFood.finalize();
			assertEquals(oFood.getNutritionAmount(1), 0.33f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(2), 0.66f, 0.01f);	
		} catch (FoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		} catch (FoodNotFinalized e) {
			fail("FoodNotFinalized");			
		}
		
		clsFood oFood2 = null;
		
		oFood2 = new clsFood();
		oFood2.setAmount(1.5f);

		try {
			oFood2.addNutritionFraction(new Integer(2), 2.0f);
			oFood2.addNutritionFraction(new Integer(3), 1.0f);
			oFood2.finalize();			
			assertEquals(oFood2.getNutritionAmount(2), 1.0f, 0.01f);		
			assertEquals(oFood2.getNutritionAmount(3), 0.5f, 0.01f);				
		} catch (FoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");
		} catch (FoodNotFinalized e) {
			fail("FoodNotFinalized");			
		}
		
		try {
			oFood.addFood(oFood2);
		} catch (FoodNotFinalized e) {
			fail("FoodNotFinalized");
		} catch (FoodAlreadyNormalized e) {
			fail("FoodAlreadyNormalized");			
		}
		
		try {
			assertEquals(oFood.getAmount(), 2.5f, 0.00001f);
			assertEquals(oFood.getNutritionAmount(1), 0.3333f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(2), 1.6666f, 0.01f);		
			assertEquals(oFood.getNutritionAmount(3), 0.5f, 0.000001f);
			
			
		} catch (FoodNotFinalized e) {
			fail("FoodNotFinalized");
		}
	}



}
