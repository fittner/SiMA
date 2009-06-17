/**
 * @author Benny Dönz
 * 13.05.2009, 21:53:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;

import statictools.clsSingletonUniqueIdGenerator;

import bw.entities.clsEntity;
import decisionunit.itf.actions.clsActionEat;
import decisionunit.itf.actions.itfActionCommand;

/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny Dönz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsActionExecutor {

	private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();

	public abstract String getName();
	
	public long getUniqueId() {
		return mnUniqueId;
	}
	
	/*
	 * Array of types of action commands which can not be performed at the 
	 * same time. This will be checked by the processor in a double loop 
	 * prior to execution. Commands of the same type automatically exclude 
	 * themselves, i.e. no two commands of the same type can be executed 
	 * in the same round.
	 */
	public ArrayList<Class<itfActionCommand>> getMutualExclusions(itfActionCommand poCommand) {
		return new ArrayList<Class<itfActionCommand>>(); 
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
	public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
		return false;
	}	
	
}
