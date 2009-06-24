package decisionunit.itf.sensors;

public class clsPositionChange extends clsSensorExtern {

	public double x=0;
	public double y=0;
	public double a=0;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += clsDataBase.addXMLTag("x", new Float(x).toString()); 
		logEntry += clsDataBase.addXMLTag("y", new Float(y).toString()); 
		logEntry += clsDataBase.addXMLTag("a", new Float(a).toString()); 
		
		return addXMLTag(logEntry);
	}

	@Override
	public String toString() {

		return getClassName()+": "+x+"/"+y+"@"+a;
	}

}
