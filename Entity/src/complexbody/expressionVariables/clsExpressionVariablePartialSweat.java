package complexbody.expressionVariables;

public class clsExpressionVariablePartialSweat extends clsExpressionVariable{
	
	static int counter = 0;
	
	public clsExpressionVariablePartialSweat(){
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
		System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
}
