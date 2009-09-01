// File clsAbstractImage.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import decisionunit.itf.sensors.clsSensorData;

import bfg.tools.cls0to1;
import bfg.tools.xmltools.XMLException;
import bfg.tools.xmltools.clsXMLConfiguration;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.enumTypeTrippleState;
//import bfg.symbolization.recognition.clsScenarioContainer;
//import bfg.symbolization.recognition.clsSuperEgoAction;
//import bfg.symbolization.recognition.clsDesireContainer;
import bfg.symbolization.ruletree.clsRuleTreeNode;
import bfg.symbolization.ruletree.clsRuleTreeElement;
import bfg.symbolization.ruletree.clsRuleCompareResult;

//TODO (langr): are these necessary???
//import pkgTools.cls0to1;
//import pkgBrainAction.clsActionPlan;
//import pkgBrainEmotion.clsEmotion;
//import pkgBrainDrive.clsDrive;
//import pkgBrain.clsIdentity;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;

/**
 *
 * This is the class description ...
 *
 * $Revision: 1097 $:  Revision of last commit
 * $Author: riediger $: Author of last commit
 * $Date: 2008-06-16 17:33:44 +0200 (Mo, 16 Jun 2008) $: Date of last commit
 *
 */
public class clsImageAbstract // extends clsImageGeneric //uncommented at import langr --> no drive/emotion 
{
  public clsRuleTreeNode moRuleRoot = new clsRuleTreeNode();
  public int mnImageId = -1;
  public String moName = "";
  public String moDescription = "";
  public boolean mnFullMatchRequired = false;

//  public clsActionPlan moActionPlan;
//  public clsSuperEgoAction moSuperEgoAction;

//  public static clsContainerAbstractImages moLoadedImageList = new clsContainerAbstractImages();

  //---------------------------------------------------------------------------
  public clsImageAbstract(int pnID, String poName, String poDescription)
  //---------------------------------------------------------------------------
  {
    mnImageId = pnID; 
    moName = poName;
    moDescription = poDescription;
  }


  //---------------------------------------------------------------------------
  public static clsContainerAbstractImages createImageAbstractList(Vector<String> poXMLFilePath, int poCreationId)
  //---------------------------------------------------------------------------
  {
    try
    {
      clsContainerAbstractImages oResult   = new clsContainerAbstractImages();

      Vector<String> imageFileList = clsXMLConfiguration.getAbstractImageFileList( poXMLFilePath, poCreationId );
      for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
      {
        String xmlFileName = (String)imageFileList.get(fileCount);
        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);

        Vector<Node> oAbstractImageNodes  = new Vector<Node>();
        clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), 
                                                         clsXMLAbstractImageReader.moAbstractImage, 1, oAbstractImageNodes);

        for(int i=0;i<oAbstractImageNodes.size();i++)                       
        {
          Node oAbstractImageNode = (Node)oAbstractImageNodes.get(i);
          clsImageAbstract oAbstractImage = clsImageAbstract.create( oAbstractImageNode );
          
          //don't use neither emotion-values nor drives now (import langr) 
          //clsImageAbstract.importTargetLevels(oAbstractImage, oAbstractImageNode);
          
          oResult.moAbstractImageList.put( oAbstractImage.mnImageId, oAbstractImage );
          //moLoadedImageList.add( oAbstractImage, oAbstractImage.mnImageId ); //add to global list for faster lookup
        }
      }

      return oResult;
    }
    catch(XMLException ex)
    {
      //Engine.log.println( "Error loading abstract images: "+ex.getMessage() );
      //Engine.log.println( "  -> Bubble: "+poCreationId+" Team: "+poXMLFilePath );
    }
    return null;
  }

//  //---------------------------------------------------------------------------
//  public static void importTargetLevels(clsImageAbstract poAbstractImage, Node poAbstractImageNode) throws XMLException
//  //---------------------------------------------------------------------------
//  {
//    Vector oNodeList  = new Vector();
//    clsXMLAbstractImageReader.getNodeElementByName( poAbstractImageNode, clsXMLAbstractImageReader.moTargetLevels, 1, oNodeList);
//
//    if( oNodeList.size() > 0 )
//    {
//      Node oTargetLevel = (Node)oNodeList.get(0);
//      Vector oSubNodes = clsXMLAbstractImageReader.getSubNodes(oTargetLevel);
//      for( int i=0; i<oSubNodes.size(); i++)
//      {
//        Node oCurrentNode = ((Node)oSubNodes.get(i));
//        String oNodeName = oCurrentNode.getNodeName();
//        if( oNodeName.toLowerCase().equals("emotion") )
//        {
//          poAbstractImage.moEmotionList.add( clsEmotion.createEmotion(oCurrentNode) );
//        }
//        else if( oNodeName.toLowerCase().equals("drive") )
//        {
//          poAbstractImage.moDriveList.add( clsDrive.createDrive(oCurrentNode) );
//        }
//        else
//        {
//          //Engine.log.println( "Unknown TargetLevelType");
//        }
//      }
//    }
//  }
                                                                          
  //---------------------------------------------------------------------------
  public static clsImageAbstract create(Node poAbstractImageNode)
  //---------------------------------------------------------------------------
  {
    NamedNodeMap oAattribs = poAbstractImageNode.getAttributes();
    String oId  = oAattribs.getNamedItem("ID").getNodeValue();
    String oName = oAattribs.getNamedItem("name").getNodeValue();
    String oFullMatchRequired = oAattribs.getNamedItem("fullMatchRequired").getNodeValue();
    String oDescription = clsXMLAbstractImageReader.getTagStringValue(poAbstractImageNode, "Description");

    int nId = clsXMLAbstractImageReader.stringToInteger(oId);

    clsImageAbstract oResult = null;

//    clsImageAbstract oCacheImage = moLoadedImageList.get(nId);
//    if( oCacheImage != null )
//    {
//      oResult = oCacheImage;
//    }
//    else
    {
      //read xml-definition
      oResult = new clsImageAbstract( clsXMLAbstractImageReader.stringToInteger(oId), oName, oDescription );
      Node oTreeRoot = clsXMLAbstractImageReader.getNextNodeElementByName(poAbstractImageNode, clsXMLAbstractImageReader.moTreeRoot);
      oResult.moRuleRoot = (clsRuleTreeNode)clsRuleTreeElement.createRuleTreeElement( oTreeRoot );
    }

    oResult.mnFullMatchRequired = enumTypeTrippleState.getBoolean( oFullMatchRequired );



//    //superEgoAction
//    Node oSuperEgoActionNode = clsXMLAbstractImageReader.getNextNodeElementByName( poAbstractImageNode, "SuperEgoAction");
//    if( oSuperEgoActionNode != null )
//    {
//      oResult.moSuperEgoAction = clsSuperEgoAction.create(oSuperEgoActionNode);
//    }

    
//    oResult.moActionPlan = clsImageAbstractAction.getAction( oResult.mnImageId );
//    if( oResult.moActionPlan == null )
//    {
//      //Engine.log.println( "AP is NULL");    
//    }
//    else
//    {
//     // Engine.log.println( "AP: "+oResult.moActionPlan.toString() );
//    }

    return oResult;
  }
  
  //---------------------------------------------------------------------------
  public cls0to1 evaluateTree(clsSensorData poPerception, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsIdentity poBrainsIdentity) 
  //---------------------------------------------------------------------------
  {
     float eps = 0.05f;

     int[] poCompareResult = {0,0};
     moRuleRoot.evaluateTree(poPerception, poBrainsIdentity, poCompareResult); //poBrainsComplexEmotions,
     cls0to1 oResult = new cls0to1( ((float)(100/poCompareResult[1])*poCompareResult[0])/100 );
     if( mnFullMatchRequired && oResult.get() < (1-eps) )
     {
//        Engine.log.println("reset match to 0 from "+oResult.get()+" under eps:"+eps);
        oResult.set(0.0f);
     }
//     Engine.log.println( "Match on ID="+mnImageId+" Match="+oResult.toString()+" | ("+moName+")" );
     return oResult;
  }

  //---------------------------------------------------------------------------
  public clsRuleCompareResult getCompareResult( clsSensorData poPerception, 
		  										clsIdentity poBrainsIdentity ) 
  //---------------------------------------------------------------------------
  {
    clsRuleCompareResult oResult = new clsRuleCompareResult(this, (evaluateTree(poPerception, poBrainsIdentity )).get());

//    oResult.setTargetDrives(moDriveList);
//    oResult.setTargetEmotions(moEmotionList);

    //first attempt-implementation of a focus of attention - if active scenario/desire --> match * 1.2f 
//    boolean moStressValue = false;
//    if( poBrainsScenarioList.getTransitionIdList().containsKey( new Integer(mnImageId) ) )
//    {
//      moStressValue = true;
//    }
//    else if( poBrainsDesireList.getTransitionIdList().containsKey( new Integer(mnImageId) ) )
//    {
//      moStressValue = true;
//    }
//    if( moStressValue ) oResult.stress(poBrainsIdentity);

    return oResult;
  }
//
//  //---------------------------------------------------------------------------
//  public String toString() 
//  //---------------------------------------------------------------------------
//  {
//
//    String oResult =  moName+" - ";
//    oResult += moDescription+":\n";
//    oResult += moRuleRoot.toString();
//    oResult += super.toString();
//    oResult += " ImageId: "+mnImageId;
//    if( moActionPlan == null )
//    {
//       //Engine.log.println( "ActionPlan was not prperly loaded - id="+mnImageId);
//    }
//	 else
//	 {
//	    oResult += " ActionPlan:"+moActionPlan.getCount();
//	 }
//    
//    return oResult;
//  }
};
