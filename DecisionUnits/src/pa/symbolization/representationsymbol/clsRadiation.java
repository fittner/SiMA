package pa.symbolization.representationsymbol;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsRadiation extends clsSensorRingSegment {
	public double mrIntensity;
	
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

