/**
 * CHANGELOG
 *
 * Sep 11, 2014 volkan - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import body.clsComplexBody;
import properties.clsProperties;
import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionAffectHeartBloodPressureSystolic;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;
import body.itfget.itfGetBody;
/**
 * DOCUMENT (volkan) - insert description 
 * 
 * @author volkan
 * Sep 11, 2014, 6:49:13 AM
 * 
 */
public class clsExecutorInternalAffectHeartBloodPressureSystolic extends clsInternalActionExecutor{
	
	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?
	
	private clsEntity moEntity;

	/**
	 * DOCUMENT (volkan) - insert description 
	 *
	 * @since Sep 11, 2014 6:50:29 AM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorInternalAffectHeartBloodPressureSystolic(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
	 * @since Sep 11, 2014 6:50:27 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO (volkan) - Auto-generated method stub
		mePartId = eBodyParts.ACTIONINT_HEART_BLOOD_PRESSURE;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2014 6:50:27 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		// TODO (volkan) - Auto-generated method stub
		moName="Internal action heart blood pressure systolic executor";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2014 6:50:27 AM
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
	 * @since Sep 11, 2014 6:50:27 AM
	 * 
	 * @see bw.body.io.actuators.clsInternalActionExecutor#execute(du.itf.actions.clsInternalActionCommand)
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsInternalActionAffectHeartBloodPressureSystolic oCommand =(clsInternalActionAffectHeartBloodPressureSystolic) poCommand;
		
		// Affect the body
		oBody.getInternalSystem().getBOrganSystem().getBOHeart().affectBloodPressureSystolic( oCommand.getIntensity() );
		
		return true;
	}

}
