/**
 * CHANGELOG
 *
 * 15.11.2013 LuHe - File created
 *
 */
package communicationPorts;


import java.util.ArrayList;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartner;
import complexbody.brainsocket.clsBrainSocket;
import complexbody.io.actuators.actionCommands.clsActionBeat;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionDivide;
import complexbody.io.actuators.actionCommands.clsActionDrop;
import complexbody.io.actuators.actionCommands.clsActionEat;
import complexbody.io.actuators.actionCommands.clsActionExcrement;
import complexbody.io.actuators.actionCommands.clsActionMove;
import complexbody.io.actuators.actionCommands.clsActionPickUp;
import complexbody.io.actuators.actionCommands.clsActionSleep;
import complexbody.io.actuators.actionCommands.clsActionTurn;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionEmotionalStressSweat;
import complexbody.io.actuators.actionCommands.clsInternalActionFacialChangeEyeBrows;
import complexbody.io.actuators.actionCommands.clsInternalActionFacialChangeEyes;
import complexbody.io.actuators.actionCommands.clsInternalActionFacialChangeMouth;
import complexbody.io.actuators.actionCommands.clsInternalActionFasterHeartPump;
import complexbody.io.actuators.actionCommands.clsInternalActionTenseMuscles;
import complexbody.io.sensors.datatypes.enums.eActionMoveDirection;
import complexbody.io.sensors.datatypes.enums.eActionSleepIntensity;
import complexbody.io.sensors.datatypes.enums.eActionTurnDirection;

import base.clsCommunicationInterface;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (herret) - Defines hold teh data and the control interfaces to the vody
 * 
 * @author LuHe
 * 15.11.2013, 09:20:01
 * 
 */
public class clsCommunicationPortDUData implements itfCommunicationPartner{

    clsCommunicationInterface moDataInterface;
    clsBrainSocket moBrainSocket;
    ArrayList<clsActionCommand> moActions = new ArrayList<clsActionCommand>();
    ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
    
    public clsCommunicationPortDUData(clsBrainSocket poBrainSocket){
        moBrainSocket = poBrainSocket;
    }
    
    public void setCommunicationInterface(clsCommunicationInterface poInterface){
    	moDataInterface=poInterface;
    	moDataInterface.setCommunicationPartner(this);
    }

    /* (non-Javadoc)
     *
     * @since 15.11.2013 09:27:02
     * 
     * @see communication.interfaces.itfCommunicationPartner#recvData(communication.datatypes.clsDataContainer)
     */
    @Override
    public clsDataContainer recvData(clsDataContainer poData) {
    	//should not be called because of Non Blocking Interface
        clsDataContainer oRetVal = new clsDataContainer();
        oRetVal.addDataPoint(new clsDataPoint("STATUSCODE","ERROR"));
        return oRetVal;
    }
    
    public void recvActionCommands(){
    	clsDataContainer oData = moDataInterface.recvData(null);
    	for(clsDataPoint oDataP: oData.getData()){
    		if(oDataP.getType().equals("ACTION_COMMAND_LIST_EXTERNAL"))moActions = convertExternalActions(oDataP);
    		if(oDataP.getType().equals("ACTION_COMMAND_LIST_INTERNAL"))moInternalActions = convertInternalActions(oDataP);
    	}
   }
    
    private ArrayList<clsActionCommand> convertExternalActions(clsDataPoint oActions){
    	ArrayList<clsActionCommand> oRetVal = new ArrayList<clsActionCommand>();
    	for(clsDataPoint oAction: oActions.getAssociatedDataPoints()){
    		if(oAction.getType().equals("ACTION_COMMAND")){
    			if(oAction.getValue().equals("MOVE")){
    				clsActionMove oNewAction = new clsActionMove();
    				if(oAction.getAssociation("DIRECTION").getValue().equals("FORWARD")){
    					oNewAction.setDirection(eActionMoveDirection.MOVE_FORWARD);
    				}
    				else if(oAction.getAssociation("DIRECTION").getValue().equals("BACKWARD")){
    					oNewAction.setDirection(eActionMoveDirection.MOVE_BACKWARD);
    				}
    				oNewAction.setSpeed(Double.parseDouble(oAction.getAssociation("DISTANCE").getValue()));
    				oRetVal.add(oNewAction);
    			}
    			else if(oAction.getValue().equals("TURN")){
    				clsActionTurn oNewAction = new clsActionTurn();
    				if(oAction.getAssociation("DIRECTION").getValue().equals("TURN_LEFT")){
    					oNewAction.setSpeed(eActionTurnDirection.TURN_LEFT);
    				}
    				else if(oAction.getAssociation("DIRECTION").getValue().equals("TURN_RIGHT")){
    					oNewAction.setSpeed(eActionTurnDirection.TURN_RIGHT);
    				}
    				oNewAction.setAngle(Double.parseDouble(oAction.getAssociation("ANGLE").getValue()));
    				oRetVal.add(oNewAction);
    			}
    			else if(oAction.getValue().equals("TURN_VISION")){
    				clsActionTurn oNewAction = new clsActionTurn();
    				if(oAction.getAssociation("DIRECTION").getValue().equals("TURN_LEFT")){
    					oNewAction.setSpeed(eActionTurnDirection.TURN_LEFT);
    				}
    				else if(oAction.getAssociation("DIRECTION").getValue().equals("TURN_RIGHT")){
    					oNewAction.setSpeed(eActionTurnDirection.TURN_RIGHT);
    				}
    				oNewAction.setAngle(Double.parseDouble(oAction.getAssociation("ANGLE").getValue()));
    				oRetVal.add(oNewAction);
    			}

    			else if(oAction.getValue().equals("EAT")){
    				oRetVal.add(new clsActionEat());
    			}
       			else if(oAction.getValue().equals("PICK_UP")){
    				oRetVal.add(new clsActionPickUp());
    			}
       			else if(oAction.getValue().equals("DROP")){
    				oRetVal.add(new clsActionDrop());
    			}
    			else if(oAction.getValue().equals("BEAT")){
    				double force = Double.parseDouble(oAction.getAssociation("FORCE").getValue());
    				clsActionBeat oNewAction = new clsActionBeat(force);
    				oRetVal.add(oNewAction);
    			}
    			else if(oAction.getValue().equals("DIVIDE")){
    				double force = Double.parseDouble(oAction.getAssociation("FACTOR").getValue());
    				clsActionDivide oNewAction = new clsActionDivide(force);
    				oRetVal.add(oNewAction);
    			}
    			else if(oAction.getValue().equals("SLEEP")){
    				clsActionSleep oNewAction = new clsActionSleep();
    				if(oAction.getAssociation("INTENSITY").getValue().equals("DEEP")){
    					oNewAction.setIntensity(eActionSleepIntensity.DEEP);
    				}
    				else if(oAction.getAssociation("INTENSITY").getValue().equals("LIGHT")){
    					oNewAction.setIntensity(eActionSleepIntensity.LIGHT);
    				}
    				oRetVal.add(oNewAction);
    			}
    			else if(oAction.getValue().equals("EXCREMENT")){
    				double force = Double.parseDouble(oAction.getAssociation("INTENSITY").getValue());
    				clsActionExcrement oNewAction = new clsActionExcrement(force);
    				oRetVal.add(oNewAction);
    			}
    			
    			
    		}
    	}
    	
    	
    	return oRetVal;
    }
    
    
    /*        moInternalActions.addDataPoint(createActionCommand("HEART_INTENSITY", moEmotionNames_Heart,moEmotionIntensities_Heart ));
        moInternalActions.addDataPoint(createActionCommand("EYES_INTENSITY", moEmotionNames_Eyes,moEmotionIntensities_Eyes ));
        moInternalActions.addDataPoint(createActionCommand("EYE_BROWNS_INTENSITY", moEmotionNames_EyeBrows,moEmotionIntensities_EyeBrows ));
        moInternalActions.addDataPoint(createActionCommand("MOUTH_INTENSITY", moEmotionNames_Mouth,moEmotionIntensities_Mouth ));
        moInternalActions.addDataPoint(createActionCommand("SWEAT_INTENSITY", moEmotionNames_StressSweat,moEmotionIntensities_StressSweat ));
        moInternalActions.addDataPoint(createActionCommand("ARM_INTENSITY", moEmotionNames_ArmsNLegs,moEmotionIntensities_ArmsNLegs ));
*/
    private ArrayList<clsInternalActionCommand> convertInternalActions(clsDataPoint oInternalActions){
    	ArrayList<clsInternalActionCommand> oRetVal = new ArrayList<clsInternalActionCommand>();
    	for(clsDataPoint oAction: oInternalActions.getAssociatedDataPoints()){
    		if(oAction.getType().equals("ACTION_COMMAND")){
    			if(oAction.getValue().equals("HEART_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionFasterHeartPump(emotionValues.b, emotionValues.a));
    			}
    			else if(oAction.getValue().equals("EYES_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionFacialChangeEyes(emotionValues.b, emotionValues.a));
    			}
    			else if(oAction.getValue().equals("EYE_BROWNS_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionFacialChangeEyeBrows(emotionValues.b, emotionValues.a));
    			}
    			else if(oAction.getValue().equals("MOUTH_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionFacialChangeMouth(emotionValues.b, emotionValues.a));
    			}
    			else if(oAction.getValue().equals("SWEAT_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionEmotionalStressSweat(emotionValues.b, emotionValues.a));
    			}
    			else if(oAction.getValue().equals("ARM_INTENSITY")){
    				clsPair<ArrayList<String>,ArrayList<Double>> emotionValues = getEmotionValues(oAction);
    				oRetVal.add(new clsInternalActionTenseMuscles(emotionValues.b, emotionValues.a));
    			}
    		}
    	}
    	
    	
    	return oRetVal;
    }
    
    public clsPair<ArrayList<String>,ArrayList<Double>> getEmotionValues(clsDataPoint action){
		ArrayList<String> emotionNames = new ArrayList<String>();
		ArrayList<Double> emotionValues = new ArrayList<Double>();
		for (clsDataPoint emotion: action.getAssociatedDataPoints()){
			emotionNames.add(emotion.getType());
			emotionValues.add(Double.parseDouble(emotion.getValue()));
		}
		return new clsPair<ArrayList<String>,ArrayList<Double>>(emotionNames,emotionValues);

    }
    public ArrayList<clsActionCommand> getActions(){
  
    	return moActions;
    }
    
    public ArrayList<clsInternalActionCommand> getInternalActions(){
    	return moInternalActions;
    }
    public void sendToDU(clsDataContainer poData){
        moDataInterface.sendData(poData);
        return;
    }
    
}
