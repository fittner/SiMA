/**
 * clsExecutorAttack.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 28.08.2009, 18:30:17
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;

import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionAttackLightning;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionCultivate;
import complexbody.io.actuators.actionCommands.clsActionDivide;
import complexbody.io.actuators.actionCommands.clsActionEat;
import complexbody.io.actuators.actionCommands.clsActionKiss;
import complexbody.io.sensors.datatypes.enums.eSensorExtType;

import singeltons.eImages;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import entities.abstractEntities.clsEntity;
import entities.actionProxies.itfAPDivideable;

/**
 * Action Executor for divide 
 * Proxy itfAPDivideable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 
 * @author herret
 * 16.07.2013, 16:31:13
 * 
 */

public class clsExecutorDivide extends clsActionExecutor{

	static double srStaminaBase = 1f;			//Stamina demand =srStaminaBase*Force*srStaminaScalingFactor; 			
	static double srStaminaScalingFactor = 0.0005;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	

	public static final String P_RANGESENSOR = "rangesensor";

	public clsExecutorDivide(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionAttackLightning.class);
		moMutEx.add(clsActionKiss.class);
		moMutEx.add(clsActionCultivate.class);

		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.MANIPULATE_AREA.toString());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_DIVIDE;
	}
	@Override
	protected void setName() {
		moName="Divide executor";	
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
		return srStaminaScalingFactor* srStaminaBase;
	}


	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		
		clsActionDivide oCommand =(clsActionDivide) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		itfAPDivideable oDivideEntity = (itfAPDivideable) findEntityInRange(moEntity, oBody, moRangeSensor ,itfAPDivideable.class);


		if (oDivideEntity==null) {
			//Nothing in range
			return false;
		} 
		//setting a overlay image, normal eating
		moEntity.setOverlayImage(eImages.Overlay_Action_Divide);
		//devide
		oDivideEntity.devide(oCommand.getSplitFactor());
		
		//3) attach eat to the self 
        
        clsAction oAction = new clsAction(1);
        oAction.setActionName("DIVIDE");
        oAction.attachEntity((clsEntity) oDivideEntity);
        moEntity.addAction(oAction);
		
		return true;
	}	
	
}