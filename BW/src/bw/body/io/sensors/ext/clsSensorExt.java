/**
 * @author zeilinger
 * 14.07.2009, 11:49:17
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;

import bw.body.io.clsBaseIO;
import bw.utils.container.clsConfigMap;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 14.07.2009, 11:49:17
 * 
 */
public abstract class clsSensorExt extends bw.body.io.sensors.external.clsSensorExt{

	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 14.07.2009, 11:51:40
	 *
	 * @param poBaseIO
	 */
	public clsSensorExt(clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, poConfig);
		
		//applyConfig();
		// TODO Auto-generated constructor stub
	}
	
	public abstract void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj);
}
