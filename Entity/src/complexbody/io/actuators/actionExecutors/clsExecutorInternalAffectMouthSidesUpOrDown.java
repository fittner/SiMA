/**
 * CHANGELOG
 *
 * Sep 11, 2014 volkan - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import properties.clsProperties;
import body.clsComplexBody;
import body.itfget.itfGetBody;

import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionAffectMouthSidesUpOrDown;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;

/**
 * DOCUMENT (volkan) - insert description 
 * 
 * @author volkan
 * Sep 11, 2014, 2:30:33 AM
 * 
 */
public class clsExecutorInternalAffectMouthSidesUpOrDown  extends clsInternalActionExecutor {
	
	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?
	
	// class variables
	private clsEntity moEntity;

	/**
	 * DOCUMENT (volkan) - insert description 
	 *
	 * @since Sep 11, 2014 2:32:58 AM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorInternalAffectMouthSidesUpOrDown(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);

		moEntity = poEntity;

		applyProperties(poPrefix, poProp);
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
	 * @since Sep 11, 2014 2:32:04 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO (volkan) - Auto-generated method stub
		mePartId = eBodyParts.ACTIONINT_FACIAL_CHANGE_MOUTH;
		
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2014 2:32:04 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		// TODO (volkan) - Auto-generated method stub
		moName="Affect Mouth Sides Up Or Down executor";
		
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2014 2:32:04 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#getStaminaDemand(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public double getStaminaDemand(clsInternalActionCommand poCommand) {
		// TODO (volkan) - Auto-generated method stub
		return srStaminaDemand;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2014 2:32:04 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#execute(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsInternalActionAffectMouthSidesUpOrDown oCommand =(clsInternalActionAffectMouthSidesUpOrDown) poCommand;
		
		// 1. Affect the body
		oBody.getIntraBodySystem().getFacialExpression().getBOFacialMouth().changeMouthSides( oCommand.getMouthSidesUpOrDown() );
		
		return true;
	}

}
