package du.itf.actions;

/**
 * Excrement command
 * Parameters
 * 	prIntensity = Intensity passed to clsCreateExcrement (inter body world system) (Default ~1)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */

public class clsActionExcrement extends clsActionCommand {

	private double mrIntensity;

	public clsActionExcrement(double prIntensity) {
		mrIntensity=prIntensity;
	}
	
	@Override
	public String getLog() {
		return "<Excrement>" + mrIntensity + "</Excrement>"; 
	}

	public double getIntensity() {
		return mrIntensity;
	}
	public void setIntensity(double prIntensity) {
		mrIntensity=prIntensity;
	}
}
