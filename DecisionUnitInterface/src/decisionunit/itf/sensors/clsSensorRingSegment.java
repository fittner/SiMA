/**
 * clsSensorRingSegment.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author langr
 * 09.09.2009, 13:56:28
 */
package decisionunit.itf.sensors;

import java.util.ArrayList;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:56:28
 * 
 */
public class clsSensorRingSegment extends clsSensorExtern implements Cloneable, itfIsContainer {
	private ArrayList<clsSensorExtern> moEntries = new ArrayList<clsSensorExtern>();
	
	public void add(clsSensorRingSegmentEntries poEntry) {
		moEntries.add(poEntry);
	}
	
	public ArrayList<clsSensorExtern> getList() {
		return moEntries;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsSensorExtern oEntry:moEntries) {
			logEntry += addXMLTag("Entry", ((clsSensorRingSegmentEntries)oEntry).logXML(id));
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
		
		int i = 0;
		for (clsSensorExtern oEntry:moEntries) {
			oRetVal += "<tr><td align='right'>"+i+"</td><td>"+oEntry.logHTML()+"</td></tr>";
			i++;
		}
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 13:58:18
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#getDataObjects()
	 */
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		// TODO (zeilinger) - Auto-generated method stub
		return moEntries;
	}

	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSensorRingSegment oClone = (clsSensorRingSegment)super.clone();

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
