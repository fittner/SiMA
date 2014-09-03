package complexbody.expressionVariables;

public class clsExpressionVariableFacialMouth extends clsExpressionVariable{
	
	private double mrMouthOpen; // 0 fully opened, 1 completely closed
	private double mrMouthSidesUpOrDown; // -1 downwards. 0 line shaped, 1 upwards
	private double mrMouthStretchiness; // lip ends' state of stretchiness. 0 normal position, 1 max distance from center
	
	/**
	 * 
	 * @return the mrMouthOpen
	 */
	public double getMouthOpen() {
		return mrMouthOpen;
	}
	/**
	 * 
	 * @param prMouthOpen the mrMouthOpen to set
	 */
	public void setMouthOpen(double prMouthOpen) {
		this.mrMouthOpen = prMouthOpen;
	}
	/**
	 * 
	 * @return the mrMouthSidesUpOrDown
	 */
	public double getMouthSidesUpOrDown() {
		return mrMouthSidesUpOrDown;
	}
	/**
	 * 
	 * @param prMouthSidesUpOrDown the mrMouthSidesUpOrDown to set
	 */
	public void setMouthSidesUpOrDown(double prMouthSidesUpOrDown) {
		this.mrMouthSidesUpOrDown = prMouthSidesUpOrDown;
	}
	/**
	 * 
	 * @return the mrMouthStretchiness
	 */
	public double getMouthStretchiness() {
		return mrMouthStretchiness;
	}
	/**
	 * 
	 * @param prMouthStretchiness the mrMouthStretchiness to set
	 */
	public void setMouthStretchiness(double prMouthStretchiness) {
		this.mrMouthStretchiness = prMouthStretchiness;
	}


}
