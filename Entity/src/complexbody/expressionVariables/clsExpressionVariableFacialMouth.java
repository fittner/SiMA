package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariableFacialMouth extends clsExpressionVariable{
	public static final String P_MOUTHOPEN = "mouthopen";
	public static final String P_MOUTHSIDES = "mouthsides_updown";
	public static final String P_MOUTHSTRETCH = "mouthstretch";
	
	private double mrMouthOpen; // 0 fully opened, 1 completely closed
	private double mrMouthSidesUpOrDown; // -1 downwards. 0 line shaped, 1 upwards
	private double mrMouthStretchiness; // lip ends' state of stretchiness. 0 normal position, 1 max distance from center
	
	public clsExpressionVariableFacialMouth() {
		super();
	}
	
	public clsExpressionVariableFacialMouth(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
	}
	
	@Override
	protected void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		mrMouthOpen = poProp.getPropertyDouble(pre + P_MOUTHOPEN);		
		mrMouthSidesUpOrDown = poProp.getPropertyDouble(pre + P_MOUTHSIDES);
		mrMouthStretchiness = poProp.getPropertyDouble(pre + P_MOUTHSTRETCH);
	}

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

	@Override
	public String getName(){
		return "MOUTH";
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre + P_MOUTHOPEN, "1.0"); // closed
		oProp.setProperty(pre + P_MOUTHSIDES , "0.0"); // line shape
		oProp.setProperty(pre + P_MOUTHSTRETCH , "0.0"); // "normal" position
		
		return oProp;
	}
	
	@Override
	public String toString() {
		String oStringRep = "Expression variable " + getName() +
				": mrMouthOpen=" + Double.toString(mrMouthOpen) +
				", mrMouthSidesUpOrDown=" + Double.toString(mrMouthSidesUpOrDown) +
				", mrMouthStretchiness=" + Double.toString(mrMouthStretchiness);
		
		return oStringRep;
	}
}
