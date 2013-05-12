/**
 * itfDecisionUnit.java: DecisionUnitInterface - decisionunit
 * 
 * @author deutsch
 * 11.05.2010, 10:21:33
 */
package du.itf;

import java.util.ArrayList;
import java.util.HashMap;

import du.enums.eDecisionType;
import du.itf.actions.itfActionProcessor;
import du.itf.actions.itfInternalActionProcessor;
import du.itf.sensors.clsInspectorPerceptionItem;
import du.itf.sensors.clsSensorData;

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
	 * Set the reference to the action processes. Designed using the command processor design pattern.
	 *
	 * @since 06.07.2011 12:50:57
	 *
	 * @param poActionProcessor
	 */
	public void setActionProcessor(itfActionProcessor poActionProcessor);
	
	//same for internal actions
	public void setInternalActionProcessor(itfInternalActionProcessor poInternalActionProcessor);
	
	/**
	 * Updates the stored sensor data to the incoming values.
	 *
	 * @since 06.07.2011 12:51:30
	 *
	 * @param poSensorData
	 */
	public void update(clsSensorData poSensorData);
	
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
	
	
	public HashMap<String, ArrayList<clsInspectorPerceptionItem>> getPerceptionInspectorData();
}
