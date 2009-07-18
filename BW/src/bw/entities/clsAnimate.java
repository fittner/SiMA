/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import java.util.TreeMap;

import decisionunit.clsBaseDecisionUnit;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.util.clsPose;
import bw.body.clsBaseBody;
import bw.body.io.sensors.ext.clsSensorEngine;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.external.clsSensorRadiation;
import bw.body.itfget.itfGetBody;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.physicalObjects.sensors.clsEntityPartRadiation;
import bw.utils.container.clsConfigMap;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile implements itfGetBody {

	public clsBaseBody moBody; // the instance of a body
	
	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	protected clsAnimate(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double poMass, clsConfigMap poConfig) {
		super(pnId, poPose, poStartingVelocity, poShape, poMass, clsAnimate.getFinalConfig(poConfig));
		
		applyConfig();
		
		moBody = createBody();
	}
	
	protected abstract clsBaseBody createBody();
	
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
	
	public void setDecisionUnit(clsBaseDecisionUnit poDecisionUnit) {
		moBody.getBrain().setDecisionUnit(poDecisionUnit);
	}
	
	@Override
	public void sensing() {
		moBody.stepSensing();
		
	}
	
	@Override
	public void execution() {
		moBody.stepExecution();
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		moBody.stepUpdateInternalState();
		
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 18:40:22
	 * 
	 * @see bw.body.itfGetBody#getBody()
	 */
	public clsBaseBody getBody() {
		// TODO Auto-generated method stub
		return moBody;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		moBody.getBrain().stepProcessing();
	}
	
	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)this.moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
	
	/**
	 * 
	 * (horvath) - returns the radiation sensor
	 *
	 * @author horvath
	 * 16.07.2009, 12:11:00
	 *
	 * @return clsEntityPartRadiation
	 */
	public clsEntityPartRadiation getRadiation()
	{
		return ((clsSensorRadiation)this.moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.RADIATION)).getMoRadiationArea(); 
	}
	
		
	public clsEntityPartVision getEatableArea()
	{
		return ((clsSensorEatableArea)this.moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
	}	
	
	//HZ - integrate SensorEngine 
	public TreeMap<Double, clsEntitySensorEngine> getSensorEngine()
	{
		return ((clsSensorEngine)this.moBody
					.getExternalIO().moSensorEngine).getMeSensorAreas(); 
	}
}
