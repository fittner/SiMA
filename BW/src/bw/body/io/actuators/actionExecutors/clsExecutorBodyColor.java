/**
 * clsExecutorBodyColor.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 28.08.2009, 15:24:18
 */
package bw.body.io.actuators.actionExecutors;


import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.intraBodySystems.clsBodyColor;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import decisionunit.itf.actions.*;

/**
 * Action Executor for changing body-colors
 * Parameters:
 *    red, green, blue (single or all at once) 
 *
 * @author Benny Dï¿½nz
 * 13.05.2009, 21:45:05
 * 
 */

public class clsExecutorBodyColor extends clsActionExecutor{

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorBodyColor(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();

		return oProp;
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR;
	}
	@Override
	protected void setName() {
		moName="BodyColor executor";
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
		return 0;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		return 0;
	}

	@Override
	public boolean execute(clsActionCommand poCommand) {

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsBodyColor oBColor = oBody.getIntraBodySystem().getColorSystem();
		
		if (poCommand instanceof clsActionBodyColor) {
			clsActionBodyColor oCmd = (clsActionBodyColor) poCommand;
			oBColor.changeColor(oCmd.getRed(), oCmd.getGreen(), oCmd.getBlue());
		}
			
		if (poCommand instanceof clsActionBodyColorRed) {
			clsActionBodyColorRed oCmd = (clsActionBodyColorRed) poCommand;
			oBColor.changeRed(oCmd.getRed());
		}
		
		if (poCommand instanceof clsActionBodyColorGreen) {
			clsActionBodyColorGreen oCmd = (clsActionBodyColorGreen) poCommand;
			oBColor.changeGreen(oCmd.getGreen());
		}

		if (poCommand instanceof clsActionBodyColorBlue) {
			clsActionBodyColorBlue oCmd = (clsActionBodyColorBlue) poCommand;
			oBColor.changeBlue(oCmd.getBlue());			
		}

		return true;
	}	

	
}

