/**
 * @author Benny D�nz
 * 13.05.2009, 21:53:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io.actuators;

import java.util.ArrayList;
import du.itf.actions.clsActionCommand;


/**
 * This abstract class must be inherited by all actions commands so they 
 * can be processed. The public constructor of a concrete action command 
 * should contain all relevant parameters, e.g. speed of movement etc. 
 * 
 * @author Benny D�nz
 * 15.04.2009, 15:25:16
 * 
 */
public abstract class clsActionExecutor  {
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 05.10.2009, 19:28:53
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsActionExecutor() {
//		super(poPrefix, poProp);
//		applyProperties(poPrefix, poProp);
	}
	
	
//	@Override
//	protected abstract void setBodyPartId();
//	@Override
//	protected abstract void setName();
//	
	
	/*
	 * Array of types of action commands which can not be performed at the 
	 * same time. This will be checked by the processor in a double loop 
	 * prior to execution. Commands of the same type automatically exclude 
	 * themselves, i.e. no two commands of the same type can be executed 
	 * in the same round.
	 */
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return new ArrayList<Class<?>>(); 
	}


	/*
	 * Check if the entity is in a state where the action can be performed, 
	 * e.g. no injuries, enough stamina, etc. and then executes the command.
	 * Returns true/false depending on if the action was successful.
	 */
	public abstract boolean execute(clsActionCommand poCommand);


	protected void setBodyPartId() {
		
		
	}

	protected void setName() {
		
		
	} 
}
