package pa.symbolization.representationsymbol;

public class clsVision extends clsSensorRingSegment {
	
	
	@Override
	public String logHTML() {
		
		String oRetVal = "<tr><td>"+getSensorType().toString().toUpperCase()+"</td><td></td></tr>";
		
		oRetVal += super.logHTML();
		
		return oRetVal;
	}	
}
