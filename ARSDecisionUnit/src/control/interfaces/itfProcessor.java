/**
 * CHANGELOG
 * 
 * 2011/07/11 TD - added javadoc comments. code sanitation.
 */
package control.interfaces;

import communication.datatypes.clsDataContainer;

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
	 * Provide the currently perceived sensor data to the decision unit.
	 *
	 * @since 11.07.2011 16:58:03
	 *
	 * @param poData
	 */
	public abstract void applySensorData(clsDataContainer poData);
	
		
	public clsDataContainer getActions();
	public clsDataContainer getInternalActions();

}
