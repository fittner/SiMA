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
public class clsSensorRingSegment extends clsSensorExtern {

	private ArrayList<clsSensorRingSegmentEntries> moEntries = new ArrayList<clsSensorRingSegmentEntries>();
	
	public void add(clsSensorRingSegmentEntries poEntry) {
		moEntries.add(poEntry);
	}
	
	public ArrayList<clsSensorRingSegmentEntries> getList() {
		return moEntries;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsSensorRingSegmentEntries oEntry:moEntries) {
			logEntry += addXMLTag("Entry", oEntry.logXML(id));
			id++;
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsSensorRingSegmentEntries oEntry:moEntries) {
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
		for (clsSensorRingSegmentEntries oEntry:moEntries) {
			oRetVal += "<tr><td align='right'>"+i+"</td><td>"+oEntry.logHTML()+"</td></tr>";
			i++;
		}
		
		return oRetVal;
	}
}
