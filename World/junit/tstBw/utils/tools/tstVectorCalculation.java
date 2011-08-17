/**
 * CHANGELOG
 *
 * 27.06.2011 deutsch - File created
 *
 */
package tstBw.utils.tools;

import static org.junit.Assert.*;

import org.junit.Test;

import sim.physics2D.util.Double2D;

import bw.utils.tools.clsVectorCalculation;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 27.06.2011, 09:32:54
 * 
 */
@SuppressWarnings("deprecation")
public class tstVectorCalculation {

	/**
	 * Test method for {@link bw.utils.tools.clsVectorCalculation#getDirectionPolar(sim.physics2D.util.Double2D)}.
	 */
	@Test
	public void testGetDirectionPolar() {
		double angle = 0;
		
		angle = clsVectorCalculation.getDirectionPolar(new Double2D(0,0));
		assertEquals(0, angle, 0.001);

		angle = clsVectorCalculation.getDirectionPolar(new Double2D(1,1));
		assertEquals(Math.PI/4.0, angle, 0.001);

		angle = clsVectorCalculation.getDirectionPolar(new Double2D(-1,0));
		assertEquals(Math.PI, angle, 0.001);
		
		angle = clsVectorCalculation.getDirectionPolar(new Double2D(-1,-1));
		assertEquals(Math.PI*1.25, angle, 0.001);
	}

}
