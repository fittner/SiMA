package complexbody.expressionVariables;

public class clsExpressionVariableFacialEyeBrows extends clsExpressionVariable{
	
	private double mrEyeBrowsCornersUpOrDown; // 0 down, 1 up
	private double mrEyeBrowsCenterUpOrDown; // 0 down, 1 up
	
	
	static int counter = 0;
	
	public clsExpressionVariableFacialEyeBrows(){
		counter++;
		speakCounter();
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

}
