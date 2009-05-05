/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;

import ARSsim.physics2D.util.clsPose;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.utils.container.clsConfigContainer;
import enums.eEntityType;

public class clsCan extends clsInanimate {
	private static double mrDefaultWeight = 80.0f;
	private static double mrDefaultRadius = 2.0f;
	private static Color moDefaultColor = Color.blue;	
    
    public clsCan(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigContainer poConfig) {
		super(pnId, 
				poStartingPose, 
				poStartingVelocity, 
				new sim.physics2D.shape.Circle(clsCan.mrDefaultRadius, clsCan.moDefaultColor),
				clsCan.mrDefaultWeight,
				poConfig
				);

    }

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.CAN;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:34:14
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 17:46:23
	 * 
	 * @see bw.entities.clsEntity#getDefaultConfig()
	 */
	@Override
	protected clsConfigContainer getDefaultConfig() {
		// TODO Auto-generated method stub
		clsConfigContainer oDefault = new clsConfigContainer();
	
		
		return oDefault;
	}
}

