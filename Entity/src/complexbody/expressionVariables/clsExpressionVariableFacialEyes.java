package complexbody.expressionVariables;

public class clsExpressionVariableFacialEyes extends clsExpressionVariable{

	private double mrCrying;

	static int counter = 0;
	
	void speakCounter(){
		System.out.println("FindMeVst - " + this.getClass().getName() + " counter: " + counter);
	}
	
	
	public clsExpressionVariableFacialEyes(){
		mrCrying = 0;
		counter++;
		speakCounter();
	}
	
	
	public double getCrying() {
		return mrCrying;
	}
	public void setCrying(double prCrying) {

		// delete this line. for testing only...
		System.out.println( this.getClass().toString() + " -> setting crying intensity: " + prCrying);

		
		this.mrCrying = prCrying;
	}
	
	@Override
	public String getName(){
		return "EYES";
	}

}
