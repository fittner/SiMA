package decisionunit.itf.sensors;

import java.util.ArrayList;

public class clsVision extends clsSensorExtern {
	private ArrayList<clsVisionEntry> moEntries = new ArrayList<clsVisionEntry>();
	
	public void add(clsVisionEntry poEntry) {
		moEntries.add(poEntry);
	}
	
	public ArrayList<clsVisionEntry> getList() {
		return moEntries;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		for (clsVisionEntry oEntry:moEntries) {
			logEntry += addXMLTag("Entry", oEntry.logXML());
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsVisionEntry oEntry:moEntries) {
			oResult += i+". "+oEntry+" >> ";

			i++;
		}
		
		if (i>0) {
			oResult = oResult.substring(0, oResult.length()-4);
		}
		
		return oResult;
	}	
}
