package simple.remotecontrol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import config.clsProperties;
import statictools.clsGetARSPath;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eActionKissIntensity;
import du.enums.eActionMoveDirection;
import du.enums.eActionSleepIntensity;
import du.enums.eActionTurnDirection;
import du.enums.eDecisionType;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import du.itf.itfProcessKeyPressed;
import du.itf.actions.*;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsSensorRingSegmentEntry;
import du.itf.sensors.clsVision;

public class clsRemoteControl extends clsBaseDecisionUnit implements itfProcessKeyPressed {
	private int mnUniqueId;
	private int moKeyPressed;
	private int moPrevKeyPressed;	// previous key pressed

	private boolean mnLogXML = false;
	private int mnStepsToSkip = 1;
	private int mnStepCounter = 0;
	private String moFileName;

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
	public clsRemoteControl(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		//mnUniqueId = clsUniqueIdGenerator.getUniqueId(); //TD 2011/04/11 - uid is the new unique id
		moFileName = clsGetARSPath.getArsPath()+"/remotebotlog_"+getDateTime()+"_"+mnUniqueId+".xml";
		
		applyProperties(poPrefix, poProp);

	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsBaseDecisionUnit.getDefaultProperties(poPrefix) );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);

	}	
	
	@Override
	protected void finalize() throws Throwable
	{
	  endFile();
	  //do finalization here
	  super.finalize(); //not necessary if extending Object.
	} 
	
	@Override
	public void setKeyPressed(int i) {
		moPrevKeyPressed = moKeyPressed;
		moKeyPressed = i;
		//System.out.println("Pressed key: "+ moKeyPressed);
	}
	
	public int getKeyPressed() {
		return moKeyPressed;
	}
	
	public void setLogXML(boolean mnLogXML) {
		this.mnLogXML = mnLogXML;
		
		if (this.mnLogXML) {
			startFile();
		} else {
			endFile();
		}
	}
	
	public boolean isLogXML() {
		return mnLogXML;
	}	
	
	public void setStepsToSkip(int pnStepsToSkip) {
		mnStepsToSkip = pnStepsToSkip;
		
		if(mnStepsToSkip < 1){
			mnStepsToSkip = 1;
		}
	}

	public int getStepsToSkip() {
		return mnStepsToSkip;
	}

	@Override
	public void process() {
		 itfActionProcessor poActionProcessor = getActionProcessor();
		//the processing is taken over by the user via keyboard
		 
		 if (moKeyPressed!=0) {
			// moKeyPressed=moKeyPressed;
	    	//System.out.println("key:" + moKeyPressed);
		 }
		 
	   	switch( moKeyPressed )
    	{
    	case 38: //up
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD) );
    		poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
    		break;
    	case 40: //down
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
    		poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,0.5));
    		break;
    	case 37: //rotate_left
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
    		poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
    		break;
    	case 39: //rotate_right
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
    		poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
    		break;
    	case 69: //'E'
    		eat(poActionProcessor, eEntityType.CAKE);
    		break;
    	case 521:
    	case 107: // '+'
    		if(moPrevKeyPressed != 107){	// the '+' key has to be released 
    			poActionProcessor.call(new clsActionPickUp() );
    		}
    		break;
    	case 45:
    	case 109: // '-'
    		if(moPrevKeyPressed != 109){	// the '-' key has to be released 
    			poActionProcessor.call(new clsActionDrop() );
    		}
    		break;
    	case 111: // '/'
    		poActionProcessor.call(new clsActionFromInventory() );
    		break;
    	case 106: // '*'
    		poActionProcessor.call(new clsActionToInventory() );
    		break;

    	case 76: //'L'
    		attack(poActionProcessor,	eEntityType.ARSIN);
    		break;

    	case 66: //'B'
    		kill(poActionProcessor,	eEntityType.ARSIN);
    		break;
    		
    	case 88: //'X'
    		poActionProcessor.call(new clsActionExcrement(1) );
    		break;
    		
    	case 77: //'M'
    		poActionProcessor.call(new clsActionMoveToEatableArea());
    		break;
    		
    	case 75: //'K'
    		poActionProcessor.call(new clsActionKiss(eActionKissIntensity.MIDDLE));
    		break;
    		
    	case 67: //'C'
    		poActionProcessor.call(new clsActionCultivate(1) );
    		break;

    	case 49: //1
    		if (moPrevKeyPressed!=moKeyPressed) poActionProcessor.call(clsActionSequenceFactory.getSalsaSequence(2, 2));
    		break;
    		
    	case 50: //2
    		if (moPrevKeyPressed!=moKeyPressed) poActionProcessor.call(clsActionSequenceFactory.getTangoSequence(2,2));
    		break;
    		
    	case 51: //3
    		if (moPrevKeyPressed!=moKeyPressed) poActionProcessor.call(clsActionSequenceFactory.getWalzSequence(2,2));
    		break;

    	case 70: //f
    		if (moPrevKeyPressed!=moKeyPressed) {
    			poActionProcessor.call(new clsActionBodyColor (10,-10,-10));
    		}
    	
    	case 83: //'S'
    		poActionProcessor.call(new clsActionSleep (eActionSleepIntensity.DEEP));
    		break;
    		
//    	case 83: //'S'
//            if(botState==HAVECAN)
//            {
//	    		objCE.unRegisterForceConstraint(pj);                            
//	            botState = APPROACHINGCAN;
//	            objCE.removeNoCollisions(getMobile(), currentCan.getMobile());
//	            objCE.removeNoCollisions(e1, currentCan.getMobile());
//	            objCE.removeNoCollisions(e2, currentCan.getMobile());
//	            currentCan.visible = true;
//            }
//    		break;

   	
    	}
	   	if (mnLogXML) {
	   		goLogging(poActionProcessor);
	   	}
	}

	protected void eat(itfActionProcessor poActionProcessor, du.enums.eEntityType peEntityType) {
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if (oEatArea.getDataObjects().size() > 0) {
			clsEatableAreaEntry oEntry = (clsEatableAreaEntry) oEatArea.getDataObjects().get(0);
			if (oEntry.getEntityType() == peEntityType) {
				poActionProcessor.call(new clsActionEat());	
			}
		}
	}

	protected void attack(itfActionProcessor poActionProcessor, du.enums.eEntityType peEntityType) {
		clsVision oVis = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);

		for(int i=0; i<oVis.getDataObjects().size(); i++){
			if (((clsSensorRingSegmentEntry)oVis.getDataObjects().get(i)).getEntityType() == peEntityType) {
				poActionProcessor.call(new clsActionAttackLightning(4, ((clsSensorRingSegmentEntry)oVis.getDataObjects().get(i)).getEntityId()));	
			}
		}

	}


	/**
	 * Hurts the entity with 4 hit-points - using calsActionAttackBite as command-processor interface 
	 * 
	 * @param poActionProcessor
	 */
	protected void kill(itfActionProcessor poActionProcessor, du.enums.eEntityType peEntityType) {
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if (oEatArea.getDataObjects().size() > 0) {
			clsEatableAreaEntry oEntry = (clsEatableAreaEntry) oEatArea.getDataObjects().get(0);
			if (oEntry.getEntityType() == peEntityType) {
				poActionProcessor.call(new clsActionAttackBite(4));	
			}
		}		
	}
	
	private void startFile() {
	    try{
	    	String logEntry = "<RemoteControl id=\""+mnUniqueId+"\">\n\n";
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(moFileName,false);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(logEntry);
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }		
	}
	
	private void endFile() {
	    try{
	    	String logEntry = "</RemoteControl>\n\n";
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(moFileName,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(logEntry);
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }		
	}
	
	private void goLogging(itfActionProcessor poActionProcessor) {


		//skip logging for mnStepsToSkip
		if((mnStepCounter % mnStepsToSkip)==0) {
			String logEntry = "<DUSet round=\""+mnStepCounter+"\">";
			
			logEntry += getSensorData().logXML();
			logEntry += poActionProcessor.logXML();
			
			logEntry += "</DUSet>\n";
			
		    try{
		   	    // Create file 
		   	    FileWriter fstream = new FileWriter(moFileName,true);
		        BufferedWriter out = new BufferedWriter(fstream);
		        out.write(logEntry);
		        out.flush();
		   	    //Close the output stream
		   	    out.close();
		     }catch (Exception e){//Catch exception if any
		   	      System.err.println("Error: " + e.getMessage());
		    }     
		}
		mnStepCounter++;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:55:20
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.REMOTE;
		
	}
	
	
}
