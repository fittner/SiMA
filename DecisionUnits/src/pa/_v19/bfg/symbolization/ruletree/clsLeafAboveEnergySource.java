// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import pa._v19.bfg.symbolization.brainimages.clsIdentity;
import pa._v19.bfg.symbolization.brainimages.clsImageAbstract;
import pa._v19.bfg.symbolization.brainimages.clsImagePerception;
import pa._v19.bfg.symbolization.brainimages.clsPerceptionAboveEnergySource;

import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeTrippleState;

//import pkgBrainComplexEmotion.clsContainerComplexEmotion;


/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
class clsLeafAboveEnergySource extends clsRuleTreeLeaf
{
  public int meAboveEnergySource = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int mnConsumable = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
//    Engine.log.println("clsLeafAboveEnergySource::evaluateTree");
    clsLeafAboveEnergySource oResult = new clsLeafAboveEnergySource();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("value") != null )
      {
        oResult.meAboveEnergySource = enumTypeTrippleState.getInteger( oAttributes.getNamedItem("value").getNodeValue() );
        oResult.mnConsumable = enumTypeTrippleState.getInteger( oAttributes.getNamedItem("consumable").getNodeValue() );
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------                                    
  @Override
  public boolean evaluateTree( clsSensorData poPerception, 
			   				   clsIdentity poBrainsIdentity, 
			   				   int[] poCompareResult )
  //---------------------------------------------------------------------------
  {
//    Engine.log.println("clsLeafAboveEnergySource::evaluateTree");
    if( meOptionalType != eOptional.OPTIONAL)
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
    // - (langr): make sensor! 
    if( compare( null ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      oResult = true;
    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //--------------------------------------------------------------------------- 
  {
    //return E_NOTIMPL; ;-)
  }

  //---------------------------------------------------------------------------
  @Override
  public boolean compare(clsDataBase poData)
  //---------------------------------------------------------------------------
  {
	clsPerceptionAboveEnergySource oAboveES = new clsPerceptionAboveEnergySource();//(clsPerceptionAboveEnergySource)poData;
	  
    //leafBubblesVisible info:
    boolean nResult = false;

    int nIsAbove = 0; 
    if( oAboveES.isAbove() ) nIsAbove = 1;

    int nIsConsumable = 0;
    if( oAboveES.isConsumable() ) nIsConsumable = 1;

//    Engine.log.println(" - "+ nIsAbove + "<-->" + meAboveEnergySource);

    if( meCompareOperator.compareInteger(nIsAbove, meAboveEnergySource) && ( mnConsumable == enumTypeTrippleState.TTRIPPLE_UNDEFINED || meCompareOperator.compareInteger(  nIsConsumable, mnConsumable)) )
    {
      nResult = true;
    }
    if( mnNegated )
    {
      nResult = !nResult;
    }
    return nResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafAboveEnergySource: ";
    oRetValue += super.toString();
    oRetValue += " isAbove="+enumTypeTrippleState.getString(meAboveEnergySource);
    return oRetValue;
  }

};


