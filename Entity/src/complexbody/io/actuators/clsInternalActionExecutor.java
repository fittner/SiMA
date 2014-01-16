/**
 * @author Benny D�nz
 * 13.05.2009, 21:53:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators;

import java.util.ArrayList;

import properties.clsProperties;

import complexbody.io.clsSensorActuatorBaseExt;

import du.itf.actions.clsInternalActionCommand;


/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny D�nz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsInternalActionExecutor extends clsSensorActuatorBaseExt {
	public static final String P_ENERGYRELATION = "energyrelation";
	
	protected double srEnergyRelation;		//Relation energy to stamina
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 05.10.2009, 19:28:53
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsInternalActionExecutor(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = clsSensorActuatorBaseExt.getDefaultProperties(pre);

		oProp.setProperty(pre+P_ENERGYRELATION, 0.01);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		srEnergyRelation = poProp.getPropertyDouble(pre+P_ENERGYRELATION);
	}

	
	@Override
	protected abstract void setBodyPartId();
	@Override
	protected abstract void setName();
	
	
	/*
	 * Array of types of action commands which can not be performed at the 
	 * same time. This will be checked by the processor in a double loop 
	 * prior to execution. Commands of the same type automatically exclude 
	 * themselves, i.e. no two commands of the same type can be executed 
	 * in the same round.
	 */
	public ArrayList<Class<?>> getMutualExclusions(clsInternalActionCommand poCommand) {
		return new ArrayList<Class<?>>(); 
	}

	/*
	 * Get the amount of stamina needed per round to perform the action. Even 
	 * if the action can not be performed this amount of stamina will be consumed.
	 */
	public abstract double getStaminaDemand(clsInternalActionCommand poCommand);
	
	/*
	 * Get the amount of energy needed per round to perform the action. Even 
	 * if the action can not be performed this amount of energy will be consumed.
	 */
	public double getEnergyDemand(clsInternalActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}

	/*
	 * Check if the entity is in a state where the action can be performed, 
	 * e.g. no injuries, enough stamina, etc. and then executes the command.
	 * Returns true/false depending on if the action was successful.
	 */
	public abstract boolean execute(clsInternalActionCommand poCommand); 

	/*
	 * Support function for finding an entity in a given Range (Self-Referenced passed so entities own body can be ignored)
	 */
//	protected clsEntity findSingleEntityInRange(clsEntity poSelfReference, clsComplexBody poBody, eSensorExtType peSensor, Class<?> poInterface) {
//
//		ArrayList<clsCollidingObject> oSearch=null;
//		if (peSensor== eSensorExtType.EATABLE_AREA) oSearch = ((clsSensorEatableArea) poBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(peSensor)).getSensorData();
//		if (peSensor== eSensorExtType.MANIPULATE_AREA) oSearch = ((clsSensorManipulateArea) poBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(peSensor)).getSensorData();
//		if (peSensor== eSensorExtType.VISION) oSearch = ((clsSensorVision) poBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(peSensor)).getSensorData();
//		if (oSearch==null) return null;
//		
//		clsEntity oEntity=null;
//
//		for(int i=0; i<oSearch.size(); i++){
//			PhysicalObject2D poIntObject = oSearch.get(i).moCollider; 
//
//			clsEntity oIntEntity=null;
//			if (poIntObject instanceof clsMobileObject2D) {
//				oIntEntity = ((clsMobileObject2D) poIntObject).getEntity();
//			} else if (poIntObject instanceof clsStationaryObject2D) {
//				oIntEntity = ((clsStationaryObject2D) poIntObject).getEntity();
//			}
//			
//			if (oIntEntity !=null && oIntEntity != poSelfReference) {
//				if (poInterface.isAssignableFrom(oIntEntity.getClass())  ) {
//					if (oEntity !=null) return null;
//					oEntity=oIntEntity;
//				}
//			}
//		}
//
//		return oEntity;
//	}
//
//	/*
//	 * Support function for finding an entity in a given Range
//	 */
//	protected clsEntity findNamedEntityInRange(String EntityID, clsComplexBody poBody, eSensorExtType peSensor, Class<?> poInterface) {
//
//		ArrayList<clsCollidingObject> oSearch=null;
//		if (peSensor== eSensorExtType.EATABLE_AREA) oSearch = ((clsSensorEatableArea) poBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(peSensor)).getSensorData();
//		if (peSensor== eSensorExtType.VISION) oSearch = ((clsSensorVision) poBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(peSensor)).getSensorData();
//		if (oSearch==null) return null;
//		
//		clsEntity oEntity=null;
//
//		for(int i=0; i<oSearch.size(); i++){
//			clsCollidingObject poObject = oSearch.get(i);
//			PhysicalObject2D poIntObject = poObject.moCollider; 
//
//			clsEntity oIntEntity=null;
//			if (poIntObject instanceof clsMobileObject2D) {
//				oIntEntity = ((clsMobileObject2D) poIntObject).getEntity();
//			} else if (poIntObject instanceof clsStationaryObject2D) {
//				oIntEntity = ((clsStationaryObject2D) poIntObject).getEntity();
//			}
//			
//			if (oIntEntity !=null) {
//				if (poInterface.isAssignableFrom(oIntEntity.getClass()) && oIntEntity.getId()==EntityID  ) {
//					if (oEntity !=null) return null;
//					oEntity=oIntEntity;
//				}
//			}
//		}
//
//		return oEntity;
//	}

}
