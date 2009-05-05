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

/**
 * An instance of the mobile object clsAnimate that can:
 * - grow
 * - be harvest
 * - withdrawn
 * - be eaten  
 * 
 * @author langr
 * 
 */
public class clsPlant extends clsAnimate{

	private static double mrDefaultWeight = 300.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.ORANGE;	
    
    public clsPlant(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigContainer poConfig) {
		super(pnId, poStartingPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsPlant.mrDefaultRadius, clsPlant.moDefaultColor), clsPlant.mrDefaultWeight, poConfig);

    }

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType =  eEntityType.PLANT;
		
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
	 * 25.02.2009, 17:34:33
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
	 * 25.02.2009, 17:34:33
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
	 * 05.05.2009, 17:46:06
	 * 
	 * @see bw.entities.clsEntity#getDefaultConfig()
	 */
	@Override
	protected clsConfigContainer getDefaultConfig() {
		// TODO Auto-generated method stub
		clsConfigContainer oDefault = super.getDefaultConfig();
	
		
		return oDefault;
	}

}
