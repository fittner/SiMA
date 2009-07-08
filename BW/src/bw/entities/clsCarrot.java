/**
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import enums.eEntityType;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.util.clsPose;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.07.2009, 10:33:20
 * 
 */
public class clsCarrot extends clsPlant {

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 08.07.2009, 10:33:28
	 *
	 * @param pnId
	 * @param poStartingPose
	 * @param poStartingVelocity
	 * @param poConfig
	 */
	public clsCarrot(int pnId, clsPose poStartingPose,
			Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poStartingPose, poStartingVelocity, poConfig);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.CARROT;
		
	}	

}
