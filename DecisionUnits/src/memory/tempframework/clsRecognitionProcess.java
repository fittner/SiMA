// File clsRecognitionProcess
// July 21, 2006
//

// Belongs to package
package memory.tempframework;

// Imports

//import memory.tempframework.clsActionContainer;
//import memory.tempframework.clsRuleCompareResult;
//import memory.tempframework.enumTypeTrippleState;
//import java.util.Vector;

/**
 * This is the class description ... $Revision: 755 $:  Revision of last commit $Author: langr $: Author of last commit $Date: 2007-07-19 15:05:38 +0200 (Do, 19 Jul 2007) $: Date of last commit
 */
public class clsRecognitionProcess {

//  //including the recognized states of the scenario-definition
//	public Vector moRecognizedStateList = new Vector();
  public clsScenario moRelativeScenario = null;
//
//  public int mnTriggerSinceLastStateChange = 0;
//
//  public clsActionPlan moStateActions = null;
//
//  //---------------------------------------------------------------------------
//  public clsRecognitionProcess()
//  //---------------------------------------------------------------------------
//  {
//  }
//
//  //---------------------------------------------------------------------------
//  public clsRecognitionProcess(clsScenario poScenario)
//  //---------------------------------------------------------------------------
//  {
//    moRelativeScenario = poScenario;
//    push(poScenario.moEntryState);
//  }
//
//  //---------------------------------------------------------------------------
//  public int triggerScenarioRecognition( clsRuleCompareResult poCompareResult, 
//                                         clsRecognitionProcessContainer poStateChangeCausedActions, 
//                                         clsRecognitionProcessResult poRecognizedResult, clsActionContainer poInnerAction)
//  //---------------------------------------------------------------------------
//  //return values: 0...no recognition
//  //               1...StateChange
//  //               2...RECOGNITION
//  //              -1...abort condition matches --> discard this
//  {
//    int nResult = 0;
//
//    clsState oCurrentState = getHead();
//    int eAccomplished = checkActionAccomplished();
//    if( eAccomplished != enumTypeTrippleState.TTRIPPLE_UNDEFINED )
//    {
////      Engine.log.println("triggerScenarioRecognition in transition list");
//      clsState oNewState = oCurrentState.moTransitionList.triggerStateTransition( poCompareResult, mnTriggerSinceLastStateChange, eAccomplished );
//      if( oNewState != null && oCurrentState.mnId != oNewState.mnId)
//      {
//        nResult = 1; 
//        push( oNewState );
//
//          Engine.log.println("Recognition Process - SCENARIO ACTIVE");
//
//        if(  oNewState.mnTerminalState == true )
//        {
////          Engine.log.println("Recognition Process - SCENARIO RECOGNIZED");
//          //scenario recognized!!!
//          nResult = 2;
//          poRecognizedResult.moRecognized.add(this);
//        }
//        else
//        {
//          cloneActionPlan( oCurrentState );
//          poStateChangeCausedActions.add( this );
//        }
//        mnTriggerSinceLastStateChange = 0;
//      }
//      else
//      {
//        //no state change, but maybe abort condition
//        mnTriggerSinceLastStateChange++;
//        if( oCurrentState.moTransitionList.checkAbortConditions(poCompareResult, mnTriggerSinceLastStateChange) )
//        {
//          nResult = -1;
//          poRecognizedResult.moAborted.add(this);
//        }
//      }
//    }
//    return nResult;
//  }
//
//  //---------------------------------------------------------------------------
//  protected void cloneActionPlan( clsState poCurrentState )
//  //---------------------------------------------------------------------------
//  {
//    if( poCurrentState.moActionPlan != null )
//    {
//      moStateActions = poCurrentState.moActionPlan.clone2();
//    }
//  }
//
//  //---------------------------------------------------------------------------
//  protected int checkActionAccomplished( )
//  //---------------------------------------------------------------------------
//  {
//    if( moStateActions == null ) 
//    {
//      return enumTypeTrippleState.TTRIPPLE_TRUE;
//    }
//    else
//    {
//        return moStateActions.checkActionAccomplished();
//    }
//  }
//
//  //---------------------------------------------------------------------------
//	public void push( clsState poRecognizedState )
//  //---------------------------------------------------------------------------
//	{
//    moRecognizedStateList.add(0, poRecognizedState);
//	}     
//
//  //---------------------------------------------------------------------------
//  public clsState getHead()
//  //---------------------------------------------------------------------------
//  {
//    clsState oResult = null;
//    if( moRecognizedStateList.size() > 0 )
//    {
//      Object obj = moRecognizedStateList.get(0);
////      clsState oState = (clsState)obj;
////      Engine.log.println("~~~ oState ID="+oState.toString());
//      oResult = (clsState)obj;
//    }
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------  
//  public void getCurrentActionList(clsActionContainer poOuterActionList, clsActionContainer poInnerActionList)
//  //---------------------------------------------------------------------------
//  { 
//    clsState oCurrentState = getHead();
//
//    if( oCurrentState == null) 
//    {
//    	return;
//    }
// 
////    Engine.log.println( "getCurrentActionList --> StateOfRecognitionList:"+oCurrentState.mnId );
//    if( oCurrentState.mnTerminalState == true )
//    {
////      Engine.log.println( "TERMINAL DESIRE STATE" );
//      moRelativeScenario.moActionPlan.getCurrentActionList( poOuterActionList, poInnerActionList );
//    }
//    else
//    {
//      oCurrentState.getCurrentActionList(poOuterActionList, poInnerActionList);
//    }
//  }
//
//  //---------------------------------------------------------------------------
//  protected String gettoString(Object poObject)
//  //---------------------------------------------------------------------------
//  {
//     return "";
//  }
//
//  //---------------------------------------------------------------------------
//  public String toString()
//  //---------------------------------------------------------------------------
//  {
//     return "scenario recognition process - scenarioId="+moRelativeScenario.mnId+" scenarioName="+moRelativeScenario.moName+" TriggerSinceLastStateChange="+mnTriggerSinceLastStateChange;
//  }
};
