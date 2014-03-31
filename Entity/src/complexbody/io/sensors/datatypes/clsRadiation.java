package complexbody.io.sensors.datatypes;

import java.util.ArrayList;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsRadiation extends clsSensorExtern implements Cloneable {
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
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.10.2009, 12:52:29
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
	 * @author deutsch
	 * 20.10.2009, 12:52:29
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
        	clsRadiation oClone = (clsRadiation)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}

