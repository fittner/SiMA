/**
 * 
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

/**
 * @author langr
 *
 */
public class clsStaminaSystem extends clsSensorHomeostasis {

	public double mrStaminaValue;

	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Stamina", new Double(mrStaminaValue).toString()); 


		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Stamina "+mrStaminaValue;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td><td>"+oDoubleFormatter .format("%.5f",mrStaminaValue)+"</td></tr>";
	}		
	
}
