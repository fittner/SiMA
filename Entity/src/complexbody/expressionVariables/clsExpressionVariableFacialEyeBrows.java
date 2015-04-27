package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariableFacialEyeBrows extends clsExpressionVariable{
	public static final String P_CORNERSUPDOWN = "corners_updown";
	public static final String P_CENTERUPDOWN = "center_updown";
	
	private double mrEyeBrowsCornersUpOrDown; // 0 down, 1 up
	private double mrEyeBrowsCenterUpOrDown; // 0 down, 1 up
	
	
	static int counter = 0;
	
	public clsExpressionVariableFacialEyeBrows() {
		
	}
	
	public clsExpressionVariableFacialEyeBrows(String poPrefix, clsProperties poProp){
		super(poPrefix, poProp);
		counter++;
		speakCounter();
	}
	
	@Override
	protected void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);

	    mrEyeBrowsCornersUpOrDown = poProp.getPropertyDouble(pre + P_CORNERSUPDOWN);		
		mrEyeBrowsCenterUpOrDown = poProp.getPropertyDouble(pre + P_CENTERUPDOWN);
	}
	
	void speakCounter(){
		//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
	
	public double getEyeBrowsCenterUpOrDown() {
		return mrEyeBrowsCenterUpOrDown;
	}

	public void setEyeBrowsCenterUpOrDown(double prEyeBrowsCenterUpOrDown) {
		if(prEyeBrowsCenterUpOrDown < 0){
			this.mrEyeBrowsCenterUpOrDown = 0.0;
		}
		else if(prEyeBrowsCenterUpOrDown > 1.0){
			this.mrEyeBrowsCenterUpOrDown = 1.0;
		}
		else{
			this.mrEyeBrowsCenterUpOrDown = prEyeBrowsCenterUpOrDown;
		}
	}

	public double getEyeBrowsCornersUpOrDown() {
		return mrEyeBrowsCornersUpOrDown;
	}

	public void setEyeBrowsCornersUpOrDown(double prEyeBrowsCornersUpOrDown) {
		if(prEyeBrowsCornersUpOrDown < 0){
			this.mrEyeBrowsCornersUpOrDown = 0.0;
		}
		else if(prEyeBrowsCornersUpOrDown > 1.0){
			this.mrEyeBrowsCornersUpOrDown = 1.0;
		}
		else{
			this.mrEyeBrowsCornersUpOrDown = prEyeBrowsCornersUpOrDown;
		}
	}
	
	@Override
	public String getName(){
		return "EYE_BROWS";
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre + P_CORNERSUPDOWN, "0.5"); // neutral
		oProp.setProperty(pre + P_CENTERUPDOWN , "0.5"); // neutral
		
		return oProp;
	}

	@Override
	public String toString() {
		String oStringRep = "Expression variable " + getName() + ": mrEyeBrowsCornersUpOrDown=" + Double.toString(mrEyeBrowsCornersUpOrDown) + ", mrEyeBrowsCornersUpOrDown=" + Double.toString(mrEyeBrowsCornersUpOrDown);
		
		return oStringRep;
	}
}
