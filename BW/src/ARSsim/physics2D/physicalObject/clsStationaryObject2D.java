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
public class clsStationaryObject2D extends sim.physics2D.physicalObject.StationaryObject2D{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 915012100712508497L;
	
	private clsEntity moEntity;
	
	public clsStationaryObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
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
