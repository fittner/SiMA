package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariableGeneralSweat extends clsExpressionVariable{

	static int counter = 0;
	
	public clsExpressionVariableGeneralSweat(String poPrefix, clsProperties poProp){
		super(poPrefix, poProp);
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
		//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	@Override
	public String getName(){
		return "GENERAL_SWEAT";
	}
}
