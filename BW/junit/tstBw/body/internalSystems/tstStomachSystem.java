/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.body.internalSystems;

import static org.junit.Assert.*;

import bw.body.internalSystems.clsStomachSystem;
import bw.exceptions.exNoSuchNutritionType;

import org.junit.Test;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstStomachSystem {

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#clsStomachSystem()}.
	 */
	@Test
	public void testClsStomachSystem() {
		clsStomachSystem oSS = new clsStomachSystem();
		
		assertNotNull(oSS);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#addNutritionType(java.lang.Integer)}.
	 */
	@Test
	public void testAddNutritionType() {
		clsStomachSystem oSS = new clsStomachSystem();

		assertNull(oSS.getNutritionLevel(1));
		
		oSS.addNutritionType(1);
		
		assertNotNull(oSS.getNutritionLevel(1));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#removeNutritionType(java.lang.Integer)}.
	 */
	@Test
	public void testRemoveNutritionType() {
		clsStomachSystem oSS = new clsStomachSystem();

		assertNull(oSS.getNutritionLevel(1));
		
		oSS.addNutritionType(1);
		
		assertNotNull(oSS.getNutritionLevel(1));
		
		oSS.removeNutritionType(1);

		assertNull(oSS.getNutritionLevel(1));
		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#getNutrition(java.lang.Integer)}.
	 */
	@Test
	public void testGetNutritionLevel() {
		clsStomachSystem oSS = new clsStomachSystem();

		oSS.addNutritionType(1);
		
		assertNotNull(oSS.getNutritionLevel(1));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#addNutrition(java.lang.Integer, float)}.
	 */
	@Test
	public void testAddNutrition() {
		clsStomachSystem oSS = new clsStomachSystem();
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.0f, 0.000001f);
		
		try {
			oSS.addNutrition(2, 1.0f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.0f, 0.000001f);
		
		try {
			oSS.addNutrition(4, 2.0f);
			fail("NoSuchNutritionType exception not thrown");
		} catch (exNoSuchNutritionType e) {
			//expected exception
		}
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.0f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#withdrawNutrition(java.lang.Integer, float)}.
	 */
	@Test
	public void testWithdrawNutrition() {
		clsStomachSystem oSS = new clsStomachSystem();
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		try {
			oSS.addNutrition(1, 1.0f);
			oSS.addNutrition(2, 1.0f);
			oSS.addNutrition(3, 1.0f);			
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}

			
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
		
		try {
			oSS.withdrawNutrition(2, 0.5f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.5f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);

		try {
			oSS.withdrawNutrition(4, 0.25f);
			fail("NoSuchNutritionType");			
		} catch (exNoSuchNutritionType e) {
			//expected exception
		}
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.5f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
		
		try {
			oSS.withdrawNutrition(1, 1.5f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.5f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#addEnergy(float)}.
	 */
	@Test
	public void testAddEnergy() {
		clsStomachSystem oSS = new clsStomachSystem();
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		oSS.addEnergy(3.0f);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
		
		try {
			oSS.withdrawNutrition(2, 0.5f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		oSS.addEnergy(0.6f);

		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.2f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.7f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.2f, 0.000001f);
		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#withdrawEnergy(float)}.
	 */
	@Test
	public void testWithdrawEnergy() {
		clsStomachSystem oSS = new clsStomachSystem();
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		try {
			oSS.addNutrition(1, 1.0f);
			oSS.addNutrition(2, 1.0f);
			oSS.addNutrition(3, 1.0f);			
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}

			
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
		
		oSS.withdrawEnergy(0.6f);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.8f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.8f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.8f, 0.000001f);
		
		try {
			oSS.addNutrition(2, 0.2f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		oSS.withdrawEnergy(0.3f);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.7f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.9f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.7f, 0.000001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#step()}.
	 */
	@Test
	public void testStep() {
		clsStomachSystem oSS = new clsStomachSystem();
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		oSS.addEnergy(3.0f);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
	
		oSS.step();
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.9f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.9f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.9f, 0.000001f);

		try {
			oSS.addNutrition(2, 0.1f);
		} catch (exNoSuchNutritionType e) {
			fail("NoSuchNutritionType");
		}
		
		oSS.step();
		oSS.step();
		oSS.step();
		oSS.step();
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 0.5f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 0.6f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 0.5f, 0.000001f);		
		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsStomachSystem#getEnergy()}.
	 */
	@Test
	public void testGetEnergy() {
		clsStomachSystem oSS = new clsStomachSystem();
		
		assertEquals(oSS.getEnergy(), 0.0f, 0.000001f);
		
		oSS.addNutritionType(1);
		oSS.addNutritionType(2);
		oSS.addNutritionType(3);
		
		oSS.addEnergy(3.0f);
		
		assertEquals(oSS.getNutritionLevel(1).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(2).getContent(), 1.0f, 0.000001f);
		assertEquals(oSS.getNutritionLevel(3).getContent(), 1.0f, 0.000001f);
		
		assertEquals(oSS.getEnergy(), 3.0f, 0.000001f);
		
		oSS.withdrawEnergy(0.5f);
		
		assertEquals(oSS.getEnergy(), 2.5f, 0.000001f);
		
		oSS.withdrawEnergy(2.5f);
		assertEquals(oSS.getEnergy(), 0.0f, 0.000001f);
		oSS.addEnergy(3.33f);
		assertEquals(oSS.getEnergy(), 3.33f, 0.000001f);
		oSS.withdrawEnergy(3.33f);
		assertEquals(oSS.getEnergy(), 0.0f, 0.000001f);
		
	}

}
