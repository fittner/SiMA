package simple.remotecontrol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import statictools.clsGetARSPath;
import statictools.clsSingletonUniqueIdGenerator;
import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionEat;
import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsEatableArea;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;

public class clsRemoteControl extends clsBaseDecisionUnit  {
	private int moKeyPressed;

	private boolean mnLogXML = true;
	private String moFileName;

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
	public clsRemoteControl() {
		super();

		moFileName = clsGetARSPath.getArsPath()+"/remotebotlog_"+getDateTime()+"_"+clsSingletonUniqueIdGenerator.getUniqueId()+".xml";
	}
	public void setKeyPressed(int i) {
		moKeyPressed = i;
	}
	
	public void setLogXML(boolean mnLogXML) {
		this.mnLogXML = mnLogXML;
	}
	
	public boolean isLogXML() {
		return mnLogXML;
	}	
	
	@Override
	public void process(itfActionProcessor poActionProcessor) {
		//the processing is taken over by the user via keyboard
		
	   	switch( moKeyPressed )
    	{
    	case 38: //up
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD) );
    		poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,4));
    		break;
    	case 40: //down
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
    		poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,4));
    		break;
    	case 37: //rotate_left
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
    		poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
    		break;
    	case 39: //rotate_right
    		//moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
    		poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
    		break;
    	case 65: //'A'
    		break;
    	case 69: //'E'
    		eat(poActionProcessor);
    		break;
    	case 83: //'S'
//            if(botState==HAVECAN)
//            {
//	    		objCE.unRegisterForceConstraint(pj);                            
//	            botState = APPROACHINGCAN;
//	            objCE.removeNoCollisions(getMobile(), currentCan.getMobile());
//	            objCE.removeNoCollisions(e1, currentCan.getMobile());
//	            objCE.removeNoCollisions(e2, currentCan.getMobile());
//	            currentCan.visible = true;
//            }
    		break;
    	}
		
	   	if (mnLogXML) {
	   		goLogging(poActionProcessor);
	   	}
	}

	private void eat(itfActionProcessor poActionProcessor) {
		/*
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0)
		{

				if( oEatArea.mnTypeOfFirstEntity == eEntityType.CAKE )
				{
						//clsEatAction oEatAction = new clsEatAction();
						//poActionList.addEatAction(oEatAction);
						poActionProcessor.call(new clsActionEat());	
				}
		}
		*/
		poActionProcessor.call(new clsActionEat());	
	}
	
	private void goLogging(itfActionProcessor poActionProcessor) {
		String logEntry = "<DUSet>";
		
		logEntry += getSensorData().logXML();
		logEntry += poActionProcessor.logXML();
		
		logEntry += "</DUSet>\n";
		
	    try{
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(moFileName,true);
	         BufferedWriter out = new BufferedWriter(fstream);
	         out.write(logEntry);
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }		
	}
	
}
