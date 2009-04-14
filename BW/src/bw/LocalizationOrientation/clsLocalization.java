/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.LocalizationOrientation;

import bw.memory.clsElementStep;
import bw.memory.clsEvent;
import bw.memory.clsFeatureStep;
import bw.memory.clsMemory;
import java.util.HashMap;
import java.util.Iterator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.physicalObject.StationaryObject2D;
import sim.physics2D.util.Double2D;
import sim.util.Bag;

/**
 * TODO (borer) - insert description 
 * @author  borer
 */
public class clsLocalization {
	
	int areacount=0;
	double similarObjectsTolerance = 0.01; // 0.01 - 1; 0.01->no deviation at all
	double similarObjcetbeeringTolerance = 40;	//  0 < x <= 360	size of discrete angular segments
	
	private clsAreaSemanticMemory SemMemory = new clsAreaSemanticMemory();
	private clsDeadReckoning DeadReckoning = new clsDeadReckoning();
	private clsPathPlanning PathPlanning = new clsPathPlanning();
	private
	
	int currAreaID=-1;
	int prevAreaID=-1;
	int relativeHeadingDirection;
	private clsStep currentStep=null;
	private clsStep previousStep=null;
	public clsPath tmpPath;
	private clsStep pathCurrentStep;
	private clsPerceivedObj previousPerceivedObj=null;
	private clsAssumptions assumtionSystem=new clsAssumptions(1.,30., 0.2, 5);
	
	
	
	//emotions not yet implemented
	public void orientate(clsPerceivedObj PerceivedObj,clsMemory episodicMemory){
	
		currentStep=new clsStep();	
		
//		only calc if there exist objects in the environment
		if (PerceivedObj.getNum()>0){
			clsArea tempArea;
			//if an Area was encoded
			if( ( tempArea = calc_area(PerceivedObj))!=null ){
				 currAreaID = SemMemory.rememberArea(tempArea,similarObjectsTolerance, similarObjcetbeeringTolerance);
				//if area boarder is crossed
				if (this.currAreaID != prevAreaID){
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
									this.SemMemory.updateUpToDateness(0.6, assumtionSystem.expectedAreaID);
								}else{
									//1.4 has about the same inverted gain as 0.6
									this.SemMemory.updateUpToDateness(1.4, assumtionSystem.expectedAreaID);
								}
							}
						}
						
						//if there is a path planed
						if (tmpPath!=null){
							//	check if the path is beeing followed
							if (!((tmpPath.getPrevArea() == prevAreaID) && (tmpPath.getNextArea() == currAreaID))){
							//	if not, clear the path
								tmpPath=null;}
							else{
							//if the path is beeing followed get the next step
								pathCurrentStep=tmpPath.getNextStep();
								if (pathCurrentStep==null){
									tmpPath=null;
									System.out.println("ARIVED AT GOAL");
									}
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
		
		this.previousStep=this.currentStep;
		
		
		prevAreaID=currAreaID;
		previousPerceivedObj=PerceivedObj;
	}
	
	
	/*Hier wird ein satz von objekten in sicht in eine area umgewandelt 
	 * vorausgesetzt es ist ein referenzobjekt vorhanden
	 * 
	 * †bergabewert: die listen an gesichteten Objekten
	 * 
	 * RŸckgabewert: die erstelle Area oder Null falls kein Stationary 
	 * objet vorhanden ist, das die Area identifizieren kšnnte
	 * */
	
	private clsArea calc_area(clsPerceivedObj tmpPerceivedObj){
		clsPerceivedObj PerceivedObj=tmpPerceivedObj.clone();
		
		int i=0,j;
		clsArea newArea = new clsArea(PerceivedObj.getNum());
		
		if (PerceivedObj.getNum() > 0){

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
	
	
	public boolean planPath(int GoalAreaID, clsMemory eMemory){
		tmpPath= PathPlanning.calculate(this.getCurrentArea(),GoalAreaID,filterStepHistory(eMemory), SemMemory);
		
		if(tmpPath!=null){
			if (tmpPath.getNextArea()==this.currAreaID)
				pathCurrentStep=tmpPath.getNextStep();
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
		if (objects.getNum()<2)
			return 361;
		
		if (this.tmpPath==null)
			return 0;
		
		double D, E, F, gamma, lambda, epsilon, direction=0;
		int i,mainObject=-1,secondaryObject=-1;
		clsArea area = SemMemory.getAreaEntry(this.currAreaID-1);
		
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
	System.out.printf("ERROR at getting nextMove");
		gamma = Math.toDegrees( Math.acos(( Math.pow(D, 2)+Math.pow(tmpMove.B, 2)-Math.pow(E, 2))/(2*D*tmpMove.B )) );
		lambda = Math.abs(gamma - tmpMove.beta);
		
		F = Math.sqrt(Math.pow(D, 2) + Math.pow(tmpMove.A, 2) - (2*D*tmpMove.A*Math.cos(Math.toRadians(lambda))));
		
		epsilon = Math.toDegrees( Math.acos((Math.pow(F, 2) + Math.pow(D, 2) - Math.pow(tmpMove.A, 2))/(2*F*D)) );
		
		direction = epsilon - objects.getBearing(mainObject);
		
		System.out.println(direction + "Grad");
		if (direction<0)
			direction+=360;
		if (!tmpPath.isFwPath){
//			direction-=180;
			if (direction<0)
				direction+=360;
		}
		
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
}


