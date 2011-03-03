/**
 * itfProcessor.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 03.03.2011, 14:33:52
 */
package pa;

import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 14:33:52
 * 
 */
public interface itfProcessor {
	public abstract void step();
	public abstract void getActionCommands(itfActionProcessor poActionContainer);
	public abstract void applySensorData(clsSensorData poData);

}
