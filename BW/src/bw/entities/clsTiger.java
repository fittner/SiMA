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

import lifeCycle.clsTigerMind;
import sim.physics2D.util.Double2D;
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
public class clsTiger extends clsAnimal {

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
	public clsTiger(int pnId, clsPose poPose, Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, getFinalConfig(poConfig));
		applyConfig();		
		setDecisionUnit(new clsTigerMind());
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
		
		oDefault.add(eConfigEntries.SPEED, new clsConfigFloat(3.0f));
		oDefault.add(eConfigEntries.WEIGHT, new clsConfigFloat(100.0f));
		oDefault.add(eConfigEntries.RADIUS, new clsConfigFloat(5.0f));
		oDefault.add(eConfigEntries.COLOR, new clsConfigInt( Color.RED.getRGB() ));

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
}
