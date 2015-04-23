/**
 * @author
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;
import singeltons.eImages;

import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionMove;

import entities.abstractEntities.clsEntity;

/**
 * Action Executor for inner speech
 * 
 * Parameters:
 * 
 * @author 

 * 
 */
public class clsExecutorInnerSpeech extends clsActionExecutor{

	static double mrpleasure = 0.5; //0.5f;		//Lustgewinn beim Aussprechen
	static double srStaminaDemand = 0.5; //0.5f;		//Stamina demand 

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Speed) ; 			
	static double srStaminaScalingFactor = 0.01f;  
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
 

	public clsExecutorInnerSpeech(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
	
		//applyProperties(poPrefix,poProp);
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
		mePartId = entities.enums.eBodyParts.ACTIONEX_INNERSPEECH;
	}
	@Override
	protected void setName() {
		moName="Speech executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Executor 
	 */
	
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionMove oCommand =(clsActionMove) poCommand; 
		if (oCommand.getStaminaDemand()==1){
    		moEntity.setOverlayImage(eImages.Overlay_Action_InnerSpeech_Schnitzel);
    	}
		if (oCommand.getStaminaDemand()==1){
            moEntity.setOverlayImage(eImages.Overlay_Action_InnerSpeech_Nourish);
    	
    	}
    	return true;
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
		clsActionMove oCommand =(clsActionMove) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getSpeed()) ;
	}
	
}
	
	