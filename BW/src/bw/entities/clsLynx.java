/**
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import lifeCycle.JAM.clsLynxMind;
import sim.display.clsKeyListener;
import sim.physics2D.util.Double2D;
import simple.remotecontrol.clsRemoteControl;
import ARSsim.physics2D.util.clsPose;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigInt;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import enums.eEntityType;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 */
public class clsLynx extends clsAnimal {

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 12.05.2009, 19:34:43
	 *
	 * @param pnId
	 * @param poPose
	 * @param poStartingVelocity
	 * @param poConfig
	 */
	public clsLynx(int pnId, clsPose poPose, Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, getFinalConfig(poConfig));
		applyConfig();		
		setDecisionUnit(new clsLynxMind());
	}

	
	private void applyConfig() {
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.SPEED, new clsConfigFloat(6.0f));
		oDefault.add(eConfigEntries.WEIGHT, new clsConfigFloat(200.0f));
		oDefault.add(eConfigEntries.RADIUS, new clsConfigFloat(15.0f));
		oDefault.add(eConfigEntries.COLOR, new clsConfigInt( Color.PINK.getRGB() ));

		return oDefault;
	}
		
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		meEntityType = eEntityType.TIGER;
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {

	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
	}
}
