package complexbody.expressionVariables;

import properties.clsProperties;

public abstract class clsExpressionVariable {
	public static final String P_INTENSITY = "intensity";
	public static final String P_DURATION = "duration";
	
	private double mrEIntensity;
	private int mnDuration;
	private String name;
	
	public clsExpressionVariable(){
		mrEIntensity = 0.0;
		mnDuration = 0;
	}
	
	public clsExpressionVariable(String poPrefix, clsProperties poProp){
		applyProperties(poPrefix, poProp);
		name = getName();
	}

	protected void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);

	    mrEIntensity = poProp.getPropertyDouble(pre + P_INTENSITY);		
	    mnDuration = poProp.getPropertyInt(pre + P_DURATION);
	}
	
	public double getEIntensity() {
		return this.mrEIntensity;
	}
	
	public void triggerExpression(double prIncomingIntensity){
		
		if ( prIncomingIntensity < 0.0 ){ // incoming intensity cant be negative
			this.mrEIntensity = 0.0;
		}
		else if ( prIncomingIntensity > 1.0 ){
			this.mrEIntensity = 1.0;
		}
		else{
			this.mrEIntensity = prIncomingIntensity;
		}
		
		// delete these 2 lines. for testing only...
		//System.out.println( this.getClass().toString() + " -> triggering intensity: " + prIncomingIntensity);

		
		updateDuration();
		
	}
	
	public int getDuration(){
		updateDuration();
		return this.mnDuration;
	}
	public void updateDuration(){
		if (this.getEIntensity() > 0.0){
			this.mnDuration = 1; // as long as there is an effective intensity registered, effect will continue
		}
		else {
			this.mnDuration = 0;
		}
	}
	
	public abstract String getName();
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre + P_INTENSITY, "0.0"); // standard behavior is 0.0
		oProp.setProperty(pre + P_DURATION, "0"); // standard behavior is 0.0
		
		return oProp;
	}

	@Override
	public String toString() {
		String oStringRep = "Expression variable " + getName() + ": intensity=" + Double.toString(mrEIntensity) + ", duration=" + Integer.toString(mnDuration);
		
		return oStringRep;
	}
} // end class clsExpressionVariable
