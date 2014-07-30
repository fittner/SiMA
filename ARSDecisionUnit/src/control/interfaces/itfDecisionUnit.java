/**
 * itfDecisionUnit.java: DecisionUnitInterface - decisionunit
 * 
 * @author deutsch
 * 11.05.2010, 10:21:33
 */
package control.interfaces;


import base.clsCommunicationInterface;

import du.enums.eDecisionType;
/**
 * The interface that a decision unit has to implement to be compatible with the brain socket.
 * 
 * @author deutsch
 * 11.05.2010, 10:21:33
 * 
 */
public interface itfDecisionUnit {
	/**
	 * Processes the incoming data and produces the action commands. Also known as "thinking".
	 *
	 * @since 06.07.2011 12:50:55
	 *
	 */
	public void process();
	
	
	

	/**
	 * Update the action logger. Add the currently selected actions to the log.
	 *
	 * @since 06.07.2011 12:51:32
	 *
	 */
	public void updateActionLogger();
	
	/**
	 * Returns the type of the decision unit. 
	 *
	 * @since 06.07.2011 12:51:34
	 *
	 * @return
	 */
	public eDecisionType getDecisionUnitType();
	
	
	
	public void setBodyDataInterface(clsCommunicationInterface poInterface);
	
	public void setControlInterface(clsCommunicationInterface poInterface);
}
