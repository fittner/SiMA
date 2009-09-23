package decisionunit.itf.sensors;

import java.util.Formatter;

public class clsTemperatureSystem  extends clsSensorIntern {
	
	public double mrTemperatureValue;
	
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

}
