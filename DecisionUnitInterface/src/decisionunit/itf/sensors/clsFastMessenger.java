/**
 * 
 */
package decisionunit.itf.sensors;

import java.util.ArrayList;

/**
 * @author langr
 *
 * TODO - deutsch : what shall we do with all that arrays in the BW.FastMessengerClass???
 *
 */
public class clsFastMessenger extends clsSensorIntern {
	public ArrayList<clsFastMessengerEntry> moEntries = new ArrayList<clsFastMessengerEntry>();
	
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (clsFastMessengerEntry oEntry:moEntries) {
			logEntry += addXMLTag("Entry", oEntry.logXML(id));
			id++;
		}
		
		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (clsFastMessengerEntry oEntry:moEntries) {
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
		for (clsFastMessengerEntry oEntry:moEntries) {
			oRetVal += "<tr><td align='right'>"+i+"</td><td>"+oEntry.logHTML()+"</td></tr>";
			i++;
		}
		
		return oRetVal;
	}

}
