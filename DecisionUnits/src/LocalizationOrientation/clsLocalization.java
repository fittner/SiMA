/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package LocalizationOrientation;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.entities.clsStone;
import memory.clsElementStep;
import memory.clsEvent;
import memory.clsMemory;
import sim.util.Bag;

/**
 * TODO (borer) - insert description 
 * @author  borer
 */
public class clsLocalization {
	
	int areacount=0;
	
	
	private clsAreaSemanticMemory SemMemory = new clsAreaSemanticMemory();
	private clsDeadReckoning DeadReckoning = new clsDeadReckoning();
	private clsPathPlanning PathPlanning = new clsPathPlanning();
	private clsMemory episodicMemory;
	
	int currAreaID=-1;
	int prevAreaID=-1;
	int relativeHeadingDirection;
	private clsStep currentStep=null;
	//private clsStep previousStep=null;
	public boolean madeTransition=false;
	
	public clsPath tmpPath;
	//private clsStep pathCurrentStep;
	private int currentGoalArea=1;

	
	private clsPerceivedObj previousPerceivedObj=null;
	
	//For Adjustment
	double similarObjectsTolerance = 0.01; // 0.01 - 1; 0.01->no deviation at all
	double similarObjcetbeeringTolerance = 45;	//  0 < x <= 360	size of discrete angular segments
	double areaEvaluationUp = 1.4;
	double areaEvaluationDown = 0.6;
	private clsAssumptions assumtionSystem=new clsAssumptions(1.,30., 0.5, 5);
	
	public  clsLocalization(clsMemory moMemory){
		episodicMemory=moMemory;
	}
	
	
	//emotions not yet implemented
	public void orientate(clsPerceivedObj PerceivedObj){
	
		currentStep=new clsStep();	
		madeTransition=false;
		
//		only calc if there exist objects in the environment
		if (PerceivedObj.getNum()>0){
			clsArea tempArea;
			//if an Area was encoded
			if( ( tempArea = calc_area(PerceivedObj))!=null ){
				 currAreaID = SemMemory.rememberArea(tempArea,similarObjectsTolerance, similarObjcetbeeringTolerance);
				 
				//if area boarder is crossed
				if (this.currAreaID != prevAreaID){
					madeTransition=true;
					
						//here we can not use the tempArea to calc the link point because the main objects might not be the same as in the remembered area 
						if (this.currAreaID > 0)
							currentStep.setPathToPrev(DeadReckoning.calcLink(SemMemory.getAreaEntry(this.currAreaID-1),PerceivedObj));
						//update the previous step if there is one
						if (this.prevAreaID > 0){
							currentStep.setPathToNext(DeadReckoning.calcLink(SemMemory.getAreaEntry(this.prevAreaID-1),previousPerceivedObj));
							if(assumtionSystem.makeAssumption(SemMemory.getAreaEntry(this.prevAreaID-1), previousPerceivedObj, filterStepHistory(episodicMemory), tmpPath, (tmpPath==null ? 0 : PathGetDirection(previousPerceivedObj))) ){
							//Debug Robert Borer
								System.out.printf("Seems like going to area:"+assumtionSystem.expectedAreaID+ "  ");
								if (assumtionSystem.expectedAreaID!=this.currAreaID){
									//as the expected ID was not reached it might be gone and thus the upToDatenes is lowered
									//0.6 means the upToDateness has dropped to about the half after 2 wrong assertions
									this.SemMemory.updateUpToDateness(areaEvaluationDown, assumtionSystem.expectedAreaID);
									System.out.printf("Evaluation Down for Area "+ assumtionSystem.expectedAreaID+" ");
								}else{
									//1.4 has about the same inverted gain as 0.6
									this.SemMemory.updateUpToDateness(areaEvaluationUp, assumtionSystem.expectedAreaID);
								}
							}
						}
						
						 //check if part of the path is passed
						 if (tmpPath!=null){
							 if(tmpPath.getNextArea()==currAreaID){
									if (currAreaID==currentGoalArea){
										System.out.println("SUCCESS: Arrived in goal area");
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
	 * ÅEergabewert: die listen an gesichteten Objekten
	 * 
	 * Rückgabewert: die erstelle Area oder Null falls kein Stationary 
	 * objet vorhanden ist, das die Area identifizieren könnte
	 * */
	
	private clsArea calc_area(clsPerceivedObj tmpPerceivedObj){
		clsPerceivedObj PerceivedObj=tmpPerceivedObj.clone();
		
		int j,k,mainpos=0;
		clsArea newArea = new clsArea(PerceivedObj.getNum());
		
		//extract the non Landmark Objects; in this case only cake
		for (k=0;k<PerceivedObj.getNum();k++){
			if (PerceivedObj.getObject(k).moObject instanceof clsMobileObject2D){
				if (! (( (clsMobileObject2D)PerceivedObj.getObject(k).moObject ).getEntity() instanceof clsStone) ){
					newArea.addNonLandmarkObject(PerceivedObj.getObject(k));
					PerceivedObj.removeObject(k);
				}
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
		
		if((mainObject==-1)||(secondaryObject==-1))
			return 361;
		
		//Do calculation according to thesis; using the distance and bearing infos and the law of cosine
		D=objects.getDistance(mainObject);
		E=objects.getDistance(secondaryObject);
//debug Robert Borer hier war ein fehler NULLPOINTER EXCEPTION
if(tmpMove==null)
	System.out.printf("ERROR:Can not find nextMove");
		gamma = Math.toDegrees( Math.acos(( Math.pow(D, 2)+Math.pow(tmpMove.B, 2)-Math.pow(E, 2))/(2*D*tmpMove.B )) );
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
	
	public boolean spontaniousFoodSearch(){
		
		int foodArea=findFoodArea();
		
		//DEBUG
		System.out.println("FOOD IN AREA "+foodArea);
		
		if (foodArea!=-1)
			return planPath(foodArea);
		
		return false;
	}
	
	
	private int findFoodArea(){
		int i;
		for (i=this.SemMemory.getSize()-1;i>=0;i--){
			if (SemMemory.getAreaEntry(i).hasFood())
				return i;
		}
		return -1;
	}
	
	
}


