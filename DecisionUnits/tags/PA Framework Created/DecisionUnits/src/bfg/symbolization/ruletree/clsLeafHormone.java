// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsContainerPerceptions;
import bfg.symbolization.brainimages.clsContainerPercHormones;
import bfg.symbolization.brainimages.clsPerceptionHormone;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeHormone;
import bfg.utils.enums.enumTypeLevelHormone;

/**
 *
 * This is the class description ...
 *
 * $Revision: 768 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-19 23:12:25 +0200 (Do, 19 Jul 2007) $: Date of last commit
 *
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
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity)
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean nResult = false;
    if( compare( poImage.moHormonesList ) )
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
  
  //---------------------------------------------------------------------------
  public boolean compare(clsContainerPercHormones poHormoneList)
  //---------------------------------------------------------------------------
  {
    //leafBubblesVisible info:
    boolean nResult = false;
    for( int i=0; i<poHormoneList.moHormones.size(); i++)
    {
      clsPerceptionHormone oHormone = (clsPerceptionHormone)poHormoneList.moHormones.get(i);
      if( meTypeHormone != oHormone.getType() )
        continue;

      if(  !meCompareOperator.compareInteger(oHormone.getLevel(), meTypeLevelHormone) )
        continue;
      nResult = true;
      break;  //not weighted now, if more than one perception exists!
    }
    if( mnNegated ) nResult = !nResult;
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

