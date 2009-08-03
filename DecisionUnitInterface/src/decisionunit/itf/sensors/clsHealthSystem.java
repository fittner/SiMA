/**
 * 
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

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

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td><td>"+oDoubleFormatter .format("%.2f",mrHealthValue)+"</td></tr>";
	}		

}
