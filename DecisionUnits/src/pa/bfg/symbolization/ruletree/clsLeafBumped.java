// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package pa.bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import pa.bfg.symbolization.brainimages.clsIdentity;
import pa.bfg.symbolization.brainimages.clsImageAbstract;
import pa.bfg.symbolization.brainimages.clsImagePerception;

import du.enums.eSensorExtType;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import bfg.utils.enums.eOptional;
import bfg.utils.enums.eTrippleState;

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
class clsLeafBumped extends clsRuleTreeLeaf
{
  public eTrippleState meBumped = eTrippleState.UNDEFINED;

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
        oResult.meBumped = eTrippleState.valueOf( oAttributes.getNamedItem("value").getNodeValue() );
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
    if( meOptionalType != eOptional.OPTIONAL )
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
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafBumped: ";
    oRetValue += super.toString();
    oRetValue += " isBumped="+meBumped.toString();
    return oRetValue;
  }

/* (non-Javadoc)
 *
 * @author zeilinger
 * 15.09.2009, 13:32:11
 * 
 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
 */
	@Override
	public boolean compare(clsDataBase poData) {
		//leafBubblesVisible info:
	    boolean nResult = false;
	
	    if( poData != null && meCompareOperator.compare(((clsBump)poData).getBumped(), meBumped) )
	    {
	      nResult = true;
	    }
	    if( mnNegated )
	    {
	      nResult = !nResult;
	    }
	    return nResult;
	}

};

