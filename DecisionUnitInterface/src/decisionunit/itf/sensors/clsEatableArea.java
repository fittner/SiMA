package decisionunit.itf.sensors;

public class clsEatableArea extends clsSensorRingSegment {
	
	
	@Override
	public String logHTML() {
		
		String oRetVal = "<tr><td>"+moSensorType.toString().toUpperCase()+"</td><td></td></tr>";
		
		oRetVal += super.logHTML();
		
		return oRetVal;
	}	
}
