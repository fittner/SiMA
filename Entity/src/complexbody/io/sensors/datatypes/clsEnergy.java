/**
 * 
 */
package complexbody.io.sensors.datatypes;

import java.util.Formatter;

/**
 * @author langr
 * 
 * Holds the information about the actual energy level within the stomach
 *
 */
public class clsEnergy extends clsSensorIntern implements Cloneable{
	protected double mrEnergy;
	
	public double getEnergy() {
		return mrEnergy;
	}
	public void setEnergy(double prEnergy) {
		mrEnergy = prEnergy;
	}
	
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

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsEnergy oClone = (clsEnergy)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
}
