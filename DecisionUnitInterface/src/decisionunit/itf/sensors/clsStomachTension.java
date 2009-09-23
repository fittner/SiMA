/**
 * clsStomachTension.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author deutsch
 * 10.08.2009, 16:09:01
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 10.08.2009, 16:09:01
 * 
 */
public class clsStomachTension extends clsSensorIntern {

	public double mrTension;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Tension", new Double(mrTension).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Tension "+mrTension;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td>"+oDoubleFormatter .format("%.5f",mrTension)+"<td></td></tr>";
	}	
}
