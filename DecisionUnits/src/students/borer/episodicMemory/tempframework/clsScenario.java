// File clsScenario.java
// July 19, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports
//import pkgBrainComplexEmotion.clsContainerComplexEmotionValue;
//import pkgBrainComplexEmotion.clsComplexEmotionValue;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
//import pkgBrainDesire.clsDesire;
//import pkgBrainDesire.clsDesireRecognitionProcess;
//import pkgBrainDesire.clsDesireScenario;
//import pkgBrainDesire.clsDesireState;
//import pkgXMLTools.clsXMLAbstractImageReader;
//import pkgXMLTools.clsXMLConfiguration;
//import pkgXMLTools.XMLException;
//import org.w3c.dom.Node;
//import org.w3c.dom.NamedNodeMap;
//import java.util.Vector;
//import java.util.TreeMap;
//import java.util.TreeSet;
//import java.util.Iterator;
//import pkgBrainAction.clsActionPlan;
//import pkgBrainAction.clsActionContainer;
//import pkgBrainImages.clsContainerAbstractImages;
//import pkgBrainEmotion.clsContainerEmotion;
//import pkgBrainDrive.clsContainerDrive;
//import pkgBrainEmotion.clsEmotion;
//import pkgBrainDrive.clsDrive;
//import pkgBrainRuleTree.clsRuleCompareResult;

/**
 *
 * This is the class description ...
 *
 * $Revision: 1195 $:  Revision of last commit
 * $Author: riediger $: Author of last commit
 * $Date: 2008-07-18 10:06:25 +0200 (Fr, 18 Jul 2008) $: Date of last commit
 *
 */
public class clsScenario {
  public int mnId;

  public String moName;
//  public String moDescription;
//
//  private clsStateContainer moStateList = new clsStateContainer();
//  private int mnEntryStateId;
//  public clsState moEntryState;
//
//  public clsActionPlan moActionPlan = new clsActionPlan();
//  public clsSuperEgoAction moSuperEgoAction = null;
//
//  public clsContainerDrive moTargetDriveList = new clsContainerDrive();
//  public clsContainerEmotion moTargetEmotionList = new clsContainerEmotion();
//  public clsContainerComplexEmotionValue moTargetComplexEmotionList = new clsContainerComplexEmotionValue();
//  public int mnDesireId;
//  public clsDesire moDesire;
//  //complex drives/emotions should be added!
//  public boolean mnTerminated = false;
////  protected clsRecognitionProcessContainer moRecognitionProcessList = new clsRecognitionProcessContainer();
//  public clsRecognitionProcessContainer moRecognitionProcessList = new clsRecognitionProcessContainer();
//
//  //---------------------------------------------------------------------------
//  public static clsScenario create(Node poScenarioNode, 
//                                   clsContainerAbstractImages poBrainsAbstractImages, 
//                                   clsContainerComplexEmotion poBrainsComplexEmotions)
//  //---------------------------------------------------------------------------
//  {
//    clsScenario oResult = new clsScenario();
//    create( poScenarioNode, poBrainsAbstractImages, poBrainsComplexEmotions, oResult);
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public static void create(Node poScenarioNode, 
//                            clsContainerAbstractImages poBrainsAbstractImages, 
//                            clsContainerComplexEmotion poBrainsComplexEmotions, 
//                            clsScenario poResult)
//  //---------------------------------------------------------------------------
//  {
//    NamedNodeMap oAttributes = poScenarioNode.getAttributes();
//    poResult.mnId   = Integer.parseInt( oAttributes.getNamedItem("ID").getNodeValue());
//
//    poResult.moName = clsXMLAbstractImageReader.getTagStringValue( poScenarioNode, "Name");
//    poResult.moDescription = clsXMLAbstractImageReader.getTagStringValue( poScenarioNode, "Description");
//
//    poResult.mnEntryStateId = Integer.parseInt(clsXMLAbstractImageReader.getTagStringValue( poScenarioNode, "EntryState"));
//
//    //state list
//
//    Vector oStateList = new Vector();
//    clsXMLAbstractImageReader.getNodeElementByName( poScenarioNode, "State", 1, oStateList);
//    for( int i=0; i<oStateList.size(); ++i)
//    {
//      clsState oState = null;
//
//      if( poResult == null ) 
//      { 
//        //Engine.log.println("ich bin null: " + poResult);
//      }
//
//      if(poResult instanceof clsDesireScenario)
//      {
//        //case we have a desire-scenario --> create desireStates!  
//        oState = clsDesireState.create( (Node)oStateList.get(i), poBrainsAbstractImages, poBrainsComplexEmotions );
//      }
//      else
//      {
////        Engine.log.println( "loading scenario state" );
//        oState = clsState.create( (Node)oStateList.get(i), poBrainsAbstractImages );
//      }
//      poResult.moStateList.add( oState, oState.mnId );
//    }
//
//    poResult.moEntryState = poResult.moStateList.get( poResult.mnEntryStateId );
//    poResult.moStateList.linkTransitionToState();
//
//    //actionPlan
//    Node oActionPlanNode = clsXMLAbstractImageReader.getNextNodeElementByName( poScenarioNode, "ActionPlan");
//    poResult.moActionPlan = clsActionPlan.create( oActionPlanNode );
//
//    //superEgoAction
//    Node oSuperEgoActionNode = clsXMLAbstractImageReader.getNextNodeElementByName( poScenarioNode, "SuperEgoAction");
//    if( oSuperEgoActionNode != null )
//    {
//      poResult.moSuperEgoAction = clsSuperEgoAction.create(oSuperEgoActionNode);
//    }
//
//    //drives
//    Vector oDriveList = new Vector();
//    clsXMLAbstractImageReader.getNodeElementByName( poScenarioNode, "TargetDrives", 1, oDriveList);
//    for( int i=0; i<oDriveList.size(); ++i)
//    {
//      clsDrive oTemp = clsDrive.createDrive( (Node)oDriveList.get(i) );
//      if (oTemp != null) {
//        poResult.moTargetDriveList.add( oTemp );
//      }
//    }
//
//    //emotions
//    Vector oEmotionList = new Vector();
//    clsXMLAbstractImageReader.getNodeElementByName( poScenarioNode, "TargetEmotions", 1, oEmotionList);
//    for( int i=0; i<oEmotionList.size(); ++i)
//    {
//      clsEmotion oTemp =  clsEmotion.createEmotion( (Node)oEmotionList.get(i) );
//
//      if (oTemp != null) {
//        poResult.moTargetEmotionList.add(oTemp);
//      }
//    }
//
//    //complexEmotions
//    Vector oComplexEmotionList = new Vector();
//    clsXMLAbstractImageReader.getNodeElementByName( poScenarioNode, "ComplexEmotion", 1, oComplexEmotionList);
//    for( int i=0; i<oComplexEmotionList.size(); ++i)
//    {
//      poResult.moTargetComplexEmotionList.add( clsComplexEmotionValue.create( (Node)oComplexEmotionList.get(i), poBrainsComplexEmotions ));
//    }
//    
//    String oDesireId = clsXMLAbstractImageReader.getTagStringValue( poScenarioNode, "DesireId");
//    if( !oDesireId.equals("") )
//    {
//      poResult.mnDesireId = Integer.parseInt(oDesireId);
//    }
//  }
//
//  //---------------------------------------------------------------------------
//  public static clsScenarioContainer createScenarioList(Vector poXMLFilePath, int poCreationId, clsContainerAbstractImages poAbstractImages, 
//                                                        clsContainerAbstractImages poBrainsAbstractImages, 
//                                                        clsContainerComplexEmotion poBrainsComplexEmotions)
//  //---------------------------------------------------------------------------
//  {
//    //load team-/bubble-specific scenarios
//    try
//    {
//      clsScenarioContainer oResult = new clsScenarioContainer();
//      Vector oScenarioNodes  = new Vector();
//
//      Vector imageFileList = clsXMLConfiguration.getConfig().getScenarioFileList( poXMLFilePath, poCreationId );
//      for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
//      {
//        String xmlFileName = (String)imageFileList.get(fileCount);
//        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
//
//        clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), "Scenario", 1, oScenarioNodes);
//
//        for(int i=0;i<oScenarioNodes.size();i++)                       
//        {                                    
//          Node oScenarioNode = (Node)oScenarioNodes.get(i);
//          clsScenario oScenario = clsScenario.create( oScenarioNode, poBrainsAbstractImages, poBrainsComplexEmotions );
//          //clsImageAbstract.importTargetLevels(oAbstractImage, oAbstractImageNode);
//          oResult.add( oScenario, oScenario.mnId );
//          //moLoadedImageList.add( oAbstractImage, oAbstractImage.mnImageId ); //add to global list for faster lookup
//        }
//      }
//
//      return oResult;
//    }
//    catch(XMLException ex)
//    {
//      Engine.log.println( "Error loading scenario: "+ex.getMessage() );
//      Engine.log.println( "  -> Bubble: "+poCreationId+" Team: "+poXMLFilePath + " Path: " + poXMLFilePath);
//    }
//    return null;
//  }
//
///*  //---------------------------------------------------------------------------
//  public static clsScenarioContainer createScenarioList(int pnBubbleId, int pnTeamId, 
//                                                        clsContainerAbstractImages poAbstractImages, 
//                                                        clsContainerAbstractImages poBrainsAbstractImages,
//                                                        clsContainerComplexEmotion poBrainsComplexEmotions)
//  //---------------------------------------------------------------------------
//  {
//    //load team-/bubble-specific scenarios
//    try
//    {
//      clsScenarioContainer oResult = new clsScenarioContainer();
//      Vector oScenarioNodes  = new Vector();
//
//      Vector imageFileList = clsXMLConfiguration.getConfig().getScenarioFileList( pnBubbleId, pnTeamId );
//      for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
//      {
//        String xmlFileName = (String)imageFileList.get(fileCount);
//        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
//
//        clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), "Scenario", 1, oScenarioNodes);
//
//        for(int i=0;i<oScenarioNodes.size();i++)                       
//        {
//          Node oScenarioNode = (Node)oScenarioNodes.get(i);
//          clsScenario oScenario = clsScenario.create( oScenarioNode, poBrainsAbstractImages, poBrainsComplexEmotions );
//          //clsImageAbstract.importTargetLevels(oAbstractImage, oAbstractImageNode);
//          oResult.add( oScenario, oScenario.mnId );
//          //moLoadedImageList.add( oAbstractImage, oAbstractImage.mnImageId ); //add to global list for faster lookup
//        }
//      }
//
//      return oResult;
//    }
//    catch(clsXMLAbstractImageReader.XMLException ex)
//    {
//      //Engine.log.println( "Error loading scenario: "+ex.getMessage() );
//      //Engine.log.println( "  -> Bubble: "+pnBubbleId+" Team: "+pnTeamId );
//    }
//    return null;
//  }
//*/
//  //---------------------------------------------------------------------------
//  public clsRecognitionProcessContainer triggerScenarioRecognition(clsRuleCompareResult poCompareResult, clsRecognitionProcessContainer poInitialRecognitionProcesses, clsRecognitionProcessContainer poStateChangeCausedActions, clsRecognitionProcessResult poRecognizedResult )
//  //---------------------------------------------------------------------------
//  {
////Engine.log.println("clsScenario::triggerScenarioRecognition start");
////Engine.log.println("WEIL 1: moRecognitionProcessList="+moRecognitionProcessList.size() );
//    clsRecognitionProcessContainer oResult = new clsRecognitionProcessContainer();
//
//    TreeSet oDiscardRecognitions = new TreeSet();
//
//    //check started recognition
//    for( int i=0; i<moRecognitionProcessList.size();++i )
//    {
////      Engine.log.println("trigger recognition ??????  number " + i);
//      clsRecognitionProcess oRecognition = null;;
//      oRecognition = moRecognitionProcessList.get(i);
//      int nRecognitionResult = oRecognition.triggerScenarioRecognition( poCompareResult, poStateChangeCausedActions, poRecognizedResult, new clsActionContainer() );
//      if( nRecognitionResult == 2 )
//      {
//        oResult.add( oRecognition );
//        oDiscardRecognitions.add( new Integer(i) );
////        Engine.log.println("clsScenario - SCENARIO recognized");
//      }
//      else if( nRecognitionResult == -1 )
//      {
//        oDiscardRecognitions.add( new Integer(i) );        
//        Engine.log.println("clsScenario - SCENARIO discarded");
//      }
//      else if( nRecognitionResult == 1 )
//      {
////        Engine.log.println("clsScenario - SCENARIO State changed");
//      }
//    }
////Engine.log.println("WEIL 2: moRecognitionProcessList="+moRecognitionProcessList.size() );
//    //delete matching or aborted recognition processes from list
//    Vector oHelper = new Vector();
//    Iterator it = oDiscardRecognitions.iterator();
//    while (it.hasNext())  //reverse order
//    {
//      oHelper.add(0, it.next());
//    }
//    for( int i=0; i<oHelper.size(); ++i )
//    {
//      moRecognitionProcessList.remove( ((Integer)oHelper.get(i)).intValue() );
//    }
////Engine.log.println("WEIL 3: moRecognitionProcessList="+moRecognitionProcessList.size() );
//    //check for new recognition
//    clsRecognitionProcess oRecognitionProcess = this.getNewRecognitionProcess(new clsActionContainer());
//    //if there is no recently initialized process --> initialize new/next one
//    if( oRecognitionProcess != null )
//    {
//      int nRecognition = oRecognitionProcess.triggerScenarioRecognition( poCompareResult, poStateChangeCausedActions, poRecognizedResult, new clsActionContainer() );
//      
//  //    Engine.log.println("clsRecognitionProcess returned: " + nRecognition);
//      
//      if( nRecognition == 2 )
//      {
//        //case a scenario consist of only ONE transition (and therefor 2 states)
//        oResult.add( oRecognitionProcess );
//  //      Engine.log.println("clsScenario - SCENARIO recognized");
//      }
//      else if( nRecognition == 1 )
//      {
//        Engine.log.println("clsScenario -SCENARIO process started");
//        moRecognitionProcessList.add( oRecognitionProcess );
//        poInitialRecognitionProcesses.add( oRecognitionProcess );  
//        poRecognizedResult.moInitialized.add(oRecognitionProcess);
//        Engine.log.println(" recognitionProcesses: "+moRecognitionProcessList.size());
//        //started recognition processes are listed in STMSnippet
//        //when the STM looses a snippet the rec. proc. will be also discarded
//      }
//    }
//
////Engine.log.println("WEIL 4: moRecognitionProcessList="+moRecognitionProcessList.size() );
//
//
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public clsRecognitionProcess getNewRecognitionProcess(clsActionContainer poInnerAction)
//  //---------------------------------------------------------------------------
//  {
//    clsRecognitionProcess oResult = null;
//    if( !moRecognitionProcessList.containsInitializedProcess() )
//    {
////Engine.log.println("WEIL: moRecognitionProcessList="+moRecognitionProcessList.size() );
//        oResult = new clsRecognitionProcess(this);
//    }
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public boolean removeRecognitionProcess( clsRecognitionProcess poRecognitionProcess )
//  //---------------------------------------------------------------------------
//  {
//    //Engine.log.println("clsScenario::removeRecognitionProcess");
//    boolean oResult = moRecognitionProcessList.removeIt( (Object)poRecognitionProcess );
//    if( oResult == false )
//    {
//      //Engine.log.println("RemovedObjectNotFound");
//    }
//    else
//    {
//      //Engine.log.println("Object removed");
//    }
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public void getTransitionIdList(TreeMap oTreeMap)
//  //---------------------------------------------------------------------------
//  {
//    moRecognitionProcessList.getTransitionIdList(oTreeMap);
//  }
//
//  //---------------------------------------------------------------------------
//  public int getRecognitionProcessCount()
//  //---------------------------------------------------------------------------
//  {
//    return moRecognitionProcessList.size();
//  }
//
//  //---------------------------------------------------------------------------
//  public String getActiveScenarioString()
//  //---------------------------------------------------------------------------
//  {
//    String oResult = "";
//
//    if( moRecognitionProcessList.size() > 0 )
//    {
//      oResult += "ID="+mnId+" Name="+moName+" ";
//      for( int i=0;i<moRecognitionProcessList.size();++i )
//      {
//        clsRecognitionProcess oProc = moRecognitionProcessList.get(i);
//		if( (moRecognitionProcessList != null) && (oProc instanceof clsDesireRecognitionProcess) )
//		{
//			clsDesireRecognitionProcess oRecProc = (clsDesireRecognitionProcess)oProc;
//			if( oRecProc != null )
//			{
//				oResult += "RecProc:" + i + " StateId=" + oRecProc.getHead().mnId + " StateName=" + oRecProc.getHead().moName;
//			}
//		}
//      }
//    }
//    return oResult;
//  }
//
//  public clsStateContainer getStateContainer(){
//	  return moStateList;
//  }
//
//  //---------------------------------------------------------------------------
//  public String toString()
//  //---------------------------------------------------------------------------
//  {
//    String oResult = "";
//
//    oResult += "ID="+mnId+" Name="+moName+" Descr="+moDescription+"\n";
//    oResult += "  StateList="+moStateList.toString()+"\n";       
//    oResult += "  EntryState="+moEntryState.mnId+"\n";
//    oResult += "  ActionPlan="+moActionPlan.toString()+"\n";
//    oResult += "  EmotionList="+moTargetEmotionList.toString()+"\n";
//    oResult += "  DriveList="+moTargetDriveList.toString()+"\n";
//    oResult += "  ComplexEmotionList="+moTargetComplexEmotionList.toString()+"\n";
//    
//    if( mnTerminated ) oResult+="ScenarioTerminated";
//
//    return oResult;
//  }
};
