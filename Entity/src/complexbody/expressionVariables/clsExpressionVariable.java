package complexbody.expressionVariables;

public abstract class clsExpressionVariable {
	
	private double mrEIntensity;
	private int mnDuration;
	
	public clsExpressionVariable(){
		mrEIntensity = 0.0; // starting value
		mnDuration = 0;
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
		System.out.println( this.getClass().toString() + " -> triggering intensity: " + prIncomingIntensity);

		
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
	
} // end class clsExpressionVariable
