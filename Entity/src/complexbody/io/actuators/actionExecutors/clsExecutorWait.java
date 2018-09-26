/**
 * CHANGELOG
 *
 * 01.12.2015 Kollmann - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import properties.clsProperties;
import singeltons.eImages;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import entities.abstractEntities.clsEntity;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 01.12.2015, 16:56:20
 * 
 */
public class clsExecutorWait extends clsActionExecutor {

	clsEntity moEntity;
	
	/**
	 * DOCUMENT (Kollmann) - Base constructor 
	 *
	 * @since 01.12.2015 16:57:00
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorWait(String poPrefix, clsProperties poProp, clsEntity poSelf) {
		super(poPrefix, poProp);
		moEntity = poSelf;
	}

	/**
	 * DOCUMENT (Kollmann) - Return the default properties
	 * Currently uses the action executors implementation to set the energy<->stamina relation to a fixed value
	 *
	 * @since 01.12.2015 16:59:13
	 *
	 * @param poPrefix
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);

		return oProp;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 01.12.2015 16:56:20
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_WAIT;
	}

	/* (non-Javadoc)
	 *
	 * @since 01.12.2015 16:56:20
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		moName="Wait executor";
	}

	/* (non-Javadoc)
	 *
	 * @since 01.12.2015 17:04:36
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#getEnergyDemand(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 01.12.2015 17:04:24
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#getStaminaDemand(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		return 0; 
	}

	/* (non-Javadoc)
	 *
	 * @since 01.12.2015 16:56:20
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#execute(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		moEntity.setOverlayImage(eImages.Overlay_Action_Wait);
		
		//3) attach wait to the self 
        
        clsAction oAction = new clsAction(1);
        oAction.setActionName("WAIT");
        moEntity.addAction(oAction);
		
		return true;
	}

}
