package complexbody.expressionVariables;

public class clsExpressionVariableGeneralSweat extends clsExpressionVariable{

	static int counter = 0;
	
	public clsExpressionVariableGeneralSweat(){
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
