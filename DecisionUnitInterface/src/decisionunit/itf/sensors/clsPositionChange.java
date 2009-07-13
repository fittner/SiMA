package decisionunit.itf.sensors;

public class clsPositionChange extends clsSensorExtern {

	public double x=0;
	public double y=0;
	public double a=0;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += clsDataBase.addXMLTag("x", new Double(x).toString()); 
		logEntry += clsDataBase.addXMLTag("y", new Double(y).toString()); 
		logEntry += clsDataBase.addXMLTag("a", new Double(a).toString()); 
		
		return addXMLTag(logEntry);
	}

	@Override
	public String toString() {

		return getClassName()+": "+x+"/"+y+"@"+a;
	}

}
