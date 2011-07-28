/**
 * @author deutsch
 * 22.07.2009, 09:02:32
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.config;

import static org.junit.Assert.*;

import org.junit.Test;

import config.clsRandomProperties;


/**
 * 
 * 
 * @author deutsch
 * 22.07.2009, 09:02:32
 * 
 */
public class tstRandomProperties {

	/**
	 * Test method for {@link config.clsRandomProperties#getRandom(java.lang.String)}.
	 */
	@Test
	public void testGetRandom() {
		String oNull = "";
		String oNotSupported = "$";

		try {
			@SuppressWarnings("unused")
			double temp = clsRandomProperties.getRandom(oNull);
			fail();
		} catch (java.lang.NullPointerException e) {
		} catch (java.lang.UnsupportedOperationException e) {
		}

		try {
			@SuppressWarnings("unused")
			double temp = clsRandomProperties.getRandom(oNotSupported);
			fail();
		} catch (java.lang.NullPointerException e) {
		} catch (java.lang.UnsupportedOperationException e) {
		}

	}
	
	@Test
	public void testGetRandomLinear() {
		String oL = "L1.0;2.0";
		
		try {
			double temp = clsRandomProperties.getRandom(oL);
			if (temp<1.0 || temp > 2.0) {
				fail(temp+" does not match expected result from "+oL);
			}
		} catch (java.lang.NullPointerException e) {
			fail();
		} catch (java.lang.UnsupportedOperationException e) {
			fail();			
		}
	}

	@Test
	public void testGetRandomGaussian() {
		String oG = "G100.0;1.0";	
		
		try {
			@SuppressWarnings("unused")
			double temp = clsRandomProperties.getRandom(oG);
		} catch (java.lang.NullPointerException e) {
			fail();
		} catch (java.lang.UnsupportedOperationException e) {
			fail();			
		}		
	}

	@Test
	public void testGetRandomBoundedGaussian() {
		String oBG = "BG100.0;1.0;95.0;105.0";
		
		try {
			double temp = clsRandomProperties.getRandom(oBG);
			if (temp<95.0 || temp > 105.0) {
				fail(temp+" does not match expected result from "+oBG);
			}			
		} catch (java.lang.NullPointerException e) {
			fail();
		} catch (java.lang.UnsupportedOperationException e) {
			fail();			
		}			
	}
	
	@Test
	public void testGetRandomTailGaussian() {
		String oTG = "TG0.0;1.0";	
		
		try {
			double temp = clsRandomProperties.getRandom(oTG);
			if (temp<0.0) {
				fail(temp+" does not match expected result from "+oTG);
			}	
		} catch (java.lang.NullPointerException e) {
			fail();
		} catch (java.lang.UnsupportedOperationException e) {
			fail();			
		}		
	}	
}
