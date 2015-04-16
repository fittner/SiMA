package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariablePartialSweat extends clsExpressionVariable{
	
	static int counter = 0;
	
	public clsExpressionVariablePartialSweat() {
		super();
	}
	
	public clsExpressionVariablePartialSweat(String poPrefix, clsProperties poProp){
		super(poPrefix, poProp);
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
	//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
	@Override
	public String getName(){
		return "PARTIAL_SWEAT";
	}
	
}
