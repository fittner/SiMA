/**
 * clsSlowMessenger.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author deutsch
 * 23.09.2009, 13:21:28
 */
package decisionunit.itf.sensors;

import java.util.HashMap;
import java.util.Map;

import enums.eSlowMessenger;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.09.2009, 13:21:28
 * 
 */
public class clsSlowMessenger extends clsSensorIntern implements Cloneable {
	protected HashMap<eSlowMessenger, Double> moSlowMessengerValues = new HashMap<eSlowMessenger, Double>();

	public HashMap<eSlowMessenger, Double> getSlowMessengerValues() {
		return moSlowMessengerValues;
	}
	public void setSlowMessengerValues(HashMap<eSlowMessenger, Double> poSlowMessengerValues) {
		moSlowMessengerValues = poSlowMessengerValues;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:21:37
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#logHTML()
	 */
	@Override
	public String logHTML() {
		String oRetVal = "<tr><td>"+getClassName()+"</td><td></td></tr>";
		
		for (Map.Entry<eSlowMessenger, Double> entry:moSlowMessengerValues.entrySet()) {
			oRetVal += "<tr><td align=right>"+entry.getKey().name()+"</td><td>"+entry.getValue()+"</td></tr>";
		}

		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:21:37
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#logXML()
	 */
	@Override
	public String logXML() {
		String logEntry = "";
		int id = 0;
		
		for (Map.Entry<eSlowMessenger, Double> entry:moSlowMessengerValues.entrySet()) {
			String tmp_entry = addXMLTag("key",entry.getKey().name())+addXMLTag("value",""+entry.getValue());
			logEntry += addXMLTag("Entry", tmp_entry);
			id++;
		}
		
		return addXMLTag(logEntry);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:21:37
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#toString()
	 */
	@Override
	public String toString() {
		String oResult = getClassName()+": ";
		
		int i = 0;
		
		for (Map.Entry<eSlowMessenger, Double> entry:moSlowMessengerValues.entrySet()) {
			oResult += entry.getKey().name()+": "+entry.getValue()+" | ";

			i++;
		}
		
		if (i>0) {
			oResult = oResult.substring(0, oResult.length()-3);
		}
		
		return oResult;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSlowMessenger oClone = (clsSlowMessenger)super.clone();
        	
        	if (moSlowMessengerValues != null) {
        		oClone.moSlowMessengerValues = new HashMap<eSlowMessenger, Double>();
        		
        		for (Map.Entry<eSlowMessenger, Double> entry:moSlowMessengerValues.entrySet()) {
        			oClone.moSlowMessengerValues.put(entry.getKey(), entry.getValue());
        		}        		
        	}

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
