/**
 * @author borer
 */
package LocalizationOrientation;

import enums.eEntityType;
import episodicMemory.*;
import sim.util.Bag;

/**
 * This class is the main interface for the localization,
 * the entity can call one of several functions here
 * Orientate: to update the orientation with new data
 * planPath: for path planing to a desired area
 * PathGetDirection: to receive the angle change that has to be performed to get to the current goal area
 * getCurrArea: to get the ID of the current area
 * @author  borer
 */
public class clsLocalization {
	
	int areacount=0;
	
	
	private clsAreaSemanticMemory SemMemory = new clsAreaSemanticMemory();
	private clsDeadReckoning DeadReckoning = new clsDeadReckoning();
	private clsPathPlanning PathPlanning = new clsPathPlanning();
	private clsMemory episodicMemory;
	
	public Bag potentialCurrentAreas;
	public int currAreaID=-1;
	public int prevAreaID=-1;
	int relativeHeadingDirection;
	private clsStep currentStep=null;
	//private clsStep previousStep=null;
	public boolean madeTransition=false;
	public boolean madeAssumption=false;
	public boolean madeCorrectAss=false;
	public boolean ArrivedAtGoal=false;
	
	public clsPath tmpPath;
	//private clsStep pathCurrentStep;
	private int currentGoalArea=1;

	
	private clsPerceivedObj previousPerceivedObj=null;
	
	//For Adjustment
	double similarObjectsTolerance = 0.3; // 0.01 - 1; 0.01->no deviation at all
	double similarObjectBearingTolerance= 0.01;
	double ObjcetbeeringTolerance = 90;	//  0 < x <= 360	size of discrete angular segments
	double areaEvaluationUp = 1.4;
	double areaEvaluationDown = 0.6;
	private clsAssumptions assumtionSystem=new clsAssumptions(5.,30.);
	
	
	
	public int stat_assumptionsMade=0;
	public int stat_transitionsMade=0;
	public int stat_downevaluationsMemory=0;
	public int stat_downevaluationsAssSys=0;
	public int stat_areacount=0;
	
	
	public  clsLocalization(clsMemory moMemory){
		episodicMemory=moMemory;
	}
	
	
	/**
	* The main orientation function. triggers the location knowledge update, the link encoding. the assumption triggering,
	*
	* @param PerceivedObj: list of Objects in sight with their location data
	*/
	public void orientate(clsPerceivedObj PerceivedObj){
	
		currentStep=new clsStep();	
		madeTransition=false;
		madeAssumption=false;
		madeCorrectAss=false;
		ArrivedAtGoal=false;
		
//		only calc if there exist objects in the environment
		if (PerceivedObj.getNum()>0){
			clsArea tempArea;
			//if an Area was encoded
			if( ( tempArea = calc_area(PerceivedObj))!=null ){
				 potentialCurrentAreas = SemMemory.rememberArea(tempArea,similarObjectsTolerance, similarObjectBearingTolerance, ObjcetbeeringTolerance);
				 currAreaID= ((clsArea)potentialCurrentAreas.get(0)).getid();
				 //statistic
				 stat_areacount=SemMemory.getSize();
				 
				//if area boarder is crossed
				if (this.currAreaID != prevAreaID){
					madeTransition=true;
					//statistic
					stat_transitionsMade++;
					
						//here we can not use the tempArea to calc the link point because the main objects might not be the same as in the remembered area 
						if (this.currAreaID > 0)
							currentStep.setPathToPrev(DeadReckoning.calcLink(SemMemory.getAreaEntry(this.currAreaID-1),PerceivedObj));
						//update the previous step if there is one
						if (this.prevAreaID > 0){
							currentStep.setPathToNext(DeadReckoning.calcLink(SemMemory.getAreaEntry(this.prevAreaID-1),previousPerceivedObj));
							
							
							
							//2 ASSUMPTIONS, once for goal and starting area of the current transition. To cover all possibilities
							assumtionSystem.makeAssumption(true, SemMemory.getAreaEntry(this.prevAreaID-1), previousPerceivedObj, filterStepHistory(episodicMemory), tmpPath, (tmpPath==null ? 0 : PathGetDirection(previousPerceivedObj))); 
							assumtionSystem.makeAssumption(false, SemMemory.getAreaEntry(this.currAreaID-1), PerceivedObj, filterStepHistory(episodicMemory), tmpPath, (tmpPath==null ? 0 : PathGetDirection(previousPerceivedObj))); 
							clsAssumptionResult AssumptionResult=assumtionSystem.getAssumptioResult();
							if(AssumptionResult!=null){
								
								//statistic
								stat_assumptionsMade++;
								
								System.out.println("!!Assumption made!! Traveling between " + AssumptionResult.getTransitionStartArea() + " and "+ AssumptionResult.getTransitionGoalArea());
								madeAssumption=true;
								//check starting and goal area for necessary update
								if (AssumptionResult.getTransitionStartArea() == this.prevAreaID || AssumptionResult.getTransitionStartArea()==this.currAreaID){
//									1.4 has about the same inverted gain as 0.6
									this.SemMemory.updateUpToDateness(areaEvaluationUp, AssumptionResult.getTransitionStartArea());
									madeCorrectAss=true;
								}else{
									//as the expected ID was not reached it might be gone and thus the upToDatenes is lowered
									//0.6 means the upToDateness has dropped to about the half after 2 wrong assertions
									this.SemMemory.updateUpToDateness(areaEvaluationDown, AssumptionResult.getTransitionStartArea());
									System.out.println("Evaluation Down for Area "+ AssumptionResult.getTransitionStartArea() +" due to Assumption System");
									//statistic
									stat_downevaluationsAssSys++;
								}
								
								if (AssumptionResult.getTransitionGoalArea() == this.prevAreaID || AssumptionResult.getTransitionGoalArea()==this.currAreaID){
									this.SemMemory.updateUpToDateness(areaEvaluationUp, AssumptionResult.getTransitionGoalArea());
									madeCorrectAss=true;
								}else{
									this.SemMemory.updateUpToDateness(areaEvaluationDown, AssumptionResult.getTransitionGoalArea());
									System.out.printf("Evaluation Down for Area "+ AssumptionResult.getTransitionGoalArea() +" due to Assumption System");
//									statistic
									stat_downevaluationsAssSys++;
								}
							}
							
							//EVALUATION depending on area matching perfection with the memorized area.
							if (!SemMemory.imperfectMatch){
								this.SemMemory.updateUpToDateness(areaEvaluationUp, this.currAreaID);
								madeCorrectAss=true;
							}else{
								this.SemMemory.updateUpToDateness(areaEvaluationDown, this.currAreaID);
								System.out.println("Evaluation Down for Area "+ this.currAreaID +" due to Memory System");
//								statistic
								stat_downevaluationsMemory++;
							}
								
						}
						
						 //check if part of the path is passed
						 if (tmpPath!=null){
							 if(couldIBeThere(tmpPath.getNextArea())){
									if (couldIBeThere(currentGoalArea)){
										System.out.println("SUCCESS: Arrived in goal area");
										ArrivedAtGoal=true;
										tmpPath=null;
									}else{
										/*//if the path is beeing followed get the next step
										pathCurrentStep=tmpPath.getNextStep();
										if (pathCurrentStep==null){
											tmpPath=null;
											System.out.println("Path ends Here");
											}*/
										//WARNING HIGHLY INEFFICIENT
										planPath(this.currentGoalArea);
									}
							 }else if(tmpPath.getNextMove().reached){
								 System.out.println("ERROR: Not following planed path");
								 tmpPath=null;
							 }
							}
						
			//Debug Robert Borer
					System.out.println("I am in Area " + currAreaID);
					System.out.printf("Looks also like: ");
					for (int i=0;i<potentialCurrentAreas.numObjs;i++)
						System.out.printf("%d , ",((clsArea)potentialCurrentAreas.get(i)).getid());
					System.out.printf("\n");
					
				}
			}else{
				currAreaID=-1;
			}
		}else{
			currAreaID=-1;
		}
		
		/*even if there are no boarder crossings, the steps are always 
		kept up to date because they are used in an episodic memory event for identifying the area an event occured*/
		currentStep.setCurrentArea(currAreaID);
		currentStep.setPrevArea(prevAreaID);
		currentStep.setNextarea(currAreaID);
		
		//this.previousStep=this.currentStep;
		
		
		prevAreaID=currAreaID;
		previousPerceivedObj=PerceivedObj;
	}
	
	
	/*Hier wird ein satz von objekten in sicht in eine area umgewandelt 
	 * vorausgesetzt es ist ein referenzobjekt vorhanden
	 * 
	 * @param tmpPerceivedObj: list of objects currently in sight. including location data 
	 * 
	 * @return data saved within an clsArea container.
	 * */
	
	private clsArea calc_area(clsPerceivedObj tmpPerceivedObj){
		clsPerceivedObj PerceivedObj=tmpPerceivedObj.clone();
		
		int j,k,mainpos=0;
		clsArea newArea = new clsArea(PerceivedObj.getNum());
		
		//extract the non Landmark Objects; in this case only cake
		for (k=0;k<PerceivedObj.getNum();k++){
			
			if (! (PerceivedObj.getObject(k).getEntityType()==eEntityType.STONE)  ){
				//at the moment no walls allowed. just cake
					if ((PerceivedObj.getObject(k).getEntityType() == eEntityType.CAKE))
						newArea.addNonLandmarkObject(PerceivedObj.getObject(k));
					PerceivedObj.removeObject(k);
					k--;
			}
		}
					
		
		if (PerceivedObj.getNum() > 0){

			//set a unique object in first position
			//first: find a unique object
			for ( k=0;k<PerceivedObj.getNum();k++){
				mainpos=k;
				for ( j=0;j<PerceivedObj.getNum();j++)
					if ((PerceivedObj.getObject(j).equals(PerceivedObj.getObject(mainpos))) && (mainpos!=j))
						break;
				if (j>=PerceivedObj.getNum())
					break;
			}
			
			if (mainpos!=0){
//				set the mainobject to first position to ease future calculations
				Object tmpobj=PerceivedObj.getObject(0);
				double tmpbearing = PerceivedObj.getBearing(0),tmpdistance = PerceivedObj.getDistance(0);
				
				PerceivedObj.getObjects().set(0, PerceivedObj.getObjects().get(mainpos));
				PerceivedObj.getObjects().set(mainpos, tmpobj);
				PerceivedObj.setBearing(0, PerceivedObj.getBearing(mainpos));
				PerceivedObj.setBearing(mainpos, tmpbearing);
				PerceivedObj.setDistance(0, PerceivedObj.getDistance(mainpos));
				PerceivedObj.setDistance(mainpos, tmpdistance);
				
			}else if (mainpos >= PerceivedObj.getNum())
				mainpos=0;
			
			
			for ( j=1;j<PerceivedObj.getNum();j++)
					PerceivedObj.setBearing(j, (PerceivedObj.getBearing(0)>PerceivedObj.getBearing(j)) ? 
													(PerceivedObj.getBearing(j)-PerceivedObj.getBearing(0)+360) : 
													(PerceivedObj.getBearing(j)-PerceivedObj.getBearing(0))
													);
			PerceivedObj.setBearing(0, 0);
			newArea.setObjects(PerceivedObj);

			return newArea;
		}else{	//wenn kein referenzobjekt vorhanden ist 
			return null;
		}
	}
	
	public int getCurrentArea(){
		return this.currAreaID;
	}
	
	public clsStep getCurrStep(){
		return this.currentStep;
	}
	
	
	public boolean planPath(int GoalAreaID){
		currentGoalArea=GoalAreaID;
		tmpPath= PathPlanning.calculate(this.getCurrentArea(),GoalAreaID,filterStepHistory(episodicMemory), SemMemory);
		
		if(tmpPath!=null){
			if (tmpPath.getNextArea()==this.currAreaID)
				tmpPath.getNextStep();
			//Debug output
			tmpPath.String();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Returns the direction to go to for following the calculated path
	 *
	 * @param objects:  list of objects currently in sight. including location data 
	 *
	 * @return the angle in degree that has to be added to the current traveling direction to follow the Path. 
	 * if return value is greater than 360 -> error
	 */
	public double PathGetDirection(clsPerceivedObj objects){
		clsArea area = SemMemory.getAreaEntry(this.currAreaID-1);
		if ((objects.getNum()<2) || (area.getObjects().getNum()<2)){
			System.out.println("ORIENTATION ERROR: Cannot calc direction from here\n");
			return 361;
		}
		
		if (this.tmpPath==null){
			System.out.printf("ORIENTATION ERROR: No Path found   -    RECALC   ->   ");
			if (planPath(this.currentGoalArea)){
				System.out.printf("SUCCESS\n");
			}else{
				System.out.printf("FAILED\n");
				return -1;
			}
		}
		
		double D, E, F, gamma, lambda, epsilon, direction=0;
		int i,mainObject=-1,secondaryObject=-1;
		
		
		//get the link that is to take next.
		clsMove tmpMove=tmpPath.getNextMove();
		//search main object and save distance to it in D
		for (i=0;i<objects.getNum();i++){
			if (objects.getObject(i).equals(area.getObjects().getObject(0)))
				mainObject=i;
			if (objects.getObject(i).equals(area.getObjects().getObject(1)))
				secondaryObject=i;
		}
		
		if((mainObject==-1)||(secondaryObject==-1)){
			System.out.println("ORIENTATION ERROR: Orientation Landmarks missing (no main or secondary object)\n");
			return 361;
		}
		
		//Do calculation according to thesis; using the distance and bearing infos and the law of cosine
		D=objects.getDistance(mainObject);
		E=objects.getDistance(secondaryObject);
//debug Robert Borer hier war ein fehler NULLPOINTER EXCEPTION
if(tmpMove==null){
	System.out.printf("ERROR:Can not find nextMove");
	return 361;
}
		//due to imprcise calculation and measurement the following expression might be slightl >1 but this can not be
		double tmp= ( Math.pow(D, 2)+Math.pow(tmpMove.B, 2)-Math.pow(E, 2))/(2*D*tmpMove.B );
		tmp= tmp>1 ? 1:tmp;
		tmp= tmp<-1 ? -1:tmp;
		
		gamma = Math.toDegrees( Math.acos(tmp) );
		lambda = (gamma - tmpMove.beta);
		
		F = Math.sqrt(Math.pow(D, 2) + Math.pow(tmpMove.A, 2) - (2*D*tmpMove.A*Math.cos(Math.toRadians(lambda))));
		
		epsilon = Math.toDegrees( Math.acos((Math.pow(F, 2) + Math.pow(D, 2) - Math.pow(tmpMove.A, 2))/(2*F*D)) );
		
		//winkel anpassen
		//System.out.printf("distance %2.2f\n", F);
		if (lambda>0)
			epsilon=epsilon*(-1);
		if (tmpMove.alpha<180)
			epsilon=epsilon*(-1);
		direction = objects.getBearing(mainObject)+epsilon;
		

		//if the agent is very close to transition point, use the passing direction. distance has to be adjusted as needed
		if (F<2)
			tmpMove.reached=true;	
		
		if (tmpMove.reached){
			direction=(360-tmpMove.direction)+objects.getBearing(mainObject);
			//passing direction is recorded at every transition if the transition is used backwards the direction has to be inverted
			if (!tmpPath.isFwPath)
				direction-=180;
		}
		
//		System.out.println(direction + "Grad");
		if (direction<0)
			direction+=360;
		if (direction>360)
			direction-=360;
		
		
		return direction;
	}
	
	
	/**
	*filters all the step information from the episodic memory
	*
	* @param eMemory: the episodic memory
	*
	* @return a list of steps extrcted from the episodic memory
	*/
	private Bag filterStepHistory(clsMemory eMemory){
		Bag stepHistory= new Bag();
		int i;
		clsStep tmpStep;
		//filter all steps from the Episodic memory that contain a transition
		for (i=0;i<eMemory.moAlarmSystem.moMemory.size();i++){
			tmpStep =  ( (clsElementStep) ( (clsEvent)eMemory.moAlarmSystem.moMemory.getObject(i) ).getFeatureStep().getFeatureElements().get(0) ).getStep();
			if (tmpStep.getPrevArea() != tmpStep.getNextarea());
				stepHistory.add(tmpStep);
		}
		
		return stepHistory;
	}
	
	
	/**
	*spontaniously plans a path to a food source
	*
	* @return true if a path was found, false if not
	*/
	public boolean spontaniousFoodSearch(){
		Bag foodAreas = findFoodArea();
		if (foodAreas.numObjs!=0){
			int i,best=0;
			double bestquality=10000,quality;
			Bag stepHistory = filterStepHistory(episodicMemory);
			clsPath newPath,bestPath=null;
			
			for (i=0;i<foodAreas.numObjs;i++){
				 newPath=PathPlanning.calculate(this.getCurrentArea(),((clsArea)foodAreas.get(i)).getid(),stepHistory , SemMemory);
				 if (newPath==null)
					 continue;
					 
				 quality=newPath.getPathQuality((clsAreaSemanticMemory)SemMemory);
				 if (quality<bestquality){
					 bestquality=quality;
					 bestPath=newPath;
					 best=i;
				 }
				 
			}
			tmpPath=bestPath;
			
			if (tmpPath==null)
				return false;

			if (couldIBeThere(tmpPath.getNextArea()))
				tmpPath.getNextStep();
			//Debug output
			tmpPath.String();
			this.currentGoalArea=((clsArea)foodAreas.get(best)).getid();
			return true;
		}
		
		return false;
	}
	
	
	/**
	* searches for an area that contains a food source
	*
	* @return a list of areas that have a food source
	*/
	private Bag findFoodArea(){
		int i;
		Bag Areas=new Bag();
		
		for (i=this.SemMemory.getSize()-1;i>=0;i--){
			if (SemMemory.getAreaEntry(i).hasFood())
				Areas.add(SemMemory.getAreaEntry(i));
		}
		return Areas;
	}
	
	public boolean AmIInFoodArea(){
		//if (SemMemory.getAreaEntry(currAreaID-1).hasFood())
		if (currAreaID==currentGoalArea)
			return true;
		return false;
	}
	
	public boolean couldIBeThere(int AreaId){
		for (int i=0;i<potentialCurrentAreas.numObjs;i++){
			if (((clsArea)potentialCurrentAreas.get(i)).getid() == AreaId)
				return true;
		}
		
		return false;
	}
	
}

