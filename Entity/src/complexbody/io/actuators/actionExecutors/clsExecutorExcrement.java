/**
 * clsExecutorExcrement.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 29.08.2009, 09:58:22
 */
package complexbody.io.actuators.actionExecutors;


import java.util.ArrayList;

import properties.clsProperties;

import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.*;

import registration.clsRegisterEntity;
import singeltons.eImages;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsSmartExcrement;

/**
 * Action Executor for excrementing
 * Parameters: none
 * 	 prIntensityScalingFactor = Scales the intensity for small vs big animals/arsins (default = 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorExcrement extends clsActionExecutor{

	static double srStaminaDemand = 0.1f;			//Stamina demand =srStaminaDemand*Intensity 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	
	private double mrIntensityScalingFactor;

	public static final String P_INTENSITYCALINGFACTOR = "intensityscalingfactor";

	public clsExecutorExcrement(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_INTENSITYCALINGFACTOR, 1f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		mrIntensityScalingFactor=poProp.getPropertyFloat(pre+P_INTENSITYCALINGFACTOR);
	}


	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_EXCREMENT;
	}
	@Override
	protected void setName() {
		moName="Excrement executor";	
	}


	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		clsActionExcrement oCommand =(clsActionExcrement) poCommand;
		return srStaminaDemand*oCommand.getIntensity() ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionExcrement oCommand =(clsActionExcrement) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		
		moEntity.setOverlayImage(eImages.Overlay_Action_Deposit);
		
		

		//Get Smart Excrement
		clsSmartExcrement oEx = oBody.getInterBodyWorldSystem().getCreateExcrement().getSmartExcrements(oCommand.getIntensity()*mrIntensityScalingFactor );
		if (oEx==null) return false; 
		
		//Drop it in Mason
		clsRegisterEntity.registerEntity(oEx);
		oEx.setRegistered(true);
		
		// the stimulation of the erogenous zone
        oBody.getIntraBodySystem().getErogenousZonesSystem().StimulateRectalMucosa(0.5); //TODO 0.5 frei gew�hlt
        //eigentlich realistischer: reizung durch die anwesenheit der Kotstange im rrectum, bis zur Schmerzgrenze, dann nochmal reizung bei Ausscheidung
		
		//reset the wait till digestion starts again
		oBody.getInternalSystem().getStomachSystem().ResetRectumWaitCounter();
		
		
		//Attach action to entity
        clsAction oAction = new clsAction(1);
        oAction.setActionName("DEPOSIT");
        moEntity.addAction(oAction);
		return true;
	}	
	
}