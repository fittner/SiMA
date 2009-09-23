/**
 * 
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

/**
 * @author langr
 * 
 * Holds the information about the actual energy level within the stomach
 *
 */
public class clsEnergy extends clsSensorExtern {

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

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td>"+oDoubleFormatter .format("%.5f",mrEnergy)+"<td></td></tr>";
	}	
}
