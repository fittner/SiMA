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
import bw.utils.container.clsConfigMap;
import enums.eEntityType;
import ARSsim.physics2D.util.clsPose;

/**
 * Mason representative (physics+renderOnScreen) for a fungus. 
 * 
 * 
 * @author muchitsch
 * 
 */
public class clsFungus extends clsInanimate {
	//private static String moImagePath = sim.clsBWMain.msArsPath + "/src/resources/images/fungus.jpg";
	private static Color moDefaultColor = Color.orange;

	public clsFungus(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, double prRadius, clsConfigMap poConfig)
    {
		super(pnId, poPose, poStartingVelocity, null, prRadius , clsFungus.getFinalConfig(poConfig));
		
		applyConfig();
		
		double rMass = prRadius * 10; //just some radom default value
		
		//setShape(new ARSsim.physics2D.shape.clsCircleImage(prRadius, moDefaultColor , moImagePath), rMass);
		setShape(new sim.physics2D.shape.Circle(prRadius, moDefaultColor),rMass);
    } 
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.FUNGUS;
		
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
	 * 25.02.2009, 17:37:10
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
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}



}