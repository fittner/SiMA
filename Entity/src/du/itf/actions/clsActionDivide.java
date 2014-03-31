package du.itf.actions;

/**
 *  Beat
 *  Parameters
 * 	prForce = The force used to attack the entity   (Default ~4)
 * 
 * @author herret
 * 17.07.2013, 15:31:13
 * 
 */
public class clsActionDivide extends clsActionCommand {

	private double mrSplitFactor;

	public clsActionDivide(double prSplitFactor) {
		mrSplitFactor=prSplitFactor;
	}
	
	@Override
	public String getLog() {
		return "<Divide>" + mrSplitFactor + "</Devide>"; 
	}

	public double getSplitFactor() {
		return mrSplitFactor;
	}
	public void setSplitFactor(double prSplitFactor) {
		mrSplitFactor=prSplitFactor;
	}
}
