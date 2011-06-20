/**
 * itfReturnActionCommands.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 15:02:31
 */
package pa._v19.interfaces;

import du.itf.actions.itfActionProcessor;

/**
 * 
 * @author deutsch
 * 11.08.2009, 15:02:31
 * 
 */
@Deprecated
public interface itfReturnActionCommands {
	public void getActionCommands(itfActionProcessor poActionContainer); //call by reference
}
