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
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
//import bfg.symbolization.brainimages.clsPerceptionSmellOMat;
//import bfg.symbolization.brainimages.clsContainerPercSmellOMats;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enumsOld.enumOptionalType;
import bfg.utils.enumsOld.enumTypeScentIntensity;
import bfg.utils.enumsOld.enumTypeTrippleState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafBubblesScentable extends clsRuleTreeLeaf
{
  public int mnIntensity = enumTypeScentIntensity.TSCENTINT_NONE;
  public int mnEnergyConsumer = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int mnEnergyProducer = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int mnTeammate = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node node)
  //---------------------------------------------------------------------------
  {
    clsLeafBubblesScentable retVal = new clsLeafBubblesScentable();
    if( retVal != null )
    {
      NamedNodeMap attribs = node.getAttributes();
      if( attribs.getNamedItem("intensity") != null )
      {
        String value  = attribs.getNamedItem("intensity").getNodeValue();
        retVal.mnIntensity = enumTypeScentIntensity.getInteger(value);
      }
      retVal.mnEnergyConsumer = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "energyconsumer"));
      retVal.mnEnergyProducer = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "energyproducer"));
      retVal.mnTeammate = enumTypeTrippleState.getInteger(clsXMLAbstractImageReader.getTagStringValue(node, "teammate"));
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
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }    boolean retVal = false;
    if( compare( null))//new clsContainerPercSmellOMats(), poBrainsIdentity ) )
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
  * 15.09.2009, 13:41:48
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
	    boolean retVal = false;

//	    if( mnIntensity == enumTypeScentIntensity.TSCENTINT_NONE && perceptionList.moSmells.size() == 0 ) {
//	      retVal = true;
//	    } else {
//	      for( int i=0; i<perceptionList.moSmells.size(); i++)
//	      {
//	        clsPerceptionSmellOMat smell = perceptionList.moSmells.get(i);
//
//	        if( !meCompareOperator.compareInteger( smell.meScentIntensity, mnIntensity) )
//	          continue;
//	        if( mnEnergyConsumer != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (mnEnergyConsumer==enumTypeTrippleState.TTRIPPLE_TRUE) != smell.mnEnergyConsumer)
//	          continue;
//	        if( mnEnergyProducer != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (mnEnergyProducer==enumTypeTrippleState.TTRIPPLE_TRUE) != smell.mnEnergyProducer)
//	          continue;
//	        if( mnTeammate != enumTypeTrippleState.TTRIPPLE_UNDEFINED && (mnTeammate==enumTypeTrippleState.TTRIPPLE_TRUE) != smell.mnTeamMate )
//	          continue;
//	        retVal = true;
//	        break;  //not weighted now, if more than one perception exists!
//	      }
//	    }
//	    if( mnNegated ) retVal = !retVal;
	    return retVal; }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafBubblesScentable: ";
    oRetValue += super.toString();
    oRetValue += " intensity:"+enumTypeScentIntensity.getString(mnIntensity);
    oRetValue += " energyConsumer:"+enumTypeTrippleState.getString(mnEnergyConsumer);
    oRetValue += " energyProducer:"+enumTypeTrippleState.getString(mnEnergyProducer);
    oRetValue += " teamMate:"+enumTypeTrippleState.getString(mnTeammate);
    return oRetValue;
  }
};




