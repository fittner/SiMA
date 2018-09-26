/**
 * CHANGELOG
 *
 * Oct 31, 2015 zhukova - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

/*import body.clsComplexBody;
import body.itfget.itfGetBody;*/
import body.clsComplexBody;
import body.itfget.itfGetBody;
import properties.clsProperties; 
//import singeltons.eImages;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionObjectTransfer;
//import complexbody.io.actuators.actionCommands.clsActionRequest;
import complexbody.io.sensors.datatypes.enums.eSensorExtType;
//import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
//import entities.actionProxies.itfAPDivideable;
//import entities.actionProxies.itfTransferable;
import entities.actionProxies.itfTransferable;

/**
 * DOCUMENT (zhukova) - insert description 
 * 
 * @author zhukova
 * Oct 31, 2015, 7:06:49 PM
 * 
 */
public class clsExecutorObjectTransfer extends clsActionExecutor {

	/**
	 * DOCUMENT (zhukova) - insert description 
	 *
	 * @since Oct 31, 2015 7:07:30 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	
	private clsMobile moEntity;
	public static final String P_RANGESENSOR = "rangesensor";
	private eSensorExtType moRangeSensor;

	public clsExecutorObjectTransfer(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		moEntity  = poEntity;
		applyProperties(poPrefix,poProp);

		// TODO (zhukova) - Auto-generated constructor stub
	}

	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor = eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre + P_RANGESENSOR, eSensorExtType.MANIPULATE_AREA.toString());
		return oProp;
	}
	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2015 7:07:32 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		
		mePartId = entities.enums.eBodyParts.ACTIONEX_OBJECTTRANSFER;

	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2015 7:07:32 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#setName()
	 */
	@Override
	protected void setName() {
		
		moName="Object transfer executor";			
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2015 7:07:32 PM
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
	 * @since Oct 31, 2015 7:07:32 PM
	 * 
	 * @see complexbody.io.actuators.clsActionExecutor#execute(complexbody.io.actuators.actionCommands.clsActionCommand)
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		
		clsActionObjectTransfer oCommand = (clsActionObjectTransfer) poCommand; 
		clsMobile oMEntity = (clsMobile) moEntity;		
		clsMobile carriedEntity = oMEntity.getInventory().getCarriedEntity();
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		if (carriedEntity == null) { return false; } 
		try {
			itfTransferable oATransferedEntity = (itfTransferable) findEntityInRange(moEntity, oBody, moRangeSensor ,itfTransferable.class);
			if (oATransferedEntity == null) {
				return false;
			}
			clsMobile oTransferedEntity = oATransferedEntity.getTransferedEntity();
			oMEntity.getInventory().dropCarriedItem();
			oTransferedEntity.getInventory().setCarriedEntity(carriedEntity);
		} catch(Throwable e) {
			return false;			
		}		
        clsAction oAction = new clsAction(1);
        oAction.setActionName("TRANSFER");
        moEntity.addAction(oAction);
		return true;
	}

}
