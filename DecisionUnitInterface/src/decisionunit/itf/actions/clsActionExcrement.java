package decisionunit.itf.actions;

/**
 * Excrement command
 * Parameters
 * 	prIntensity = Intensity passed to clsCreateExcrement (inter body world system) (Default ~1)
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:31:13
 * 
 */

public class clsActionExcrement implements itfActionCommand {

	private double mrIntensity;

	public clsActionExcrement(double prIntensity) {
		mrIntensity=prIntensity;
	}
	
	public String getLog() {
		return "<Excrement>" + mrIntensity + "</Kill>"; 
	}

	public double getIntensity() {
		return mrIntensity;
	}
	public void setIntensity(double prIntensity) {
		mrIntensity=prIntensity;
	}
}