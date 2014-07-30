package complexbody.io.sensors.datatypes;

import java.util.ArrayList;
import java.util.Formatter;

import datatypes.clsAngle;


public class clsPositionChange extends clsSensorExtern implements Cloneable {
	protected double x=0;
	protected double y=0;
	protected double a=0;
	
	public double getX() {
		return x;
	}
	public void setX(double prX) {
		x = prX;
	}
	
	public double getY() {
		return y;
	}
	public void setY(double prY) {
		y = prY;
	}
	
	public double getA() {
		return a;
	}
	public void setA(double prA) {
		a = prA;
	}
	
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

		ArrayList<clsSensorExtern> oRetVal = new ArrayList<clsSensorExtern>();
		oRetVal.add(this);
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:47:33
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPositionChange oClone = (clsPositionChange)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}
