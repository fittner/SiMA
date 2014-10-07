/**
 * CHANGELOG
 *
 * Sep 10, 2014 volkan - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import body.clsComplexBody;
import properties.clsProperties;
import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionRaiseEyeBrowsCenter;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;
import body.itfget.itfGetBody;

public class clsExecutorInternalRaiseEyeBrowsCenter extends clsInternalActionExecutor {

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?	
	
	// class variables
	private clsEntity moEntity;
	
	/**
	 * DOCUMENT (volkan) - insert description 
	 *
	 * @since Sep 5, 2014 5:47:00 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorInternalRaiseEyeBrowsCenter(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
	 * @since Sep 5, 2014 5:45:43 PM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO (volkan) - Auto-generated method stub
		mePartId = eBodyParts.ACTIONINT_FACIAL_CHANGE_EYEBROWS;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2014 5:45:43 PM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		// TODO (volkan) - Auto-generated method stub
		moName="Raise Eye Brow Center executor";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2014 5:45:43 PM
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
	 * @since Sep 5, 2014 5:45:43 PM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#execute(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsInternalActionRaiseEyeBrowsCenter oCommand =(clsInternalActionRaiseEyeBrowsCenter) poCommand;
		
		// 1. Affect the body
		oBody.getIntraBodySystem().getFacialExpression().getBOFacialEyeBrows().changeEyeBrowsCenter( oCommand.getEyeBrowsCenterRaise() );
		
		return true;
	}

}
