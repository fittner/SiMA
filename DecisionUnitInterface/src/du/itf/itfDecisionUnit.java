/**
 * itfDecisionUnit.java: DecisionUnitInterface - decisionunit
 * 
 * @author deutsch
 * 11.05.2010, 10:21:33
 */
package du.itf;

import du.enums.eDecisionType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.05.2010, 10:21:33
 * 
 */
public interface itfDecisionUnit {
	public void process();
	public void setActionProcessor(itfActionProcessor poActionProcessor);
	public void update(clsSensorData poSensorData);
	public void updateActionLogger();
	public eDecisionType getDecisionUnitType();
}
