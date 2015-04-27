package complexbody.expressionVariables;

import properties.clsProperties;

public class clsExpressionVariableCheeksRedning extends clsExpressionVariable{

	static int counter = 0;
	
	public clsExpressionVariableCheeksRedning() {
		super();
	}
	
	public clsExpressionVariableCheeksRedning(String poPrefix, clsProperties poProp){
		super(poPrefix, poProp);
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
		//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
	@Override
	public String getName(){
		return "CHEEKS_REDNING";
	}
}
