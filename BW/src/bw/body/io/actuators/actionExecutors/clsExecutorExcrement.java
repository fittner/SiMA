/**
 * clsExecutorExcrement.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 29.08.2009, 09:58:22
 */
package bw.body.io.actuators.actionExecutors;


import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsSmartExcrement;
import bw.factories.clsRegisterEntity;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;

/**
 * Action Executor for excrementing
 * Parameters: none
 * 	 prIntensityScalingFactor = Scales the intensity for small vs big animals/bubbles (default = 1)
 * 
 * @author Benny Dï¿½nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorExcrement extends clsActionExecutor{

	static double srStaminaDemand = 0.1f;			//Stamina demand =srStaminaDemand*Intensity 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	
	private double mrIntensityScalingFactor;

	public static final String P_INTENSITYCALINGFACTOR = "intensityscalingfactor";

	public clsExecutorExcrement(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_INTENSITYCALINGFACTOR, 1f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mrIntensityScalingFactor=poProp.getPropertyFloat(pre+P_INTENSITYCALINGFACTOR);
	}


	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT;
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

		//Get Smart Excrement
		clsSmartExcrement oEx = oBody.getInterBodyWorldSystem().getCreateExcrement().getSmartExcrements(oCommand.getIntensity()*mrIntensityScalingFactor );
		if (oEx==null) return false; 
		
		//Drop it in Mason
		clsRegisterEntity.registerEntity(oEx);
		oEx.setRegistered(true);
		
		return true;
	}	
	
}