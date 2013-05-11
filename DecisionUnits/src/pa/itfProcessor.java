/**
 * CHANGELOG
 * 
 * 2011/07/11 TD - added javadoc comments. code sanitation.
 */
package pa;

import java.util.ArrayList;
import java.util.HashMap;

import du.itf.actions.itfActionProcessor;
import du.itf.actions.itfInternalActionProcessor;
import du.itf.sensors.clsInspectorPerceptionItem;
import du.itf.sensors.clsSensorData;

/**
 * The interface for the different implementations of the psychoanalytically inspired decision units. The three methods are to be called in the following
 * order: applySensorData(); step(); getActionCommands(); 
 * 
 * @author deutsch
 * 03.03.2011, 14:33:52
 * 
 */
public interface itfProcessor {
	/**
	 * Execute the implementation/do the deliberation. All modules of the decision unit are processed. 
	 *
	 * @since 11.07.2011 16:56:38
	 *
	 */
	public abstract void step();
	
	/**
	 * Return the result of the deliberation.
	 *
	 * @since 11.07.2011 16:57:26
	 *
	 * @param poActionContainer
	 */
	public abstract void getActionCommands(itfActionProcessor poActionContainer);
	
	
	public abstract void getInternalActionCommands(itfInternalActionProcessor poInternalActionContainer);
	
	/**
	 * Provide the currently perceived sensor data to the decision unit.
	 *
	 * @since 11.07.2011 16:58:03
	 *
	 * @param poData
	 */
	public abstract void applySensorData(clsSensorData poData);
	
	
	/**
	 * Return the result of the deliberation.
	 *
	 * @since 11.07.2011 16:57:26
	 *
	 * @param poActionContainer
	 */
	public abstract HashMap<String, ArrayList<clsInspectorPerceptionItem>> getPerceptionInspectorData();

}
