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
import bfg.utils.enumsOld.enumOptionalType;
import bfg.utils.enumsOld.enumTypeEntityMessages;
import bfg.utils.enumsOld.enumTypeSocialLevel;


class clsLeafOwnSocialLevel extends clsRuleTreeLeaf
{
  public int meSocialLevel = enumTypeSocialLevel.TSOCIALLEVEL_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafOwnSocialLevel oResult = new clsLeafOwnSocialLevel();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("level") != null )
      {
        oResult.meSocialLevel = enumTypeEntityMessages.getInteger( oAttributes.getNamedItem("level").getNodeValue() );
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
//    Engine.log.println("soz.lvl: "+toString());

    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
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

  /* (non-Javadoc)
  *
  * @author langr
  * 15.09.2009, 16:37:28
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
	    boolean nResult = false;
//
////	    Engine.log.println("soz.lvl2: "+toString());
//
//	    if( meCompareOperator.compareInteger(pnSocialLevel, meSocialLevel) )
//	    {
//	      nResult = true;
////	        Engine.log.println("WE HAVE A MATCH");
//	    }
//	    if( mnNegated )
//	    {
//	      nResult = !nResult;;
//	    }
	    return nResult;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafOwnSocialLevel: ";
    oRetValue += super.toString();
    oRetValue += " typeSocialLevel:"+enumTypeSocialLevel.getString(meSocialLevel);
    return oRetValue;
  }
};

