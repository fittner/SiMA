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
import bfg.utils.enumsOld.enumOptionalType;
import bfg.utils.enumsOld.enumTypeCount;
import bfg.utils.enumsOld.enumTypeDistance;
import bfg.utils.enumsOld.enumTypeSide;
import bfg.utils.enumsOld.enumTypeTrippleState;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsContainerPercVisionsEntities;
import bfg.symbolization.brainimages.clsPerceptionVisionEntity;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafBubblesVisible extends clsRuleTreeLeaf
{
  public int meNumber = enumTypeCount.TCOUNT_NONE;
  public int meSide = enumTypeSide.TSIDE_UNDEFINED;
  public int meDistance = enumTypeDistance.TDISTANCE_NULL;
  public int meEnergyConsumer = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int meEnergyProducer = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int meTeammate = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node node)
  //---------------------------------------------------------------------------
  {
    clsLeafBubblesVisible retVal = new clsLeafBubblesVisible();
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
      retVal.meEnergyConsumer = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "energyconsumer"));
      retVal.meEnergyProducer = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "energyproducer"));
      retVal.meTeammate = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "teammate"));
    }

    return retVal;
  }


  //---------------------------------------------------------------------------
  @Override
  public boolean evaluateTree( clsSensorData poPerception, 
		  clsIdentity poBrainsIdentity, 
		  int[] poCompareResult)
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }    
    boolean retVal = false;
    if( compare( null))//new clsContainerPercVisionsEntities(), poBrainsIdentity ) )
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
  * 15.09.2009, 13:45:21
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
    boolean nResult = false; //compare2(poVisionList, poIdentity);
    if( mnNegated )
    {
      nResult = !nResult;
    }
    return nResult;
 }

  //---------------------------------------------------------------------------
  public boolean compare2(clsContainerPercVisionsEntities visionList, clsIdentity poIdentity)
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
    for( int i=0; i<visionList.moEntities.size(); i++)
    {
      clsPerceptionVisionEntity vision = (clsPerceptionVisionEntity)visionList.get(i);
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
      if( meEnergyConsumer != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (meEnergyConsumer==enumTypeTrippleState.TTRIPPLE_TRUE) != vision.mnEnergyConsumer) {
        continue;
      }
      if( meEnergyProducer != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (meEnergyProducer==enumTypeTrippleState.TTRIPPLE_TRUE) != vision.mnEnergyProducer) {
        continue;
      }
      if( meTeammate != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (meTeammate==enumTypeTrippleState.TTRIPPLE_TRUE) != (vision.mnTeamId==poIdentity.mnTeamId) ) {
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
    oRetValue += " energyConsumer:"+enumTypeTrippleState.getString(meEnergyConsumer);
    oRetValue += " energyProducer:"+enumTypeTrippleState.getString(meEnergyProducer);
    oRetValue += " teamMate:"+enumTypeTrippleState.getString(meTeammate);
    return oRetValue;
  }
};

