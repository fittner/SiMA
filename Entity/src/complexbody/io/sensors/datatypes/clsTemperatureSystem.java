package complexbody.io.sensors.datatypes;

import java.util.Formatter;

public class clsTemperatureSystem  extends clsSensorIntern implements Cloneable {
	protected double mrTemperatureValue;

	public double getTemperatureValue() {
		return mrTemperatureValue;
	}
	public void setTemperatureValue(double prTemperatureValue) {
		mrTemperatureValue = prTemperatureValue;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Temperature", new Double(mrTemperatureValue).toString()); 


		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Temperature "+mrTemperatureValue;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td><td>"+oDoubleFormatter .format("%.5f",mrTemperatureValue)+"</td></tr>";
	}			

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsTemperatureSystem oClone = (clsTemperatureSystem)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
