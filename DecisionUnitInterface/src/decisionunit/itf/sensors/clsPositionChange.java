package decisionunit.itf.sensors;

import java.util.Formatter;

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

	@Override
	public String logHTML() {
		String oResult = "";
		Formatter oDoubleFormatter = new Formatter();
		oResult += "<tr><td>"+getClassName()+"</td>"+oDoubleFormatter.format("%.5f",x);
		oDoubleFormatter = new Formatter();
		oResult += "/"+oDoubleFormatter.format("%.5f",y);
		oDoubleFormatter = new Formatter();
		oResult += "@"+oDoubleFormatter.format("%.5f",a)+"<td></td></tr>";
		
		return oResult;
	}

}
