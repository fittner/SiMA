/**
 * clsSensorManipulateArea.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 */
package decisionunit.itf.sensors;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 * 
 */
public class clsSensorManipulateArea extends clsSensorRingSegment {
	
	@Override
	public String logHTML() {
		
		String oRetVal = "<tr><td>"+moSensorType.toString().toUpperCase()+"</td><td></td></tr>";
		
		oRetVal += super.logHTML();
		
		return oRetVal;
	}	
}