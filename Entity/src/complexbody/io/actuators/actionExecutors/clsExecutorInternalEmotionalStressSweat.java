/**
 # * @author Benny D�nz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import body.clsComplexBody;
import body.itfget.itfGetBody;

import properties.clsProperties;

import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionEmotionalStressSweat;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;

/**
 * Action Executor for eating
 * Proxy itfAPEatable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prBiteSize = Size of bite taken when eating (default = weight 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorInternalEmotionalStressSweat extends clsInternalActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?		
	private double mrPersonalityAffectionFactor = 0.5; // [0.0, 1.0], 0.5 -> normal person (default)
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;

	public clsExecutorInternalEmotionalStressSweat(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity = poEntity;

		applyProperties(poPrefix,poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsInternalActionExecutor.getDefaultProperties(pre);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT;
	}
	@Override
	protected void setName() {
		moName="Emotinal Stress Sweat executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsInternalActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsInternalActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsInternalActionCommand poCommand) {
		return srStaminaDemand;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsInternalActionEmotionalStressSweat oCommand =(clsInternalActionEmotionalStressSweat) poCommand; 
		
	   // Affect the body
	   // FastMessenger is not needed.
	   // oBody.AddBodyAction() is not needed.

		// delete these 6 lines later. for testing..
		System.out.println( this.getClass().toString() + " receives the following emotions:" );
		
		for(int a = 0; a < oCommand.getStorageOfEmotionNames().size(); a++){
			System.out.println("Incoming emotion name: " + oCommand.getStorageOfEmotionNames().get(a) + ", intensity: " + oCommand.getStorageOfEmotionIntensities().get(a));
		}

		// Affect the body
		oBody.getInternalSystem().getBOrganSystem().getBOSweatGlands().affectStressSweat( oCommand.getStorageOfEmotionIntensities() , oCommand.getStorageOfEmotionNames() );

		return true;
	} // end execute

} // end class
