/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import bw.clsEntity;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsMobileObject2D extends sim.physics2D.physicalObject.MobileObject2D{

	private clsEntity moEntity;
	
	/**
	 * @param moEntity the moEntity to set
	 */
	public void setEntity(clsEntity moEntity) {
		this.moEntity = moEntity;
	}

	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @return
	 */
	public clsEntity getEntity() {
		// TODO Auto-generated method stub
		return moEntity;
	}
	
}
