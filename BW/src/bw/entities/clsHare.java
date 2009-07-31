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

//import sim.display.clsKeyListener;
//import simple.remotecontrol.clsRemoteControl;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFlesh;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.io.actuators.actionProxies.itfAPKillable;
import bw.body.itfget.itfGetFlesh;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import bw.utils.tools.clsFood;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:30:22
 * 
 */
public class clsHare extends clsAnimal implements itfGetFlesh, itfAPEatable, itfAPKillable {

	public clsHare(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp );
		applyProperties(poPrefix, poProp);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		//nothing to do
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimal.getDefaultProperties(pre) );
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.HARE_IFTHENELSE.name());

		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//FIXME (deutsch) - .4. is not guaranteed - has to be changed!
		oProp.setProperty(pre+"body.sensorsext.4.offset", 5);
		oProp.setProperty(pre+"body.sensorsext.4.sensor_range", 2.5);
		oProp.setProperty(pre+"body.sensorsext.2.sensor_range", 30.0);
		oProp.setProperty(pre+"body.sensorsext.3.sensor_range", 30.0);

		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 150.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_RADIUS, 2.5);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.GRAY);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/hase.png");
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
//		oProp.setProperty(pre+P_MOBILE_SPEED, "3.0" );
		
		return oProp;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
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
//	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.07.2009, 15:13:45
	 * 27.07.2009, 14:58    adapted by zeilinger from 
	 * 						clsComplexBody to clsMeatBody 
	 * @see bw.body.itfget.itfGetFlesh#getFlesh()
	 */
	public clsFlesh getFlesh() {
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
		super.updateInternalState();
		if ( isAlive() && getFlesh().getTotallyConsumed() ) {
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}
}