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

import config.clsProperties;

import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eBodyParts;

/**
 * 
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
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		assertNotNull(moIEC);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getList()}.
	 */
	@Test
	public void testGetList() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());
	
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(1.0f));
		moIEC.setValue(eBodyParts.INTRA, new clsMutableDouble(2.0f));
		moIEC.setValue(eBodyParts.INTER, new clsMutableDouble(3.0f));
		moIEC.setValue(eBodyParts.EXTERNAL_IO, new clsMutableDouble(4.0f));
		moIEC.setValue(eBodyParts.INTERNAL_IO, new clsMutableDouble(5.0f));
		
		HashMap<eBodyParts, clsMutableDouble> moTemp = moIEC.getMergedList();
		
		assertNotNull(moTemp);
		
		assertEquals(moTemp.size(), 5.0f, 0.00001f);

		moIEC.setValue(eBodyParts.SENSOR_EXT, new clsMutableDouble(6.0f));
		
		assertEquals(moTemp.size(), 5.0f, 0.00001f);
		
		HashMap<eBodyParts, clsMutableDouble> moTemp2 = moIEC.getMergedList();
		
		assertNotNull(moTemp2);
		
		assertNotSame(moTemp, moTemp2);

	}
	

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#setValue(int, int)}.
	 */
	@Test
	public void testSetValueIntInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());
		
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(2.0f));
		
		clsMutableDouble oTemp;
		
		oTemp = moIEC.getValue(eBodyParts.INTSYS);	
		assertNotNull(oTemp);
		assertEquals(oTemp.doubleValue(), 2.0f, 0.00001f);
		
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(3.0f));

		oTemp = moIEC.getValue(eBodyParts.INTSYS);	
		assertNotNull(oTemp);
		assertEquals(oTemp.doubleValue(), 3.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#hasChanged()}.
	 */
	@Test
	public void testHasChanged() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		double rTemp;

		assertTrue(moIEC.hasChanged());
		
		rTemp = moIEC.getSum();
		
		assertEquals(rTemp, 0.0f, 0.00001f);
		
		assertFalse(moIEC.hasChanged());
		
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(2.0f));

		assertTrue(moIEC.hasChanged());
		
		rTemp = moIEC.getSum();
		
		assertEquals(rTemp, 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#setValue(java.lang.Integer, int)}.
	 */
	@Test
	public void testSetValueIntegerInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());
		
		eBodyParts oKey = eBodyParts.INTSYS;
		
		moIEC.setValue(oKey, new clsMutableDouble(2.0f));
		
		clsMutableDouble oTemp;
		
		oTemp = moIEC.getValue(oKey);
		assertNotNull(oTemp);
		assertEquals(oTemp.doubleValue(), 2.0f, 0.00001f);
		
		moIEC.setValue(oKey, new clsMutableDouble(3.0f));

		oTemp = moIEC.getValue(oKey);	
		assertNotNull(oTemp);
		assertEquals(oTemp.doubleValue(), 3.0f, 0.00001f);		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#keyExists(int)}.
	 */
	@Test
	public void testKeyExistsInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		assertFalse(moIEC.keyExists(eBodyParts.INTSYS));
		
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(1.0f));
		
		assertTrue(moIEC.keyExists(eBodyParts.INTSYS));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#keyExists(java.lang.Integer)}.
	 */
	@Test
	public void testKeyExistsInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		eBodyParts oKey = eBodyParts.INTSYS;
		
		assertFalse(moIEC.keyExists(oKey));
		
		moIEC.setValue(oKey, new clsMutableDouble(1.0f));
		
		assertTrue(moIEC.keyExists(oKey));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getValue(int)}.
	 */
	@Test
	public void testGetValueInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		assertNull(moIEC.getValue(eBodyParts.INTSYS));
		
		moIEC.setValue(eBodyParts.INTSYS, new clsMutableDouble(2.0f));
		
		assertNotNull(moIEC.getValue(eBodyParts.INTSYS));
		
		assertEquals(moIEC.getValue(eBodyParts.INTSYS).doubleValue(), 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getValue(java.lang.Integer)}.
	 */
	@Test
	public void testGetValueInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		eBodyParts oKey = eBodyParts.INTSYS;
		
		assertNull(moIEC.getValue(oKey));
		
		moIEC.setValue(oKey, new clsMutableDouble(2.0f));
		
		assertNotNull(moIEC.getValue(oKey));		
		
		assertEquals(moIEC.getValue(oKey).doubleValue(), 2.0f, 0.00001f);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsInternalEnergyConsumption#getSum()}.
	 */
	@Test
	public void testGetSum() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption("", new clsProperties());

		assertEquals(moIEC.getSum(), 0.0f, 0.00001f);
		
		moIEC.setValue(eBodyParts.INTSYS , new clsMutableDouble(1.0f));
		moIEC.setValue(eBodyParts.INTRA, new clsMutableDouble(2.0f));
		moIEC.setValue(eBodyParts.INTER, new clsMutableDouble(3.0f));
		moIEC.setValue(eBodyParts.EXTERNAL_IO, new clsMutableDouble(4.0f));
		moIEC.setValue(eBodyParts.INTERNAL_IO, new clsMutableDouble(5.0f));
		
		assertEquals(moIEC.getSum(), 15.0f, 0.00001f);
		
		moIEC.setValue(eBodyParts.INTERNAL_IO, new clsMutableDouble(0.0f));
		
		assertEquals(moIEC.getSum(), 10.0f, 0.00001f);
	}

}
