// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorData;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.clsTypeCompareOperator;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;

//commented during import by langr
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;


/**
 *
 * This is the class description ...
 *
 * $Revision: 958 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-03-11 12:53:54 +0100 (Di, 11 Mär 2008) $: Date of last commit
 *
 */
public abstract class clsRuleTreeLeaf extends clsRuleTreeElement{

  clsTypeCompareOperator  meCompareOperator = null;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement createRuleTreeLeaf(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsRuleTreeLeaf oResult = null;
    String oName = clsXMLAbstractImageReader.getNodeName( poNode );

//    Engine.log.println(" Node Name "+oName);

    if( oName == "leafVisionSegment" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafVisionSegment.create(poNode);
    }
    else if( oName == "leafDrive" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafDrive.create(poNode);
    }
    else if( oName == "leafEmotion" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafEmotion.create(poNode);
    }
    else if( oName == "leafHormone" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafHormone.create(poNode);
    }
    else if( oName == "leafLandscapesVisible" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafLandscapesVisible.create(poNode);
    }
    else if( oName == "leafObstaclesVisible" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafObstaclesVisible.create(poNode);
    }
    else if( oName == "leafBubblesVisible" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafBubblesVisible.create(poNode);
    }
    else if( oName == "leafBubblesScentable" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafBubblesScentable.create(poNode);
    }
//    else if( oName == "leafComplexEmotion" )
//    {
//      oResult = (clsRuleTreeLeaf)clsLeafComplexEmotion.create(poNode);
//    }
    else if( oName == "leafAcoustics" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafAcoustics.create(poNode);
    }
    else if( oName == "leafAboveEnergySource" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafAboveEnergySource.create(poNode);
    }   
    else if( oName == "leafBumped" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafBumped.create(poNode);
    }
    else if( oName == "leafOwnSocialLevel" )
    {
//       Engine.log.println(" Node Name "+oName);
      oResult = (clsRuleTreeLeaf)clsLeafOwnSocialLevel.create(poNode);
    }
    else if( oName == "leafAboveLandscape" )
    {
      oResult = (clsRuleTreeLeaf)clsLeafAboveLandscape.create(poNode);
    } 

    if( oResult != null )
    {
       NamedNodeMap oAttributes = poNode.getAttributes();
       String oComp  = oAttributes.getNamedItem("compare").getNodeValue();
       oResult.meCompareOperator = new clsTypeCompareOperator(oComp);
    }
    
    return oResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = super.toString();
    oRetValue += " compareOperator:"+meCompareOperator.getCompareOperator();
    return oRetValue;
  }

  @Override
  public abstract boolean evaluateTree(clsSensorData poPerception, 
			   						   clsIdentity poBrainsIdentity, 
				   					   int[] poCompareResult);
  
  public abstract void weight( clsImagePerception poImage, 
		  					   clsImageAbstract poAbstractImage, 
		  					   clsRuleCompareResult compareResult);

  public abstract boolean compare(clsDataBase poData); 
 
}
