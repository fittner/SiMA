/**
 * CHANGELOG
 *
 * Oct 29, 2015 zhukova - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import properties.clsProperties;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;

/**
 * DOCUMENT (zhukova) - insert description 
 * 
 * @author zhukova
 * Oct 29, 2015, 4:48:23 PM
 * 
 */
public class clsExecutorAgree extends clsActionExecutor {

	/**
	 * DOCUMENT (zhukova) - insert description 
	 *
	 * @since Oct 29, 2015 4:48:37 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsExecutorAgree(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
		// TODO (zhukova) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 4:48:40 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_AGREE;

		
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 4:48:40 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		moName="Request Agree executor";
		
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 4:48:40 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#getStaminaDemand(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		// TODO (zhukova) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 4:48:40 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#execute(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		
		// TODO (zhukova) - Auto-generated method stub
		return true;
	}

}
