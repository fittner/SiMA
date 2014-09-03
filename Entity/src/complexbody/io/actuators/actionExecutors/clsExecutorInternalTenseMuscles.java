
package complexbody.io.actuators.actionExecutors;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import properties.clsProperties;
import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionTenseMuscles;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;


public class clsExecutorInternalTenseMuscles extends clsInternalActionExecutor {
	
	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?		
	private double mrPersonalityAffectionFactor = 0.5; // [0.0, 1.0], 0.5 -> normal person (default)
	
	private clsEntity moEntity;

	/**
	 * DOCUMENT (volkan) - insert description 
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorInternalTenseMuscles(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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

	/* (non-Javadoc)
	 *
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId =eBodyParts.ACTIONINT_TENSE_MUSCLES;
	}

	/* (non-Javadoc)
	 *
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		moName="internal action tense muscles executor";
	}

	/* (non-Javadoc)
	 *
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#getStaminaDemand(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public double getStaminaDemand(clsInternalActionCommand poCommand) {
		return srStaminaDemand;
	}
	@Override
	public double getEnergyDemand(clsInternalActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}

	/* (non-Javadoc)
	 *
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#execute(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsInternalActionTenseMuscles oCommand =(clsInternalActionTenseMuscles) poCommand; 
		
		// delete these 6 lines later. for testing..
		System.out.println( this.getClass().toString() + " receives the following emotions:" );
		
		for(int a = 0; a < oCommand.getStorageOfEmotionNames().size(); a++){
			System.out.println("Incoming emotion name: " + oCommand.getStorageOfEmotionNames().get(a) + ", intensity: " + oCommand.getStorageOfEmotionIntensities().get(a));
		}

		// Affect the body
		oBody.getInternalSystem().getBOrganSystem().getBOArms().affectMuscleTension( oCommand.getStorageOfEmotionIntensities() , oCommand.getStorageOfEmotionNames() );
		oBody.getInternalSystem().getBOrganSystem().getBOLegs().affectMuscleTension( oCommand.getStorageOfEmotionIntensities() , oCommand.getStorageOfEmotionNames() );
	

		return true;
	} // end execute
	
	

} // end class

