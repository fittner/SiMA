/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 * 
 * Holds the information about the actual energy level within the stomach
 *
 */
public class clsStomachSystem extends clsSensorHomeostasis {

	public double mrEnergy;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Energy", new Double(mrEnergy).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Energy "+mrEnergy;
	}	
}
