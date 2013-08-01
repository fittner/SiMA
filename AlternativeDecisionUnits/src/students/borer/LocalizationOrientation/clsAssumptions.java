/**
 * @author borer
 */
package students.borer.LocalizationOrientation;

import students.borer.Mason.Bag;

/**
 *  based on the provided environmental information it
 * performs an assumption in which area the agent will arrive when performing a  
 * transition
 * 
 * @author borer
 * 
 */
public class clsAssumptions {

	int expectedAreaID=-1;
	Bag expectedAreas=new Bag();
	double beta_tolerance;
	double distance_tolerance;
	double heading_tolerance;
	
	/**
	 * 
	 * @param toleranceRadius
	 * @param toleranceHeading
	 * @param pathTransitionRadius
	 * @param pathHeadingTolerance
	 */
	public clsAssumptions(double toleranceRadius, double toleranceHeading){
		//for testing fix tolerances
		distance_tolerance=toleranceRadius;
		heading_tolerance=toleranceHeading;
	}
	
	/**
	 * from the previous area and previousPerceivedObjects the angles and the distances are calculated
	 * @param forward : if true transitions are processed that have a connection to the current transition starting area, if false the ones that have a connection to the current transition ending area. the difference lies in how the heading direction is processed 
	 * @param area : the area the Agent was residing bevor the transition
	 * @param objects : the objects that where in sight before the transition, 
	 * @param stepmemory : a list of all previous transitions, 
	 * @param currentPath : the path that is currently defined, used to check if the path is followed. if so the assumed goal area is the paths next area
	 * @param pathDirectionDifference : the directiondifference currentHeadingDirection - directiontoPlanedTransitionPoint 
	 * @return whether there is an expectation about the result of the current transition.
	 */
	public boolean makeAssumption(boolean fromStartPoint,clsArea area,clsPerceivedObj objects, Bag stepMemory, clsPath currentPath, double pathDirectionDifference){
		if (area.getObjects().getNum()<2)
			return false;
		
		Bag potentialSteps = new Bag();
		int currentArea= area.getid();
		int i,mainObject=-1,secondaryObject=-1;
		double D, E, distance=-1, headingDiff,headingCurr = -1;
		
		//gather the neccessary data
		for (i=0;i<objects.getNum();i++){
			if (objects.getObject(i).equals(area.getObjects().getObject(0)))
				mainObject=i;
			if (objects.getObject(i).equals(area.getObjects().getObject(1)))
				secondaryObject=i;
		}
		if((mainObject==-1)||(secondaryObject==-1))
			return false;
		
		headingCurr = objects.getBearing(mainObject);
		if (!fromStartPoint)
			headingCurr = headingCurr-180<0 ? headingCurr-180+360: headingCurr-180;
		//Do calculation according to thesis; using the distance and bearing infos and the law of cosine
		D=objects.getDistance(mainObject);
		E=objects.getDistance(secondaryObject);
		
		//search for transitions that occured in this area and that has a transition
		for (i=0;i<stepMemory.numObjs;i++){
			int StepsNextArea = ((clsStep)(stepMemory.get(i))).getNextarea();
			int StepsPrevArea = ((clsStep)(stepMemory.get(i))).getPrevArea();
			if ( ((StepsPrevArea == currentArea) && (StepsNextArea !=-1) && (((clsStep)(stepMemory.get(i))).getPathToNext() != null) )||
					((StepsNextArea == currentArea) && (StepsPrevArea !=-1) && (((clsStep)(stepMemory.get(i))).getPathToPrev() != null)) ){
				potentialSteps.add(stepMemory.get(i));
			}
		}
		//for each possible transition, check if data is within the tolerance
		for (i=0;i<potentialSteps.numObjs;i++){
			//depending on which direction the transition is to be crossed, same direction as the first time or backwards, the headding difference has to be calculated differently
			if 	(((clsStep)(potentialSteps.get(i))).getPrevArea() == currentArea){
				headingDiff = Math.abs(((clsStep)(potentialSteps.get(i))).getPathToNext().direction - headingCurr);
				distance= calcDistance(((clsStep)(potentialSteps.get(i))).getPathToNext().beta, ((clsStep)(potentialSteps.get(i))).getPathToNext().B, D, ((clsStep)(potentialSteps.get(i))).getPathToNext().A, E);
			}
			else{
				headingDiff = Math.abs(((clsStep)(potentialSteps.get(i))).getPathToPrev().direction - headingCurr - 180) ;
				headingDiff = headingDiff>360?headingDiff-360:headingDiff;
				distance= calcDistance(((clsStep)(potentialSteps.get(i))).getPathToPrev().beta, ((clsStep)(potentialSteps.get(i))).getPathToPrev().B, D, ((clsStep)(potentialSteps.get(i))).getPathToPrev().A, E);
			}
			
			//check if the transition data fits the defined tolerances
			if ((distance > distance_tolerance) || ((headingDiff > heading_tolerance)&&(headingDiff<(360-heading_tolerance))) ){
				potentialSteps.remove(i);
				i--;
			}else{
				expectedAreas.add(new clsAssumptionResult((clsStep)potentialSteps.get(i),headingDiff,distance));
			}
		}
		
		if (potentialSteps.numObjs==0)
			return false;
		
		//return the last in list because the latest is most likely to be the most accurate. Later on the has to a evaluation of the potentialSteps that
		//uses actuality of the steps, the time, the distance and the headingDiff
		expectedAreaID=((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getPrevArea()==currentArea ? ((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getNextarea() : ((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getPrevArea();

		return true;
	}
	
	//get the best comparison result. in this case the first entry within the potential assumption list created by the makeAssumption function
	public clsAssumptionResult getAssumptioResult(){
		if (expectedAreas.numObjs==0){
			this.expectedAreas.clear();
			return null;
		}
		
		clsAssumptionResult tmpResult = (clsAssumptionResult)this.expectedAreas.get(0);
		this.expectedAreas.clear();
		return tmpResult; 
	}
	
	
	/**
	* calculated the distance from the current location to the memorized transition using the provided data (see master thesis section: link calculation)
	*
	* @param beta: angle between transition point-main object and main object - secondary object
	* @param B: distance main - secondary object
	* @param D: distance current location - main object
	* @param A: distance transition point - main object
	* @param E: distance current location  - secondary object
	*
	* @return the distance between the current location and the transition point
	*/
	public double calcDistance(double beta, double B, double D, double A, double E){
		double gamma, lambda, F;
		gamma = Math.toDegrees( Math.acos(( Math.pow(D, 2)+Math.pow(B, 2)-Math.pow(E, 2))/(2*D*B )) );
		lambda = Math.abs(gamma - beta);
		
		F = Math.sqrt(Math.pow(D, 2) + Math.pow(A, 2) - (2*D*A*Math.cos(Math.toRadians(lambda))));
		return F;
	}
	
}