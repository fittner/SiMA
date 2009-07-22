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
import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import statictools.clsSingletonUniqueIdGenerator;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.body.io.itfGetBodyPartId;
import bw.body.io.itfGetName;
import bw.body.io.itfGetUniqueId;
import bw.entities.clsEntity;
import decisionunit.itf.actions.itfActionCommand;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.partclass.*;

/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny D�nz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsActionExecutor implements itfGetBodyPartId, itfGetName, itfGetUniqueId {
	
	protected static double srEnergyRelation = 0.2f;		//Relation energy to stamina
	
	protected clsPartActionEx moBodyPart;

	/* added by TD - replacement for obsolete parent class clsSensorActuatorBaseExt */
	private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
	protected eBodyParts mePartId;
	protected String moName;	

	/* added by TD - replacement for obsolete parent class clsSensorActuatorBaseExt */	
	protected abstract void setBodyPart();
	protected abstract void setBodyPartId();
	protected abstract void setName();
	
	/* added by TD - replacement for obsolete parent class clsSensorActuatorBaseExt */
	public eBodyParts getBodyPartId() {return mePartId;}
	public String getName() {return moName;}
	public int getUniqueId() {return mnUniqueId;}
	
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
	 * Support function for finding an entity in a given Range
	 */
	protected clsEntity findSingleEntityInRange(HashMap<Integer, PhysicalObject2D> poSearch, Class<?> poInterface) {
		clsEntity oEntity=null;

		Iterator<Integer> i = poSearch.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			PhysicalObject2D poIntObject =(PhysicalObject2D) poSearch.get(oKey); 

			clsEntity oIntEntity=null;
			if (poIntObject instanceof clsMobileObject2D) {
				oIntEntity = ((clsMobileObject2D) poIntObject).getEntity();
			} else if (poIntObject instanceof clsStationaryObject2D) {
				oIntEntity = ((clsStationaryObject2D) poIntObject).getEntity();
			}
			
			if (oIntEntity !=null ) {
				if (poInterface.isAssignableFrom(oIntEntity.getClass())  ) {
					if (oEntity !=null) return null;
					oEntity=oIntEntity;
				}
			}
		}

		return oEntity;
	}
	
}
