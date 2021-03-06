/**
 * 
 */
package complexbody.io.sensors.datatypes;

import java.util.Formatter;

/**
 * @author langr
 *
 */
public class clsStaminaSystem extends clsSensorIntern implements Cloneable {
	protected double mrStaminaValue;
	
	public double getStaminaValue() {
		return mrStaminaValue;
	}
	public void setStaminaValue(double prStaminaValue) {
		mrStaminaValue = prStaminaValue;
	}

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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsStaminaSystem oClone = (clsStaminaSystem)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
	
}
