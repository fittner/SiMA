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

import bw.body.internalSystems.clsSlowMessengerSystem;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.exceptions.exSlowMessengerAlreadyExists;
import bw.exceptions.exSlowMessengerDoesNotExist;
import bw.exceptions.exValueNotWithinRange;
import bw.utils.tools.clsDecayColumn;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class tstSlowMessengerSystem {

	/**
	 * Test method for {@link bw.body.internalSystems.clsSlowMessengerSystem#clsSlowMessengerSystem()}.
	 */
	@Test
	public void testClsSlowMessengerSystem() {
		clsSlowMessengerSystem oSMS = new clsSlowMessengerSystem(null);
		
		assertNotNull(oSMS);		
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsSlowMessengerSystem#addSlowMessenger(int)}.
	 */
	@Test
	public void testAddSlowMessengerInt() {
		clsSlowMessengerSystem oSMS = new clsSlowMessengerSystem(null);
		clsDecayColumn oDC = null;
		
		try {
			oDC = oSMS.addSlowMessenger(1);
		} catch (exSlowMessengerAlreadyExists e) {
			fail("SlowMessengerAlreadyExists");
		} catch (exContentColumnMaxContentExceeded e) {
			fail("ContentColumnMaxContentExceeded");
		} catch (exContentColumnMinContentUnderrun e) {
			fail("ContentColumnMinContentUnderrun");
		} catch (exValueNotWithinRange e) {
			fail("ValueNotWithinRange");
		}
		
		try {
			oDC = oSMS.addSlowMessenger(new Integer(2));
		} catch (exSlowMessengerAlreadyExists e) {
			fail("SlowMessengerAlreadyExists");
		} catch (exContentColumnMaxContentExceeded e) {
			fail("ContentColumnMaxContentExceeded");
		} catch (exContentColumnMinContentUnderrun e) {
			fail("ContentColumnMinContentUnderrun");
		} catch (exValueNotWithinRange e) {
			fail("ValueNotWithinRange");
		}
		
		assertEquals(oDC.getDecayRate(), 0.01f, 0.0000001f);
		assertEquals(oDC.getIncreaseRate(), 0.1f, 0.0000001f);
		assertEquals(oDC.getInjectionValue(), 0.0f, 0.0000001f);
		assertEquals(oDC.getContent(), 0.0f, 0.0000001f);
		
		try {
			oDC = oSMS.addSlowMessenger(1);
			fail("SlowMessengerAlreadyExists not thrown");
		} catch (exSlowMessengerAlreadyExists e) {
			//expected exception			
		} catch (exContentColumnMaxContentExceeded e) {
			fail("ContentColumnMaxContentExceeded");
		} catch (exContentColumnMinContentUnderrun e) {
			fail("ContentColumnMinContentUnderrun");
		} catch (exValueNotWithinRange e) {
			fail("ValueNotWithinRange");
		}
	}



	/**
	 * Test method for {@link bw.body.internalSystems.clsSlowMessengerSystem#getSlowMessengers()}.
	 */
	@Test
	public void testGetSlowMessengers() {
		clsSlowMessengerSystem oSMS = new clsSlowMessengerSystem(null);
		try {
			oSMS.addSlowMessenger(1);
			oSMS.addSlowMessenger(2);
			oSMS.addSlowMessenger(3);
		} catch (exSlowMessengerAlreadyExists e) {
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		HashMap<Integer, clsDecayColumn> oMap = oSMS.getSlowMessengers();
		
		assertNotNull(oMap);
		assertEquals(oMap.size(), 3);
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsSlowMessengerSystem#existsSlowMessenger(int)}.
	 */
	@Test
	public void testExistsSlowMessengerInt() {
		clsSlowMessengerSystem oSMS = new clsSlowMessengerSystem(null);
		try {
			oSMS.addSlowMessenger(1);
			oSMS.addSlowMessenger(2);
			oSMS.addSlowMessenger(3);
		} catch (exSlowMessengerAlreadyExists e) {
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		assertTrue(oSMS.existsSlowMessenger(1));
		assertTrue(oSMS.existsSlowMessenger(new Integer(3)));
		assertFalse(oSMS.existsSlowMessenger(4));
	}

	/**
	 * Test method for {@link bw.body.internalSystems.clsSlowMessengerSystem#getMessengerValue(int)}.
	 */
	@Test
	public void testGetMessengerValueVsInject() {
		clsSlowMessengerSystem oSMS = new clsSlowMessengerSystem(null);
		try {
			oSMS.addSlowMessenger(1);
		} catch (exSlowMessengerAlreadyExists e) {
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		} catch (exValueNotWithinRange e) {
		}
		
		try {
			oSMS.inject(1, 0.5f);
		} catch (exSlowMessengerDoesNotExist e) {
			fail("SlowMessengerDoesNotExist");
		} catch (exValueNotWithinRange e) {
			fail("ValueNotWithinRange");
		}
		
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		oSMS.stepUpdateInternalState();
		
		
		try {
			assertEquals(oSMS.getMessengerValue(1), 0.27f, 0.001f);
		} catch (exSlowMessengerDoesNotExist e) {
			fail("SlowMessengerDoesNotExist");
		}
		
		try {
			assertEquals(oSMS.getMessengerValue(2), 0.5f, 0.000001f);
			fail("SlowMessengerDoesNotExist not thrown");
		} catch (exSlowMessengerDoesNotExist e) {
			//expected exception
		}
	}
}
