/**
 * @author UNKNOWN <-- ADD NAME
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.dumbmind;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.LocalizationOrientation.clsLocalization;
import bw.LocalizationOrientation.clsPerceivedObj;
import bw.body.io.sensors.external.visionAnalysis.clsVisionAnalysis;
import bw.entities.clsBubble;
import bw.memory.clsMemory;
import bw.memory.tempframework.clsActionContainer;
import bw.memory.tempframework.clsContainerCompareResults;
import bw.memory.tempframework.clsImagePerception;
import bw.memory.tempframework.clsRecognitionProcessResult;
import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionEat;
import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;

/**
 * TODO (UNKNOWN) - add name & insert description! 
 * 
 * HINT: NEVER COMMIT FILES WITH ERRORS INSDE - IT'S PROHIBITED BY ARS-LAW!
 * 
 * @author UNKNOWN
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindOrientationTest extends clsBaseDecisionUnit {

	private boolean mnRoombaIntelligence = true;
	private boolean mnCollisionAvoidance = false;
	
	private clsLocalization moLocalization;
    private clsVisionAnalysis moVisionAnalisys;
    private clsPerceivedObj moPerceivedObjects;
    private clsMemory moMemory;
    private int test=0,collisioncount=0;
    
    private boolean followingPath=false;
    clsBubble myBody;
    
    //statistics
    private int pathfound=0;
    private int pathnotfound=0;
    private int pathincomplete=0;
    private int pathtoolong=0;
    private int pathrecalcOK=0;
    private int pathrecalcBAD=0;
    private int pathsuccess=0;
	    
	public clsDumbMindOrientationTest(clsBubble myMobile) {
		
		this.moMemory = new clsMemory();
		this.moLocalization = new clsLocalization(moMemory);
		this.moVisionAnalisys = new clsVisionAnalysis();
		
		myBody=myMobile;
		
	}
	
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		
		//calculate vision and perform localization if there landmarks in sight

		if (myBody.getVision().getMeVisionObj().size()>0){			
			moPerceivedObjects = moVisionAnalisys.calc_PerceivedObj(myBody.getVision(), myBody.getPosition().x, myBody.getPosition().y);
			moLocalization.orientate(moPerceivedObjects);
			moMemory.addData(new clsImagePerception(), new clsContainerCompareResults(), new clsRecognitionProcessResult(), new clsActionContainer(), moLocalization.getCurrStep());
			
			test++;
			if (!followingPath){
				if (test % 2500 == 0){
					if (moLocalization.getCurrentArea()==2){
						followingPath=false;
						pathsuccess++;
					}else{
							if (moLocalization.spontaniousFoodSearch()){
								followingPath=true;
								pathfound++;
							}else{
								System.out.println("no path found");
								pathnotfound++;
							}
						
					}
				}
			}else{
				if (test % 5000 == 0){
					System.out.println("path too long");
					followingPath=false;
					pathtoolong++;
				}
			}
				/*
				clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
				if (!avoid_obstacles(moPerceivedObjects,poActionProcessor) && !avoid_Walls(oVision,poActionProcessor)){
					if(followingPath){

						followPath(poActionProcessor);
					}else{	
						poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
					}
				}else{
					if(followingPath){
						collisioncount++;
						System.out.println("COLLOSION WHILE FOLLOWING PATH");
						if (collisioncount>20)
							followingPath=false;
						collisioncount=0;
					}
				}
				*/
			//test with alternative that disables avoidance while following path
			
			
				if(followingPath){
					followPath(poActionProcessor);
				}else{	
					clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
					if (!avoid_obstacles(moPerceivedObjects,poActionProcessor) && !avoid_Walls(oVision,poActionProcessor))
						poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
				}
			
		}
	}
	
	public void followPath(itfActionProcessor poActionProcessor){
		//wenn am ziel
		if (moLocalization.getCurrentArea()==2){
			followingPath=false;
			return;
		}
		
		double tmp=moLocalization.PathGetDirection(moPerceivedObjects);
		
		if (tmp==361){
			followingPath=false;
			return;
		}
		if (tmp==-1){
			followingPath=false;
			return;
		}
			
		
//erst zu checkpoint gehen dann in die richtige richtung und fertig		
		if (tmp<=360&&tmp>=0)
			if (tmp<358&&tmp>=180)
				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
			else if(tmp>2&&tmp<180)
				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
    		else
    			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0.9));
		
	}
	
	public boolean avoid_obstacles(clsPerceivedObj Objects,itfActionProcessor poActionProcessor){
		int i;
		double minDistance=20.;
		for (i=0;i<Objects.getNum();i++){
			if ((Objects.getObject(i).moObject instanceof clsMobileObject2D)&&(Objects.getDistance(i)<minDistance)){
				if (Objects.getBearing(i)<(90)){
					//System.out.printf("drehen\n");
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT,5));
					return true;
				}else if (Objects.getBearing(i)>(270)){
					//System.out.printf("drehen\n");
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT,5));
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean avoid_Walls(clsVision Objects,itfActionProcessor poActionProcessor){
		int i;
		double minDistance=15.;
		//the stones have somehow wrong values thus a second avoidance for stones os done
		for( clsVisionEntry oVisionObj : Objects.getList() ) {
			if ((oVisionObj.mnEntityType == eEntityType.WALL)&&(oVisionObj.moPolarcoordinate.mrLength<minDistance)){
				if (oVisionObj.moPolarcoordinate.moAzimuth.getDegree()<120){
					//System.out.printf("drehen\n");
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT,100));
					return true;
				}else if (oVisionObj.moPolarcoordinate.moAzimuth.getDegree()>(250)){
					//System.out.printf("drehen\n");
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT,100));
					return true;
				}
			}
		}
		
		return false;
	}
	
	

	public void setCollisionAvoidance(boolean mnCollisionAvoidance) {
		this.mnCollisionAvoidance = mnCollisionAvoidance;
		
		//this is for fun only (roland)
//		if( mnCollisionAvoidance) {
//			new AePlayWave(bw.sim.clsBWMain.msArsPath + "/src/resources/sounds/utini.wav").start();
//		}
	}

	public boolean isRoombaIntelligence() {
		return mnRoombaIntelligence;
	}

	public boolean isCollisionAvoidance() {
		return mnCollisionAvoidance;
	}

	@Override
	public void process(itfActionProcessor poActionProcessor) { 
		stepProcessing(poActionProcessor);
		
		//clsActionCommandContainer oCommands = new clsActionCommandContainer();
		//oCommands.addMoveAction( clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD));
		
	}
	
	
	
	
}
