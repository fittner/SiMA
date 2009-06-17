/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 * Actual energy consumption - the sum of all consumers
 *
 */
public class clsEnergyConsumption extends clsSensorHomeostasis {

	public float mrEnergy;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Energy", new Float(mrEnergy).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Energy "+mrEnergy;
	}		
}
