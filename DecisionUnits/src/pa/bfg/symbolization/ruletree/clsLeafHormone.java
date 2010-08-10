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
import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;

import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeHormone;
import bfg.utils.enumsOld.enumTypeLevelHormone;

/**
 *
 * This is the class description ...
 *
 * $Revision: 768 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-19 23:12:25 +0200 (Do, 19 Jul 2007) $: Date of last commit
 * @deprecated
 */
class clsLeafHormone extends clsRuleTreeLeaf
{
  public int meTypeHormone; //enumTypeHormone.THormone_UNDEFINED;
  public int meTypeLevelHormone = enumTypeLevelHormone.TLEVELHORMONE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafHormone oResult = new clsLeafHormone();
    if( oResult != null )
    {
      oResult.meTypeHormone      = enumTypeHormone.getInteger(clsXMLAbstractImageReader.getTagStringValue(poNode, "Hormone"));
      NamedNodeMap oAttribs = poNode.getAttributes();
      if( oAttribs.getNamedItem("level") != null )
      {
        String oValue  = oAttribs.getNamedItem("level").getNodeValue();
        oResult.meTypeLevelHormone = enumTypeLevelHormone.getInteger(oValue);
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
    boolean nResult = false;
    if( compare( null))//new clsContainerPercHormones() ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      nResult = true;
    }
    return nResult;
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
   * 15.09.2009, 13:43:25
   * 
   * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
   */
  @Override
  public boolean compare(clsDataBase poData) {
	    //leafBubblesVisible info:
	    boolean nResult = false;
//	    for( int i=0; i<poHormoneList.moHormones.size(); i++)
//	    {
//	      clsPerceptionHormone oHormone = (clsPerceptionHormone)poHormoneList.moHormones.get(i);
//	      if( meTypeHormone != oHormone.getType() )
//	        continue;
//
//	      if(  !meCompareOperator.compareInteger(oHormone.getLevel(), meTypeLevelHormone) )
//	        continue;
//	      nResult = true;
//	      break;  //not weighted now, if more than one perception exists!
//	    }
//	    if( mnNegated ) nResult = !nResult;
	    return nResult;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafHormone: ";
    oRetValue += super.toString();
    oRetValue += " typeHormone:"+enumTypeHormone.getString(meTypeHormone);
    oRetValue += " levelHormone:"+enumTypeLevelHormone.getString(meTypeLevelHormone);
    return oRetValue;
  }

};

