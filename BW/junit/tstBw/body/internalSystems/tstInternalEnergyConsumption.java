/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.body.internalSystems;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.utils.datatypes.clsMutableFloat;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstInternalEnergyConsumption {



	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#clsInternalEnergyConsumption()}.
	 */
	@Test
	public void testClsInternalEnergyConsumption() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertNotNull(moIEC);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getList()}.
	 */
	@Test
	public void testGetList() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
	
		moIEC.setValue(new Integer(1), new clsMutableFloat(1.0f));
		moIEC.setValue(new Integer(2), new clsMutableFloat(2.0f));
		moIEC.setValue(new Integer(3), new clsMutableFloat(3.0f));
		moIEC.setValue(new Integer(4), new clsMutableFloat(4.0f));
		moIEC.setValue(new Integer(5), new clsMutableFloat(5.0f));
		
		HashMap<Integer, clsMutableFloat> moTemp = moIEC.getMergedList();
		
		assertNotNull(moTemp);
		
		assertEquals(moTemp.size(), 5.0f, 0.00001f);

		moIEC.setValue(new Integer(6), new clsMutableFloat(6.0f));
		
		assertEquals(moTemp.size(), 5.0f, 0.00001f);
		
		HashMap<Integer, clsMutableFloat> moTemp2 = moIEC.getMergedList();
		
		assertNotNull(moTemp2);
		
		assertNotSame(moTemp, moTemp2);

	}
	

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#setValue(int, int)}.
	 */
	@Test
	public void testSetValueIntInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
		
		moIEC.setValue(1, new clsMutableFloat(2.0f));
		
		clsMutableFloat oTemp;
		
		oTemp = moIEC.getValue(1);	
		assertNotNull(oTemp);
		assertEquals(oTemp.floatValue(), 2.0f, 0.00001f);
		
		moIEC.setValue(1, new clsMutableFloat(3.0f));

		oTemp = moIEC.getValue(1);	
		assertNotNull(oTemp);
		assertEquals(oTemp.floatValue(), 3.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#hasChanged()}.
	 */
	@Test
	public void testHasChanged() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		float rTemp;

		assertTrue(moIEC.hasChanged());
		
		rTemp = moIEC.getSum();
		
		assertEquals(rTemp, 0.0f, 0.00001f);
		
		assertFalse(moIEC.hasChanged());
		
		moIEC.setValue(1, new clsMutableFloat(2.0f));

		assertTrue(moIEC.hasChanged());
		
		rTemp = moIEC.getSum();
		
		assertEquals(rTemp, 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#setValue(java.lang.Integer, int)}.
	 */
	@Test
	public void testSetValueIntegerInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
		
		Integer oKey = new Integer(1);
		
		moIEC.setValue(oKey, new clsMutableFloat(2.0f));
		
		clsMutableFloat oTemp;
		
		oTemp = moIEC.getValue(oKey);
		assertNotNull(oTemp);
		assertEquals(oTemp.floatValue(), 2.0f, 0.00001f);
		
		moIEC.setValue(oKey, new clsMutableFloat(3.0f));

		oTemp = moIEC.getValue(oKey);	
		assertNotNull(oTemp);
		assertEquals(oTemp.floatValue(), 3.0f, 0.00001f);		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#keyExists(int)}.
	 */
	@Test
	public void testKeyExistsInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertFalse(moIEC.keyExists(0));
		
		moIEC.setValue(0, new clsMutableFloat(1.0f));
		
		assertTrue(moIEC.keyExists(0));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#keyExists(java.lang.Integer)}.
	 */
	@Test
	public void testKeyExistsInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		Integer oKey = new Integer(1);
		
		assertFalse(moIEC.keyExists(oKey));
		
		moIEC.setValue(oKey, new clsMutableFloat(1.0f));
		
		assertTrue(moIEC.keyExists(oKey));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getValue(int)}.
	 */
	@Test
	public void testGetValueInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertNull(moIEC.getValue(1));
		
		moIEC.setValue(1, new clsMutableFloat(2.0f));
		
		assertNotNull(moIEC.getValue(1));
		
		assertEquals(moIEC.getValue(1).floatValue(), 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getValue(java.lang.Integer)}.
	 */
	@Test
	public void testGetValueInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		Integer oKey = new Integer(1);
		
		assertNull(moIEC.getValue(oKey));
		
		moIEC.setValue(oKey, new clsMutableFloat(2.0f));
		
		assertNotNull(moIEC.getValue(oKey));		
		
		assertEquals(moIEC.getValue(oKey).floatValue(), 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getSum()}.
	 */
	@Test
	public void testGetSum() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertEquals(moIEC.getSum(), 0.0f, 0.00001f);
		
		moIEC.setValue(1, new clsMutableFloat(1.0f));
		moIEC.setValue(2, new clsMutableFloat(2.0f));
		moIEC.setValue(3, new clsMutableFloat(3.0f));
		moIEC.setValue(4, new clsMutableFloat(4.0f));
		moIEC.setValue(5, new clsMutableFloat(5.0f));
		
		assertEquals(moIEC.getSum(), 15.0f, 0.00001f);
		
		moIEC.setValue(5, new clsMutableFloat(0.0f));
		
		assertEquals(moIEC.getSum(), 10.0f, 0.00001f);
	}

}
