/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.LocalizationOrientation;

import sim.util.Bag;

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
	double beta_tolerance;
	double distance_tolerance;
	double heading_tolerance;
	double path_distance_tolerance;
	double path_heading_tolerance;
	
	/**
	 * 
	 * @param toleranceRadius
	 * @param toleranceHeading
	 * @param pathTransitionRadius
	 * @param pathHeadingTolerance
	 */
	public clsAssumptions(double toleranceRadius, double toleranceHeading, double pathTransitionRadius, double pathHeadingTolerance){
		//for testing fix tolerances
		distance_tolerance=toleranceRadius;
		heading_tolerance=toleranceHeading;
		path_distance_tolerance=pathTransitionRadius;
		path_heading_tolerance=pathHeadingTolerance;
	}
	
	/**
	 * from the previous area and previousPerceivedObjects the angles and the distances are calculated
	 * @param area : the area the Agent was residing bevor the transition
	 * @param objects : the objects that where in sight before the transition, 
	 * @param stepmemory : a list of all previous transitions, 
	 * @param currentPath : the path that is currently defined, used to check if the path is followed. if so the assumed goal area is the paths next area
	 * @param pathDirectionDifference : the directiondifference currentHeadingDirection - directiontoPlanedTransitionPoint 
	 * @return whether there is an expectation about the result of the current transition.
	 */
	public boolean makeAssumption(clsArea area,clsPerceivedObj objects, Bag stepMemory, clsPath currentPath, double pathDirectionDifference){
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
		//Do calculation according to thesis; using the distance and bearing infos and the law of cosine
		D=objects.getDistance(mainObject);
		E=objects.getDistance(secondaryObject);
		
/*	
//		if there is currently a path check if it is being followed. If yes the path goal is the most likely goal
		if (currentPath!=null){
			clsMove tmpMove = currentPath.getNextMove();
			distance = calcDistance(tmpMove.beta, tmpMove.B, D, tmpMove.A, E);
			//for testing. a path is been followed when no angle changings have to be done. NOT when the transition is near.
			//Reason for this is the situation that when following a path a new area appears befor the transition point is close.
			//this happened due to vision error. The vision system (not the vision analysis) sees different things when following a 
			//path in reverse direction. Do not know why. so if no angle adjustments have to be performed, the path is followed
			//if ( (distance<path_distance_tolerance) && ( (pathDirectionDifference<path_heading_tolerance) || (pathDirectionDifference>(360-path_heading_tolerance)) ) ){
			if ( (pathDirectionDifference<path_heading_tolerance) || (pathDirectionDifference>(360-path_heading_tolerance))  ){
					this.expectedAreaID= currentPath.getNextArea();
				return true;
			}
		}
		*/
		
		
		//search for transitions that occured in this area and that has a transition
		for (i=0;i<stepMemory.numObjs;i++){
			if (((((clsStep)(stepMemory.get(i))).getPrevArea() == currentArea) && (((clsStep)(stepMemory.get(i))).getNextarea() !=-1) && (((clsStep)(stepMemory.get(i))).getPathToNext() != null))||
					((((clsStep)(stepMemory.get(i))).getNextarea() == currentArea) && (((clsStep)(stepMemory.get(i))).getPrevArea() !=-1) && (((clsStep)(stepMemory.get(i))).getPathToPrev() != null))){
				potentialSteps.add(stepMemory.get(i));
			}
		}
		//for each possible transition, check if data is within the tolerance
		for (i=0;i<potentialSteps.numObjs;i++){
			if 	(((clsStep)(potentialSteps.get(i))).getPrevArea() == currentArea){
				headingDiff = Math.abs(((clsStep)(potentialSteps.get(i))).getPathToNext().direction - headingCurr);
				distance= calcDistance(((clsStep)(potentialSteps.get(i))).getPathToNext().beta, ((clsStep)(potentialSteps.get(i))).getPathToNext().B, D, ((clsStep)(potentialSteps.get(i))).getPathToNext().A, E);
			}
			else{
				if ((potentialSteps.get(i)==null) || (((clsStep)(potentialSteps.get(i))).getPathToPrev() == null))
					System.out.println("test");
				headingDiff = Math.abs(((clsStep)(potentialSteps.get(i))).getPathToPrev().direction - headingCurr);
				distance= calcDistance(((clsStep)(potentialSteps.get(i))).getPathToPrev().beta, ((clsStep)(potentialSteps.get(i))).getPathToPrev().B, D, ((clsStep)(potentialSteps.get(i))).getPathToPrev().A, E);
			}
			
			
			
			if ((distance > distance_tolerance) || (headingDiff > heading_tolerance)){
				potentialSteps.remove(i);
				i--;
			}
		}
		
		if (potentialSteps.numObjs==0)
			return false;
		
		//return the last in list because the latest is most likely to be the most accurate. Later on the has to a evaluation of the potentialSteps that
		//uses actuality of the steps, the time, the distance and the headingDiff
		expectedAreaID=((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getPrevArea()==currentArea ? ((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getNextarea() : ((clsStep)(potentialSteps.get(potentialSteps.numObjs-1))).getPrevArea();

		return true;
	}
	
	public double calcDistance(double beta, double B, double D, double A, double E){
		double gamma, lambda, F;
		gamma = Math.toDegrees( Math.acos(( Math.pow(D, 2)+Math.pow(B, 2)-Math.pow(E, 2))/(2*D*B )) );
		lambda = Math.abs(gamma - beta);
		
		F = Math.sqrt(Math.pow(D, 2) + Math.pow(A, 2) - (2*D*A*Math.cos(Math.toRadians(lambda))));
		return F;
	}
	
}
