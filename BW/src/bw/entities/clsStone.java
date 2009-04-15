/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.utils.enums.eEntityType;
import ARSsim.physics2D.util.clsPose;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsStone extends clsInanimate {
	private static double mrDefaultRadiusToMassConversion = 10.0;
	private static String moImagePath = bw.sim.clsBWMain.msArsPath + "/src/resources/images/rock1.jpg";
	private static Color moDefaultColor = Color.DARK_GRAY;

	public clsStone(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, double prRadius)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(pnId, poPose, poStartingVelocity, null, prRadius * clsStone.mrDefaultRadiusToMassConversion);
		
		double rMass = prRadius * clsStone.mrDefaultRadiusToMassConversion;
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(prRadius, moDefaultColor , moImagePath), rMass);
    } 
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.STONE;
		
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
	public void execution(clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing(clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}

}