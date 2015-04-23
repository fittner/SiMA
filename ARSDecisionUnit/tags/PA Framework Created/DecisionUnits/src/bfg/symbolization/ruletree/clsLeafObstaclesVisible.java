// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import bfg.symbolization.brainimages.clsContainerPercVisionsObstacles;
import bfg.symbolization.brainimages.clsContainerPerceptions;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsPerceptionVisionObstacle;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeCount;
import bfg.utils.enums.enumTypeDistance;
import bfg.utils.enums.enumTypeObstacle;
import bfg.utils.enums.enumTypeSide;
import bfg.utils.enums.enumTypeTrippleState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 755 $:  Revision of last commit
 * $Author: langr $: Author of last commit
 * $Date: 2007-07-19 15:05:38 +0200 (Do, 19 Jul 2007) $: Date of last commit
 *
 */
class clsLeafObstaclesVisible extends clsRuleTreeLeaf
{
  public int meNumber = enumTypeCount.TCOUNT_NONE;
  public int meSide = enumTypeSide.TSIDE_UNDEFINED;
  public int meDistance = enumTypeDistance.TDISTANCE_NULL;
  public int meObstacleType = enumTypeObstacle.TOBSTACLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node node)
  //---------------------------------------------------------------------------
  {
    clsLeafObstaclesVisible retVal = new clsLeafObstaclesVisible();
    if( retVal != null )
    {
      NamedNodeMap attribs = node.getAttributes();
      if( attribs.getNamedItem("number") != null )
      {
        String value  = attribs.getNamedItem("number").getNodeValue();
        retVal.meNumber = enumTypeCount.getInteger(value);
      }
      retVal.meSide = enumTypeSide.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "side"));
      retVal.meDistance = enumTypeDistance.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "distance"));
      retVal.meObstacleType = enumTypeObstacle.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "obstacletype"));
    }

    return retVal;
  }


  //---------------------------------------------------------------------------
  @Override
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity)
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }    
    boolean retVal = false;
    if( compare( poImage.moVisionObstaclesList, poBrainsIdentity ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      retVal = true;
    }
    return retVal;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //---------------------------------------------------------------------------
  {
    //return E_NOTIMPL; ;-)
  }

  //---------------------------------------------------------------------------
  public boolean compare(clsContainerPercVisionsObstacles poVisionList, clsIdentity poIdentity)
  //---------------------------------------------------------------------------
  {
    boolean nResult = compare2(poVisionList, poIdentity);
    if( mnNegated )
    {
      nResult = !nResult;
    }
    return nResult;
  }

  //---------------------------------------------------------------------------
  public boolean compare2(clsContainerPercVisionsObstacles visionList, clsIdentity poIdentity)
  //---------------------------------------------------------------------------
  {
    if( visionList == null)
    {
      //Engine.log.println( "Vision List is null - abort compare clsLeafBubblesVisible" );
      return false;
    }

    //Engine.log.println( toString() );

    //leafBubblesVisible info:
    int matchNumber = 0;
    for( int i=0; i<visionList.moObstacles.size(); i++)
    {
      clsPerceptionVisionObstacle vision = (clsPerceptionVisionObstacle)visionList.get(i);
      //Engine.log.println( vision.toString() );
      if( vision == null)
      {
        return false;
      }
      if( meSide != enumTypeSide.TSIDE_UNDEFINED && meSide != vision.meDirection) {
        continue;
      }
      if( meDistance != enumTypeDistance.TDISTANCE_UNDEFINED && meDistance != vision.meDistance) {
        continue;
      }
      if( meObstacleType != enumTypeTrippleState.TTRIPPLE_UNDEFINED && meObstacleType != vision.mnObstacleType ) {
        continue;
      }
      matchNumber++;
    }

    //compareNumbers

    if( meCompareOperator.compareInteger(matchNumber, meNumber) )
    {
      return true;
    }
    return false;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafBubblesVisible: ";
    oRetValue += super.toString();
    oRetValue += " number:"+enumTypeCount.getString(meNumber);
    oRetValue += " side:"+enumTypeSide.getString(meSide);
    oRetValue += " distance:"+enumTypeDistance.getString(meDistance);
    oRetValue += " obstacletype:"+enumTypeTrippleState.getString(meObstacleType);
    return oRetValue;
  }
};

