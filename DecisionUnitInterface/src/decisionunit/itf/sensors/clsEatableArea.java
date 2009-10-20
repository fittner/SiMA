package decisionunit.itf.sensors;

import java.util.ArrayList;

public class clsEatableArea extends clsSensorRingSegment{
	protected ArrayList<clsEatableAreaEntry> moEntries = new ArrayList<clsEatableAreaEntry>();
	
	public ArrayList<clsEatableAreaEntry> getEntries() {
		return moEntries;
	}
	public void setEntries(ArrayList<clsEatableAreaEntry> poEntries) {
		moEntries = poEntries;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsDataBase oEntry:moEntries) {
			logEntry += addXMLTag("Entry", ((clsEatableAreaEntry)oEntry).logXML(id));
			id++;
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsEatableAreaEntry oEntry:moEntries) {
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
		
		String oRetVal = "<tr><td>"+getClassName()+"</td><td></td></tr>";
		
		int i = 0;
		for (clsEatableAreaEntry oEntry:moEntries) {
			oRetVal += "<tr><td align='right'>"+i+"</td><td>"+oEntry.logHTML()+"</td></tr>";
			i++;
		}
		
		return oRetVal;
	}
}
