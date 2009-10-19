package decisionunit.itf.sensors;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsRadiation extends clsSensorRingSegment {
	protected double mrIntensity;
	
	public double getIntensity() {
		return mrIntensity;
	}
	public void setIntensity(double prIntensity) {
		mrIntensity = prIntensity;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("mrIntensity", new Double(mrIntensity).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": intensity "+mrIntensity;
	}

	@Override
	public String logHTML() {
		return "<tr><td>"+getClassName()+"</td><td>"+mrIntensity+"</td></tr>";
	}
}

