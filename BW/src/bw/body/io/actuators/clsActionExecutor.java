/**
 * @author Benny D�nz
 * 13.05.2009, 21:53:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.clsComplexBody;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.entities.clsEntity;
import decisionunit.itf.actions.itfActionCommand;
import enums.eSensorExtType;
import bw.body.io.sensors.ext.clsSensorEatableArea;
import bw.body.io.sensors.ext.clsSensorManipulateArea;
import bw.body.io.sensors.ext.clsSensorVision;

/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny D�nz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsActionExecutor extends clsSensorActuatorBaseExt {
	
	protected static double srEnergyRelation = 0.2f;		//Relation energy to stamina
	
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
	public ArrayList<Class<?>> getMutualExclusions(itfActionCommand poCommand) {
		return new ArrayList<Class<?>>(); 
	}
	
	/*
	 * Get the amount of energy needed per round to perform the action. Even 
	 * if the action can not be performed this amount of energy will be consumed.
	 */
	public double getEnergyDemand(itfActionCommand poCommand) {
		return 0;
	}

	/*
	 * Get the amount of stamina needed per round to perform the action. Even 
	 * if the action can not be performed this amount of stamina will be consumed.
	 */
	public double getStaminaDemand(itfActionCommand poCommand) {
		return 0;
	}

	/*
	 * Check if the entity is in a state where the action can be performed, 
	 * e.g. no injuries, enough stamina, etc. and then executes the command.
	 * Returns true/false depending on if the action was successful.
	 */
	public boolean execute(itfActionCommand poCommand) {
		return false;
	}	

	/*
	 * Support function for finding an entity in a given Range (Self-Referenced passed so entities own body can be ignored)
	 */
	protected clsEntity findSingleEntityInRange(clsEntity poSelfReference, clsComplexBody poBody, eSensorExtType peSensor, Class<?> poInterface) {

		ArrayList<clsCollidingObject> oSearch=null;
		if (peSensor== eSensorExtType.EATABLE_AREA) oSearch = ((clsSensorEatableArea) poBody.getExternalIO().moSensorExternal.get(peSensor)).getSensorData();
		if (peSensor== eSensorExtType.MANIPULATE_AREA) oSearch = ((clsSensorManipulateArea) poBody.getExternalIO().moSensorExternal.get(peSensor)).getSensorData();
		if (peSensor== eSensorExtType.VISION) oSearch = ((clsSensorVision) poBody.getExternalIO().moSensorExternal.get(peSensor)).getSensorData();
		if (oSearch==null) return null;
		
		clsEntity oEntity=null;

		for(int i=0; i<oSearch.size(); i++){
			PhysicalObject2D poIntObject = oSearch.get(i).moCollider; 

			clsEntity oIntEntity=null;
			if (poIntObject instanceof clsMobileObject2D) {
				oIntEntity = ((clsMobileObject2D) poIntObject).getEntity();
			} else if (poIntObject instanceof clsStationaryObject2D) {
				oIntEntity = ((clsStationaryObject2D) poIntObject).getEntity();
			}
			
			if (oIntEntity !=null && oIntEntity != poSelfReference) {
				if (poInterface.isAssignableFrom(oIntEntity.getClass())  ) {
					if (oEntity !=null) return null;
					oEntity=oIntEntity;
				}
			}
		}

		return oEntity;
	}

	/*
	 * Support function for finding an entity in a given Range
	 */
	protected clsEntity findNamedEntityInRange(String EntityID, clsComplexBody poBody, eSensorExtType peSensor, Class<?> poInterface) {

		ArrayList<clsCollidingObject> oSearch=null;
		if (peSensor== eSensorExtType.EATABLE_AREA) oSearch = ((clsSensorEatableArea) poBody.getExternalIO().moSensorExternal.get(peSensor)).getSensorData();
		if (peSensor== eSensorExtType.VISION) oSearch = ((clsSensorVision) poBody.getExternalIO().moSensorExternal.get(peSensor)).getSensorData();
		if (oSearch==null) return null;
		
		clsEntity oEntity=null;

		for(int i=0; i<oSearch.size(); i++){
			clsCollidingObject poObject = oSearch.get(i);
			PhysicalObject2D poIntObject = poObject.moCollider; 

			clsEntity oIntEntity=null;
			if (poIntObject instanceof clsMobileObject2D) {
				oIntEntity = ((clsMobileObject2D) poIntObject).getEntity();
			} else if (poIntObject instanceof clsStationaryObject2D) {
				oIntEntity = ((clsStationaryObject2D) poIntObject).getEntity();
			}
			
			if (oIntEntity !=null) {
				if (poInterface.isAssignableFrom(oIntEntity.getClass()) && oIntEntity.getId()==EntityID  ) {
					if (oEntity !=null) return null;
					oEntity=oIntEntity;
				}
			}
		}

		return oEntity;
	}

}
