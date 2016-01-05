/**
 * CHANGELOG
 *
 * Oct 29, 2015 zhukova - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;





import properties.clsProperties;
import singeltons.eImages;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import entities.abstractEntities.clsEntity;


/**
 * DOCUMENT (zhukova) - insert description 
 * 
 * Class for execution of Request action 
 * 
 * @author zhukova
 * Oct 29, 2015, 3:52:29 PM
 * 
 */
public class clsExecutorRequest extends clsActionExecutor{

	/**
	 * DOCUMENT (zhukova) - insert description 
	 *
	 * @since Oct 29, 2015 3:52:52 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	 
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
	public clsExecutorRequest(String poPrefix, clsProperties poProp, clsEntity poSelf) {
		super(poPrefix, poProp);
		moEntity = poSelf;
		
	}

	
	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);

		return oProp;
	}
	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 3:52:57 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_REQUEST;
		
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 3:52:57 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		moName="Request executor";
		
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 29, 2015 3:52:57 PM
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
	 * @since Oct 29, 2015 3:52:57 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#execute(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		moEntity.setOverlayImage(eImages.Overlay_Action_Request);
		
		//3) attach request to the self 
        
        clsAction oAction = new clsAction(1);
        oAction.setActionName("REQUEST");
        moEntity.addAction(oAction);
		
		return true;
	}

}
