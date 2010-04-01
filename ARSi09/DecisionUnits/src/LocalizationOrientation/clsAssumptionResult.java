/**
 * @author borer
 */
package LocalizationOrientation;


public class clsAssumptionResult {

	public clsStep Step;
	public double headingDiff, distance;
	//private boolean fromStartPoint;
	
	public clsAssumptionResult (clsStep newStep, double newheadingDiff,double newdistance){
		
		this.Step=newStep;
		this.headingDiff=newheadingDiff;
		this.distance=newdistance;
		//this.fromStartPoint=fromStartPoint;
	}
	
	public int getTransitionStartArea(){
		return this.Step.getPrevArea();
	}
	
	public int getTransitionGoalArea(){
		return this.Step.getNextarea();
	}
	
}
