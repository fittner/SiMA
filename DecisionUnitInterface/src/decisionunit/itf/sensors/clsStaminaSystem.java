/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 */
public class clsStaminaSystem extends clsSensorHomeostasis {

	public float mrStaminaValue;

	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Stamina", new Float(mrStaminaValue).toString()); 


		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Stamina "+mrStaminaValue;
	}		
	
}
