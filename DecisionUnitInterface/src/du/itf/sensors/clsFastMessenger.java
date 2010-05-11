/**
 * 
 */
package du.itf.sensors;

import java.util.ArrayList;

/**
 * @author langr
 *
 * TODO - deutsch : what shall we do with all that arrays in the BW.FastMessengerClass???
 *
 */
public class clsFastMessenger extends clsSensorIntern implements Cloneable {
	protected ArrayList<clsFastMessengerEntry> moEntries = new ArrayList<clsFastMessengerEntry>();
	
	public ArrayList<clsFastMessengerEntry> getEntries() {
		return moEntries;
	}
	public void setEntries(ArrayList<clsFastMessengerEntry> poEntries) {
		moEntries = poEntries;
	}	
	
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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsFastMessenger oClone = (clsFastMessenger)super.clone();
        	
        	if (moEntries != null) {
        		oClone.moEntries = new ArrayList<clsFastMessengerEntry>();
	    		for (clsFastMessengerEntry oEntry:moEntries) {
	    			moEntries.add( (clsFastMessengerEntry)oEntry.clone() );
	    		}
        	}
    		
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	

}
