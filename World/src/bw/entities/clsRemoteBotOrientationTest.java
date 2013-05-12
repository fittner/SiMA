///**
// * @author langr
// * 
// * $Rev::                      $: Revision of last commit
// * $Author::                   $: Author of last commit
// * $Date::                     $: Date of last commit
// */
//package bw.entities;
//
//import java.awt.Color;
//import java.util.Iterator;
//
//import ARSsim.physics2D.physicalObject.clsMobileObject2D;
//import ARSsim.physics2D.util.clsPose;
//import ARSsim.portrayal.simple.clsImagePortrayal;
//import bw.body.clsBaseBody;
//import bw.body.clsComplexBody;
//import bw.body.io.actuators.clsActionProcessor;
//import decisionunit.itf.actions.*;
//import bw.physicalObjects.bodyparts.clsBotHands;
//import bw.physicalObjects.sensors.clsEntityPartVision;
//import bw.utils.container.clsConfigMap;
//import bw.utils.enums.eConfigEntries;
//import bw.body.io.sensors.external.clsSensorEatableArea;
//import bw.body.io.sensors.external.clsSensorVision;
//import bw.body.io.sensors.external.visionAnalysis.clsVisionAnalysis;
//import bw.body.itfget.itfGetEatableArea;
//import bw.body.itfget.itfGetVision;
//import bw.factories.clsSingletonMasonGetter;
//import statictools.clsSingletonUniqueIdGenerator;
//import enums.eCallPriority;
//import enums.eEntityType;
//import enums.eActionMoveDirection;
//import enums.eSensorExtType;
//import enums.eActionTurnDirection;
//import sim.display.clsKeyListener;
//import sim.physics2D.util.Angle;
//import sim.physics2D.physicalObject.PhysicalObject2D;
//
//import java.util.HashMap;
//import LocalizationOrientation.*;
//import memory.clsMemory;
//import memory.tempframework.clsActionContainer;
//import memory.tempframework.clsContainerCompareResults;
//import memory.tempframework.clsImagePerception;
//import memory.tempframework.clsRecognitionProcessResult;
//
///**
// * Sample implementation of a clsAnimate, having sensors and actuators 
// * (therefore the cycle sensing and executing implemented). The thinking
// * is in this instance out-sourced to the human user directing the entity 
// * across defined keys on her/his keyboard
// * 
// * @author langr
// * 
// */
//public class clsRemoteBotOrientationTest extends clsAnimate implements itfGetVision, itfGetEatableArea  {
//    private clsBotHands moBotHand1;
//    private clsBotHands moBotHand2;
//    
//    private clsLocalization moLocalization;
//    private clsVisionAnalysis moVisionAnalisys;
//    private clsPerceivedObj moPerceivedObjects;
//    private clsMemory moMemory;
//    private int test=0;
//    double tmp;
//    
//    private boolean followingPath=false;
//    
//    //statistics
//    private int pathfound=0;
//    private int pathnotfound=0;
//    private int pathincomplete=0;
//    private int pathtoolong=0;
//    private int pathrecalcOK=0;
//    private int pathrecalcBAD=0;
//    private int pathsuccess=0;
//       
//	private static double mrDefaultWeight = 100.0f;
//	private static double mrDefaultRadius = 10.0f;
//	private static Color moDefaultColor = Color.CYAN;
//	private static Color moDefaultHandColor = Color.gray;
//	
//	private int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
//
////	private Array moCapturedKeys;
//
//	/**
//	 * DOCUMENT (deutsch) - insert description 
//	 * 
//	 * @author deutsch
//	 * 26.02.2009, 11:29:23
//	 *
//	 * @param pnId
//	 * @param poStartingPose
//	 * @param poStartingVelocity
//	 */
//	public clsRemoteBotOrientationTest(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigMap poConfig) {
//		super(pnId, poStartingPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsRemoteBotOrientationTest.mrDefaultRadius, clsRemoteBotOrientationTest.moDefaultColor), clsRemoteBotOrientationTest.mrDefaultWeight, clsRemoteBotOrientationTest.getFinalConfig(poConfig));
//		
//		applyConfig();
//		
//		addBotHands();
//
//		this.moMemory = new clsMemory();
//		this.moLocalization = new clsLocalization(moMemory);
//		this.moVisionAnalisys = new clsVisionAnalysis();
//		
//	}
//	
//	public clsBaseBody createBody() {
//		return  new clsComplexBody(this, (clsConfigMap)moConfig.get(eConfigEntries.BODY));
//	}	
//	
//	private void applyConfig() {
//		//TODO add ...
//
//	}
//	
//	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
//		clsConfigMap oDefault = getDefaultConfig();
//		oDefault.overwritewith(poConfig);
//		return oDefault;
//	}
//	
//	private static clsConfigMap getDefaultConfig() {
//		clsConfigMap oDefault = new clsConfigMap();
//		
//		//TODO add ...
//		
//		return oDefault;
//	}	
//	
//	private clsBotHands addHand(double offsetX, double offsetY) {
//        double x = getPosition().x;
//        double y = getPosition().y;
//        sim.physics2D.util.Double2D oPos;
//                    
//        oPos = new sim.physics2D.util.Double2D(x + offsetX, y + offsetY);
//        
//        clsBotHands oHand = new clsBotHands(oPos, new sim.physics2D.util.Double2D(0, 0), 1, moDefaultHandColor);
//        
//        return oHand;
//	}
//	
//	
//	
//	/**
//	 * To be called AFTER clsRemoteBot has been initialized and registered to mason 
//	 * 
//	 * DOCUMENT (deutsch) - insert description
//	 *
//	 * @author deutsch
//	 * 26.02.2009, 11:38:59
//	 *
//	 */
//	private void addBotHands() {
//		//FIXME hands are only added correctly if - and only if - direction of bot is 0 ...
//		Angle oDirection = new Angle(getMobileObject2D().getOrientation().radians); //TODO add getDirection to clsEntity
//		getMobileObject2D().setPose(getPosition(), new Angle(0));
//		
//		moBotHand1 = addHand(12, 6);
//		moBotHand2 = addHand(12, -6);
//		
//
//		getMobileObject2D().setPose(getPosition(), oDirection);
//	}
//	
//	public clsBotHands getBotHand1() {
//		return moBotHand1;
//	}
//	public clsBotHands getBotHand2() {
//		return moBotHand2;
//	}
//	
//	public clsEntityPartVision getVision()
//	{
//		return ((clsSensorVision)moBody
//					.getExternalIO().moSensorExternal
//					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
//	}
//	
//	public clsEntityPartVision getEatableAreaVision()
//	{
//		return ((clsSensorEatableArea)moBody
//					.getExternalIO().moSensorExternal
//					.get(enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
//	}
//	
//	/* (non-Javadoc)
//	 * @see bw.clsEntity#setEntityType()
//	 */
//	@Override
//	protected void setEntityType() {
//		meEntityType = eEntityType.REMOTEBOT;
//	}
//	
//
//	/* (non-Javadoc)
//	 *
//	 * @author langr
//	 * 25.02.2009, 17:36:09
//	 * 
//	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
//	 */
//	@Override
//	public void processing() {
//		clsActionProcessor oAP = moBody.getExternalIO().getActionProcessor();
//
//		//calculate vision and perform localization if there landmarks in sight
//		if (this.getVision().getMeVisionObj().size()>0){			
//			moPerceivedObjects = moVisionAnalisys.calc_PerceivedObj(this.getVision(), this.getPosition().x, this.getPosition().y);
//			moLocalization.orientate(moPerceivedObjects);
//			if (moLocalization.madeTransition){
//				clsPose oPose = new clsPose(this.getPosition().x, this.getPosition().y, 0);
//				clsImagePortrayal.PlaceImage(clsGetARSPath.getArsPath() + "/src/resources/images/transition.jpg", 2, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
//			}else{	
//			//	clsPose oPose = new clsPose(this.getPosition().x, this.getPosition().y, 0);
//			//	clsImagePortrayal.PlaceImage(clsGetARSPath.getArsPath() + "/src/resources/images/path.jpg", 2, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
//			}
//			moMemory.addData(new clsImagePerception(), new clsContainerCompareResults(), new clsRecognitionProcessResult(), new clsActionContainer(), moLocalization.getCurrStep());
//			
//			//test++;
//			/*if (test % 1000 == 0){
//				if (moLocalization.planPath(2, moMemory)){	
//					double tmp=moLocalization.PathGetDirection(moPerceivedObjects);
//		    		if (tmp<0)
//		    			tmp+=360;
//		    		
//		    		System.out.println("direction = " + tmp);
//		    		clsMotionAction tmpMotion = clsMotionAction.creatAction(eActionCommandMotion.TURN_TO_PATH);
//		    		tmpMotion.setRelativeRotation(new Angle( Math.toRadians(tmp) ));
//		    	//	poActionList.addMoveAction(tmpMotion);
//				} else{
//					System.out.println("no path found");
//				}
//			}*/
//		}
//		
//		
//		if (followingPath)
//			this.followPath(oAP);
//		
//		
//		//the processing is taken over by the user via keyboard
//		
//	   	switch( clsKeyListener.getKeyPressed() )
//    	{
//    	case 38: //up
//    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD) );
//    		oAP.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,4));
//    		break;
//    	case 40: //down
//    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
//    		oAP.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,4));
//    		break;
//    	case 37: //rotate_left
//    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
//    		oAP.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
//    		break;
//    	case 39: //rotate_right
//    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
//    		oAP.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
//    		break;
//    	case 65: //'A'	calc path to area 2
//    		if (moLocalization.planPath(2))
//    			System.out.println("Fund a Path");
//    		else
//    			System.out.println("No Path was found");
//    		break;
//    	case 89: //'Y' print the direction to go
//    		tmp=moLocalization.PathGetDirection(moPerceivedObjects);
//    		System.out.println("direction = " + tmp);
//    		break;
//    	case 69: //'E'
//    		tmp=moLocalization.PathGetDirection(moPerceivedObjects);
//    		if (tmp<=360&&tmp>=0)
//    			if (tmp<359&&tmp>=180)
//    				oAP.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
//    			else if(tmp>1&&tmp<180)
//    				oAP.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
//    		break;
//    	case 83: //'S'
//    		if (!followingPath)
//    			followingPath=true;
//    		else
//    			followingPath=false;
//    		break;
//    	}
//	}
//	
//	/**
//	 * DOCUMENT (langr) - insert description
//	 *
//	 * @author langr
//	 * 02.04.2009, 13:04:55
//	 *
//	 * @param poEntity
//	 * @param poActionList
//	 */
//
//	private void eat() {
//		//eat
//		clsSensorEatableArea oEatArea = (clsSensorEatableArea)(moBody.getExternalIO().moSensorExternal.get(eSensorExtType.EATABLE_AREA));
//		if(oEatArea.getViewObj() != null)
//		{
//
//			Iterator<PhysicalObject2D> itr = oEatArea.getViewObj().values().iterator(); 			
//			while(itr.hasNext())
//			{
//				PhysicalObject2D oPhysicalObj = itr.next();
//				if(  oPhysicalObj instanceof clsMobileObject2D)
//				{
//					clsMobileObject2D oEatenMobileObject = (clsMobileObject2D)oPhysicalObj; 
//					clsEntity oEatenEntity = oEatenMobileObject.getEntity();
//					
//					//TODO cm only cakes for now
//					if(  oEatenEntity instanceof clsCake)
//					{
//						//clsEatAction oEatAction = new clsEatAction(eActionCommandType.EAT, oEatenEntity);
//						//poActionList.addEatAction(oEatAction);
//
//						clsActionProcessor oAP = moBody.getExternalIO().getActionProcessor();
//						oAP.call(new clsActionEat());	
//					}
//				}
//			}
//		}
//	}
//
//	public void execution() {
//		super.execution();
///*		
//		if (moTemp.length() > 0) {
//	      // System.out.println(mnUniqueId+": "+moTemp);
//	      moTemp = "";
//		}
//*/
//	}
//
//public void followPath(itfActionProcessor poActionProcessor){
//		//wenn am ziel
//		if (moLocalization.getCurrentArea()==2){
//			followingPath=false;
//			return;
//		}
//		
//		double tmp=moLocalization.PathGetDirection(moPerceivedObjects);
//		
//		if (tmp==361){
//			followingPath=false;
//			return;
//		}
//		if (tmp==-1){
//			followingPath=false;
//			return;
//		}
//			
//		
////erst zu checkpoint gehen dann in die richtige richtung und fertig		
//		if (tmp<=360&&tmp>=0)
//			if (tmp<358&&tmp>=180)
//				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
//			else if(tmp>2&&tmp<180)
//				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
//    		else
//    			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0.9f));
//		
//	}
//
//
//}