
package complexbody.io.actuators.actionExecutors;

import body.clsComplexBody;
import properties.clsProperties;
import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionTenseMuscles;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;
import body.itfget.itfGetBody;
public class clsExecutorInternalTenseMuscles extends clsInternalActionExecutor {
	
	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?
	
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
		mePartId = eBodyParts.ACTIONINT_TENSE_MUSCLES;
	}

	/* (non-Javadoc)
	 *
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		moName="Internal action tense muscles executor";
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
		
		// Affect the body
		oBody.getInternalSystem().getBOrganSystem().getBOArms().affectMuscleTension( oCommand.getTenseMuscles() );
		oBody.getInternalSystem().getBOrganSystem().getBOLegs().affectMuscleTension( oCommand.getTenseMuscles() );

		return true;
	}
} // end class

