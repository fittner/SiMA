package decisionunit.itf.sensors;

import java.util.ArrayList;
import java.util.Formatter;

import bfg.tools.shapes.clsAngle;

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
		double newAngle = clsAngle.getNormalizedAngle(a, true, Math.PI);
		oResult += "@"+oDoubleFormatter.format("%.5f",newAngle)+"<td></td></tr>";
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 15:37:35
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#getDataObjects()
	 */
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 15:37:35
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 15:37:35
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#isContainer()
	 */
	@Override
	public boolean isContainer() {
		// TODO (zeilinger) - Auto-generated method stub
		return false;
	}

}
