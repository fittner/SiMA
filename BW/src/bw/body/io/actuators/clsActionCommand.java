/**
 * @author Benny Dönz
 * 15.04.2009, 15:25:16
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;
import bw.entities.clsEntity;

/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny Dönz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsActionCommand {

	/*
	 * Array of types of action commands which can not be performed at the 
	 * same time. This will be checked by the processor in a double loop 
	 * prior to execution. Commands of the same type automatically exclude 
	 * themselves, i.e. no two commands of the same type can be executed 
	 * in the same round.
	 */
	public ArrayList<Class<clsActionCommand>> getMutualExclusions() {
		return new ArrayList<Class<clsActionCommand>>(); 
	}
	
	/*
	 * Get the amount of energy needed per round to perform the action. Even 
	 * if the action can not be performed this amount of energy will be consumed.
	 */
	public double getEnergyDemand() {
		return 0;
	}

	/*
	 * Get the amount of stamina needed per round to perform the action. Even 
	 * if the action can not be performed this amount of stamina will be consumed.
	 */
	public double getStaminaDemand() {
		return 0;
	}

	/*
	 * Check if the entity is in a state where the action can be performed, 
	 * e.g. no injuries, enough stamina, etc. and then executes the command.
	 * Returns true/false depending on if the action was successful.
	 */
	public boolean execute(clsEntity poEntity) {
		return false;
	}	
			
}
