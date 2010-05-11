/**
 * 
 */
package du.itf.sensors;

import java.util.Formatter;

/**
 * @author langr
 *
 */
public class clsHealthSystem extends clsSensorIntern implements Cloneable {
	protected double mrHealthValue;
	
	public double getHealthValue() {
		return mrHealthValue;
	}
	public void setHealthValue(double prHealthValue) {
		mrHealthValue = prHealthValue;
	}
	
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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsHealthSystem oClone = (clsHealthSystem)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	

}
