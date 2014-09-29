
package complexbody.expressionVariables;


public class clsExpressionVariableShake extends clsExpressionVariable{

	static int counter = 0;
	
	public clsExpressionVariableShake(){
		counter++;
		speakCounter();
	}
	
	void speakCounter(){
		//System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	@Override
	public String getName(){
		return "SHAKE_INTENSITY";
	}
}
