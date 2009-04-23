// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import bw.utils.enums.deprecated.enumOptionalType;
import bw.utils.enums.deprecated.enumTypeSocialLevel;
import bw.utils.enums.deprecated.enumTypeEntityMessages;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsContainerPerceptions;


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
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, 
                              /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, 
                              clsIdentity poBrainsIdentity)
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
    if( compare( poBrainsPerceptions.get(poBrainsPerceptions.moPerceptions.size()-1).meSocialLevel ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      oResult = true;
    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //--------------------------------------------------------------------------- 
  {
    //return E_NOTIMPL; ;-)
  }

  //---------------------------------------------------------------------------
  public boolean compare(int pnSocialLevel)
  //---------------------------------------------------------------------------
  {
    boolean nResult = false;

//    Engine.log.println("soz.lvl2: "+toString());

    if( meCompareOperator.compareInteger(pnSocialLevel, meSocialLevel) )
    {
      nResult = true;
//        Engine.log.println("WE HAVE A MATCH");
    }
    if( mnNegated )
    {
      nResult = !nResult;;
    }
    return nResult;
  }

  //---------------------------------------------------------------------------
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafOwnSocialLevel: ";
    oRetValue += super.toString();
    oRetValue += " typeSocialLevel:"+enumTypeSocialLevel.getString(meSocialLevel);
    return oRetValue;
  }

};

