package decisionunit.itf.sensors;

import java.util.ArrayList;

public class clsRadiation extends clsSensorExtern {
	private ArrayList<clsRadiationEntry> moEntries = new ArrayList<clsRadiationEntry>();
	
	public void add(clsRadiationEntry poEntry) {
		moEntries.add(poEntry);
	}
	
	public ArrayList<clsRadiationEntry> getList() {
		return moEntries;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsRadiationEntry oEntry:moEntries) {
			logEntry += addXMLTag("Entry", oEntry.logXML(id));
			id++;
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsRadiationEntry oEntry:moEntries) {
			oResult += i+". "+oEntry+" >> ";

			i++;
		}
		
		if (i>0) {
			oResult = oResult.substring(0, oResult.length()-4);
		}
		
		return oResult;
	}	
}
