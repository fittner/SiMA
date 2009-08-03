/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 * TODO - deutsch : what shall we do with all that arrays in the BW.FastMessengerClass???
 *
 */
public class clsFastMessengerSystem extends clsSensorIntern {

	@Override
	public String logXML() {
		String logEntry = "";
		
//		logEntry += addXMLTag("Energy", mrEnergy+""); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": ";
	}

	@Override
	public String logHTML() {
		return "<tr><td>"+getClassName()+"</td><td></td></tr>";
	}	
}
