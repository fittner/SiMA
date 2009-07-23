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

import du.utils.enums.eDecisionType;

import enums.eEntityType;

import sim.display.clsKeyListener;
import simple.remotecontrol.clsRemoteControl;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPKillable;
import bw.body.itfget.itfGetFlesh;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 */
public class clsHare extends clsAnimal implements itfGetFlesh, itfAPEatable, itfAPKillable {

	public clsHare(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp );
		applyProperties(poPrefix, poProp);
		setAlive(true);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		//add additional fields here
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.DU_HARE_MIND_IFTHENELSE.name());
		
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.SHAPE_CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE_RADIUS, "5.0");
		oProp.setProperty(pre+P_ENTITY_COLOR_R, Color.RED.getRed() );
		oProp.setProperty(pre+P_ENTITY_COLOR_G, Color.RED.getGreen() );
		oProp.setProperty(pre+P_ENTITY_COLOR_B, Color.RED.getBlue() );
		
//		oProp.setProperty(pre+P_MOBILE_SPEED, "3.0" );
//		oProp.setProperty(pre+P_ENTITY_WEIGHT, "100.0" ); //TODO: (creator) is this for the mass???
		
		return oProp;
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
