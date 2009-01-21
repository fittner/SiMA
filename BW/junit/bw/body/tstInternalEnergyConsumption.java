/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import bw.body.interalSystems.clsInternalEnergyConsumption;
import bw.utils.datatypes.clsMutableInteger;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstInternalEnergyConsumption {



	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#clsInternalEnergyConsumption()}.
	 */
	@Test
	public void testClsInternalEnergyConsumption() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertNotNull(moIEC);
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#getList()}.
	 */
	@Test
	public void testGetList() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
	
		moIEC.setValue(1, 1);
		moIEC.setValue(2, 2);
		moIEC.setValue(3, 3);
		moIEC.setValue(4, 4);
		moIEC.setValue(5, 5);
		
		HashMap<Integer, clsMutableInteger> moTemp = moIEC.getList();
		
		assertNotNull(moTemp);
		
		assertSame(moTemp.size(), 5);

		moIEC.setValue(6, 6);
		
		assertSame(moTemp.size(), 5);
	}
	

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#setValue(int, int)}.
	 */
	@Test
	public void testSetValueIntInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
		
		moIEC.setValue(1,2);
		
		clsMutableInteger oTemp;
		
		oTemp = moIEC.getValue(1);	
		assertNotNull(oTemp);
		assertSame(oTemp.intValue(), 2);
		
		moIEC.setValue(1,3);

		oTemp = moIEC.getValue(1);	
		assertNotNull(oTemp);
		assertSame(oTemp.intValue(), 3);
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#hasChanged()}.
	 */
	@Test
	public void testHasChanged() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		int nTemp;

		assertTrue(moIEC.hasChanged());
		
		nTemp = moIEC.getSum();
		
		assertSame(nTemp, 0);
		
		assertFalse(moIEC.hasChanged());
		
		moIEC.setValue(1,2);

		assertTrue(moIEC.hasChanged());
		
		nTemp = moIEC.getSum();
		
		assertSame(nTemp, 2);
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#setValue(java.lang.Integer, int)}.
	 */
	@Test
	public void testSetValueIntegerInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();
		
		Integer oKey = new Integer(1);
		
		moIEC.setValue(oKey,2);
		
		clsMutableInteger oTemp;
		
		oTemp = moIEC.getValue(oKey);
		assertNotNull(oTemp);
		assertSame(oTemp.intValue(), 2);
		
		moIEC.setValue(oKey,3);

		oTemp = moIEC.getValue(oKey);	
		assertNotNull(oTemp);
		assertSame(oTemp.intValue(), 3);		
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#keyExists(int)}.
	 */
	@Test
	public void testKeyExistsInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertFalse(moIEC.keyExists(0));
		
		moIEC.setValue(0, 1);
		
		assertTrue(moIEC.keyExists(0));
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#keyExists(java.lang.Integer)}.
	 */
	@Test
	public void testKeyExistsInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		Integer oKey = new Integer(1);
		
		assertFalse(moIEC.keyExists(oKey));
		
		moIEC.setValue(oKey, 1);
		
		assertTrue(moIEC.keyExists(oKey));
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#getValue(int)}.
	 */
	@Test
	public void testGetValueInt() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertNull(moIEC.getValue(1));
		
		moIEC.setValue(1, 2);
		
		assertNotNull(moIEC.getValue(1));
		
		assertSame(moIEC.getValue(1).intValue(), 2);
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#getValue(java.lang.Integer)}.
	 */
	@Test
	public void testGetValueInteger() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		Integer oKey = new Integer(1);
		
		assertNull(moIEC.getValue(oKey));
		
		moIEC.setValue(oKey, 2);
		
		assertNotNull(moIEC.getValue(oKey));		
		
		assertSame(moIEC.getValue(oKey).intValue(), 2);
	}

	/**
	 * Test method for {@link bw.body.interalSystems.clsInternalEnergyConsumption#getSum()}.
	 */
	@Test
	public void testGetSum() {
		clsInternalEnergyConsumption moIEC = new clsInternalEnergyConsumption();

		assertSame(moIEC.getSum(), 0);
		
		moIEC.setValue(1, 1);
		moIEC.setValue(2, 2);
		moIEC.setValue(3, 3);
		moIEC.setValue(4, 4);
		moIEC.setValue(5, 5);
		
		assertSame(moIEC.getSum(), 15);
		
		moIEC.setValue(5, 0);
		
		assertSame(moIEC.getSum(), 10);
	}

}
