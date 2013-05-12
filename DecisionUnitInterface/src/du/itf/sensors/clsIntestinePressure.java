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
public class clsIntestinePressure extends clsSensorIntern implements Cloneable {
	protected double mrPressue;
	
	public double getPressure() {
		return mrPressue;
	}
	public void setPressure(double prTension) {
		mrPressue = prTension;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Pressure", new Double(mrPressue).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Pressure "+mrPressue;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td>"+oDoubleFormatter .format("%.5f",mrPressue)+"<td></td></tr>";
	}	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsIntestinePressure oClone = (clsIntestinePressure)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
