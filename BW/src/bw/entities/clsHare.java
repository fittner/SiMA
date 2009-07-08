/**
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import enums.eEntityType;

import lifeCycle.JAM.clsHareMind;

import sim.display.clsKeyListener;
import sim.physics2D.util.Double2D;
import simple.remotecontrol.clsRemoteControl;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPKillable;
import bw.body.itfget.itfGetFlesh;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigInt;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 */
public class clsHare extends clsAnimal implements itfGetFlesh, itfAPEatable, itfAPKillable {
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
	public clsHare(int pnId, clsPose poPose, Double2D poStartingVelocity, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, getFinalConfig(poConfig));
		applyConfig();		
		setDecisionUnit(new clsHareMind());
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
		
		oDefault.add(eConfigEntries.SPEED, new clsConfigDouble(3.0f));
		oDefault.add(eConfigEntries.WEIGHT, new clsConfigDouble(100.0f));
		oDefault.add(eConfigEntries.RADIUS, new clsConfigDouble(5.0f));
		oDefault.add(eConfigEntries.COLOR, new clsConfigInt( Color.RED.getRGB() ));

		return oDefault;
	}
		
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		meEntityType = eEntityType.HARE;
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	@Override
	public clsFlesh getFlesh() {
		// TODO Auto-generated method stub
		return ((clsComplexBody)moBody).getInternalSystem().getFlesh();
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#Eat(float)
	 */
	@Override
	public clsFood Eat(double prBiteSize) {
		return getFlesh().withdraw(prBiteSize);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPEatable#tryEat()
	 */
	@Override
	public double tryEat() {
		if (!isAlive()){
  		  return 0;
		} else {
		  return 1.0f;
		}
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:17:57
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKillable#kill(float)
	 */
	@Override
	public void kill(double pfForce) {
		setAlive(false);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:17:57
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKillable#tryKill(float)
	 */
	@Override
	public double tryKill(double pfForce) {
		return 0;
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
		super.updateInternalState();
		if ( isAlive() && getFlesh().getTotallyConsumed() ) {
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}
	
}
