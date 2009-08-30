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
	public ArrayList<Class<?>> getMutualExclusions(itfActionCommand poCommand) {
		return moMutEx;
	}

	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(itfActionCommand poCommand) {
		return 0;
	}
	@Override
	public double getStaminaDemand(itfActionCommand poCommand) {
		return 0;
	}

	@Override
	public boolean execute(itfActionCommand poCommand) {

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		//clsBodyColor oBColor = oBody.getIntraBodySystem().getBioSystem();
		
		if (poCommand instanceof clsActionBodyColor) {
			clsActionBodyColor oCmd = (clsActionBodyColor) poCommand;
			//oBColor.setRed(oCmd.getRed());
			//oBColor.setGreen(oCmd.getGreen());
			//oBColor.setBlue(oCmd.getBlue());			
		}
			
		if (poCommand instanceof clsActionBodyColorRed) {
			clsActionBodyColorRed oCmd = (clsActionBodyColorRed) poCommand;
			//oBColor.setRed(oCmd.getRed());
		}
		
		if (poCommand instanceof clsActionBodyColorGreen) {
			clsActionBodyColorGreen oCmd = (clsActionBodyColorGreen) poCommand;
			//oBColor.setGreen(oCmd.getGreen());
		}

		if (poCommand instanceof clsActionBodyColorBlue) {
			clsActionBodyColorBlue oCmd = (clsActionBodyColorBlue) poCommand;
			//oBColor.setBlue(oCmd.getBlue());			
		}

		return true;
	}	

	
}

