package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariableFacialEyes extends clsExpressionVariable{
	static int counter = 0;
	
	void speakCounter(){
		//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
	
	public clsExpressionVariableFacialEyes() {
		super();
	}
	
	public clsExpressionVariableFacialEyes(String poPrefix, clsProperties poProp){
		super(poPrefix, poProp);
		counter++;
		speakCounter();
	}
	
	
	public double getCrying() {
		return getEIntensity();
	}
	public void setCrying(double prCrying) {
		// delete this line. for testing only...
		System.out.println( this.getClass().toString() + " -> setting crying intensity: " + prCrying);

		triggerExpression(prCrying);
	}
	
	@Override
	public String getName(){
		return "EYES_CRYING_INTENSITY";
	}
}
