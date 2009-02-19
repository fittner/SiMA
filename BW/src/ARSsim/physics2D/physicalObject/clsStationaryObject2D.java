/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import bw.clsEntity;
import bw.factories.clsSingletonMasonGetter;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsStationaryObject2D extends sim.physics2D.physicalObject.StationaryObject2D implements itfGetEntity, itfSetupFunctions {
	
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
	
	public void setPosition(sim.util.Double2D poPosition) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, poPosition);
	}
		
}
