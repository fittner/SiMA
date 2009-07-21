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
import bw.utils.enums.eBindingState;
import bw.utils.enums.eConfigEntries;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import enums.eEntityType;

/**
 * 
 * This class represents uranium ore - an utility for the Fungus-Eater. It's a radioactivity source and can be carried.
 * 
 * TODO (horvath) - implement radioactivity
 * 
 * @author horvath
 * 08.07.2009, 11:25:46
 * 
 */
public class clsUraniumOre extends clsInanimate implements itfAPCarryable {
	private static double mrDefaultWeight = 30.0f;
	private static double mrDefaultRadius = 4.0f;
	private static String moImagePath = sim.clsBWMain.msArsPath + "/src/resources/images/Uranium.png";
	private static Color moDefaultColor = Color.green;	
	public double mrRadiationIntensity;

    
    public clsUraniumOre(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, double orRadiationIntensity, clsConfigMap poConfig) {
		super(pnId, 
				poStartingPose, 
				poStartingVelocity, 
				null,
				clsUraniumOre.mrDefaultWeight,
				getFinalConfig(poConfig)
				);
		
		mrRadiationIntensity = orRadiationIntensity;
		
		applyConfig();
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(clsUraniumOre.mrDefaultRadius, moDefaultColor , moImagePath), clsUraniumOre.mrDefaultWeight);

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
		
		clsConfigMap oBody = new clsConfigMap();		
		oDefault.add(eConfigEntries.BODY, oBody);		
		
		return oDefault;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.URANIUM;
		
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
	
	/*
	 * Interface Carryable
	 */
	public clsMobile getCarryableEntity() {
		return this;	
	}
	public void setCarriedBindingState(eBindingState pBindingState) {
		//handle binding-state implications 
	}

}

