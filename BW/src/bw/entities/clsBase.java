/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import ARSsim.physics2D.util.clsPose;
import bw.utils.container.clsConfigMap;
import enums.eEntityType;


/**
 * TODO (horvath) - insert description 
 * 
 * @author horvath
 * 08.07.2009, 14:52:00
 * 
 */
public class clsBase extends clsInanimate{
	private static double mrDefaultWeight = 1000000.0f;
	private static double mrDefaultRadius = 20.0f;
	private static String moImagePath = sim.clsBWMain.msArsPath + "/src/resources/images/spacestation.gif";
	private static Color moDefaultColor = Color.gray;	
    
    public clsBase(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, 
				poStartingPose, 
				poStartingVelocity, 
				new sim.physics2D.shape.Circle(clsBase.mrDefaultRadius, clsBase.moDefaultColor),
				clsBase.mrDefaultWeight,
				getFinalConfig(poConfig)
				);
		
		applyConfig();

		setShape(new ARSsim.physics2D.shape.clsCircleImage(clsBase.mrDefaultRadius, moDefaultColor , moImagePath), clsBase.mrDefaultWeight);
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

}

