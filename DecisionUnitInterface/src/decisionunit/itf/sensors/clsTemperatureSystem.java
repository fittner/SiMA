package decisionunit.itf.sensors;

public class clsTemperatureSystem  extends clsSensorHomeostasis {
	
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

}
