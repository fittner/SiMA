package complexbody.expressionVariables;

public class clsExpressionVariableCheeksRedning extends clsExpressionVariable{

	static int counter = 0;
	
	public clsExpressionVariableCheeksRedning(){
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
		System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
}
