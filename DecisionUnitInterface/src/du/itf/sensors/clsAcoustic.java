/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package du.itf.sensors;

import java.util.ArrayList;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 08.03.2013, 20:42:40
 * 
 */
public class clsAcoustic extends clsSensorExtern implements Cloneable {
	protected ArrayList<clsSensorExtern> moEntries = new ArrayList<clsSensorExtern>();
	
	public void add(clsAcousticEntry poEntry) {
		moEntries.add(poEntry);
	}
	
	public clsAcoustic() {
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsSensorExtern oEntry:moEntries) {
			logEntry += addXMLTag("Entry", ((clsAcousticEntry)oEntry).logXML(id));
			id++;
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsSensorExtern oEntry:moEntries) {
			oResult += i+". "+oEntry+" >> ";

			i++;
		}
		
		if (i>0) {
			oResult = oResult.substring(0, oResult.length()-4);
		}
		
		return oResult;
	}
	
	@Override
	public String logHTML() {
		
		String oRetVal = "";
		
		oRetVal = "<tr><td>"+getSensorType().toString().toUpperCase()+"</td><td></td></tr>";
		
		int i = 0;
		for (clsSensorExtern oEntry:moEntries) {
			oRetVal += "<tr><td align='right'>"+i+"</td><td>"+oEntry.logHTML()+"</td></tr>";
			i++;
		}
		
		return oRetVal;
	}
	
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		return moEntries;
	}

	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAcoustic oClone = (clsAcoustic)super.clone();

        	if (moEntries!=null) {
        		oClone.moEntries = new ArrayList<clsSensorExtern>();
        		for (clsSensorExtern oEntry:moEntries) {
        			oClone.moEntries.add( (clsSensorExtern)oEntry.clone());
        		}
        	}
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
