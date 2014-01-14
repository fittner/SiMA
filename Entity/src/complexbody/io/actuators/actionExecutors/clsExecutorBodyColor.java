/**
 * clsExecutorBodyColor.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 28.08.2009, 15:24:18
 */
package complexbody.io.actuators.actionExecutors;


import config.clsProperties;
import java.util.ArrayList;

import complexbody.intraBodySystems.clsBodyColor;
import complexbody.io.actuators.clsActionExecutor;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import du.itf.actions.*;
import entities.abstractEntities.clsEntity;

/**
 * Action Executor for changing body-colors
 * Parameters:
 *    red, green, blue (single or all at once) 
 *
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 */

public class clsExecutorBodyColor extends clsActionExecutor{

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorBodyColor(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);

		return oProp;
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_BODYCOLOR;
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

