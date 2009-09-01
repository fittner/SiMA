// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsSensorData;
import enums.eSensorExtType;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeTrippleState;

//import pkgBrainComplexEmotion.clsContainerComplexEmotion;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafBumped extends clsRuleTreeLeaf
{
  public int meBumped = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafBumped oResult = new clsLeafBumped();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("value") != null )
      {
        oResult.meBumped = enumTypeTrippleState.getInteger( oAttributes.getNamedItem("value").getNodeValue() );
      }
    }
    return oResult;
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
    }
    boolean oResult = false;
    if( compare( (clsBump)poPerception.getSensorExt(eSensorExtType.BUMP) ) )
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
  public boolean compare(clsBump poBumped)
  //---------------------------------------------------------------------------
  {
    //leafBubblesVisible info:
    boolean nResult = false;

    int nIsBumped = 0; 
    if( poBumped.mnBumped ) nIsBumped = 1;

    if( meCompareOperator.compareInteger(nIsBumped, meBumped) )
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
    String oRetValue = "clsLeafBumped: ";
    oRetValue += super.toString();
    oRetValue += " isBumped="+enumTypeTrippleState.getString(meBumped);
    return oRetValue;
  }

};

