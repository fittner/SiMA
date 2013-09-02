package du.itf.sensors;

public class clsAcoustic extends clsSensorRingSegment {
	
	@Override
	public String logHTML() {
		
		String oRetVal = "<tr><td>"+getSensorType().toString().toUpperCase()+"</td><td></td></tr>";
		
		oRetVal += super.logHTML();
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 21.08.2013 20:38:58
	 *
	 * @param oEntry
	 */
	public void add(clsAcousticEntry oEntry) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}	
}
