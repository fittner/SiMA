// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package pa.bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import pa.bfg.symbolization.brainimages.clsContainerPercVisionsObstacles;
import pa.bfg.symbolization.brainimages.clsIdentity;
import pa.bfg.symbolization.brainimages.clsImageAbstract;
import pa.bfg.symbolization.brainimages.clsImagePerception;
import pa.bfg.symbolization.brainimages.clsPerceptionVisionObstacle;
import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;

import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeCount;
import bfg.utils.enumsOld.enumTypeDistance;
import bfg.utils.enumsOld.enumTypeObstacle;
import bfg.utils.enumsOld.enumTypeSide;
import bfg.utils.enumsOld.enumTypeTrippleState;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;

/**
 *
 * This is the class description ...
 *
 * $Revision: 755 $:  Revision of last commit
 * $Author: langr $: Author of last commit
 * $Date: 2007-07-19 15:05:38 +0200 (Do, 19 Jul 2007) $: Date of last commit
 * @deprecated
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
  public boolean evaluateTree(clsSensorData poPerception, 
		  clsIdentity poBrainsIdentity, 
		  int[] poCompareResult)
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != eOptional.OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }    
    boolean retVal = false;
    if( compare( null))//new clsContainerPercVisionsObstacles(), poBrainsIdentity ) )
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

  /* (non-Javadoc)
  *
  * @author langr
  * 15.09.2009, 16:36:03
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
    boolean nResult = false;//compare2(poVisionList, poIdentity);
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

