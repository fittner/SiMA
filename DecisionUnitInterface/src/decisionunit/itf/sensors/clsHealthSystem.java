/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 */
public class clsHealthSystem extends clsSensorHomeostasis {

	public double mrHealthValue;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Health", new Double(mrHealthValue).toString()); 


		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Health "+mrHealthValue;
	}		

}
