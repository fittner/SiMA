/**
 * clsStomachTension.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author deutsch
 * 10.08.2009, 16:09:01
 */
package du.itf.sensors;

import java.util.Formatter;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 10.08.2009, 16:09:01
 * 
 */
public class clsStomachTension extends clsSensorIntern implements Cloneable {
	protected double mrTension;
	
	public double getTension() {
		return mrTension;
	}
	public void setTension(double prTension) {
		mrTension = prTension;
	}
	
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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsStomachTension oClone = (clsStomachTension)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
