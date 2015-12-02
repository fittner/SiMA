/**
 * 05.07.2009, 12:07:04
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
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.*;
import complexbody.io.sensors.datatypes.enums.eSensorExtType;
import entities.abstractEntities.clsMobile;
import entities.actionProxies.itfTransferable;


/**
 * Action Executor for dropping objects
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:07:04
 * 
 */
public class clsExecutorDrop  extends clsActionExecutor {

	private clsMobile moEntity;

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private eSensorExtType moRangeSensor;
	public static final String P_RANGESENSOR = "rangesensor";
	
	
	public clsExecutorDrop(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);

	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);
		oProp.setProperty(pre + P_RANGESENSOR, eSensorExtType.MANIPULATE_AREA.toString());
		return oProp;
	}
	


	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_DROP;
	}
	@Override
	protected void setName() {
		moName="Drop executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}

	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		return 0;
	}
	
	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		if (!(moEntity instanceof clsMobile)) return false;
		//clsMobile oMEntity = (clsMobile) moEntity;
		/*
		if (oMEntity.getInventory().getCarriedEntity()==null) return false;
		
		//try to drop
		try {
			//decrease entity holders
			oMEntity.getInventory().dropCarriedItem();
		} catch(Throwable e) {
			return false;			
		} */
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
		//Attach action to entity
        clsAction oAction = new clsAction(1);
        oAction.setActionName("DROP");
        moEntity.addAction(oAction);
		return true;
		
		 
		
	}
}
