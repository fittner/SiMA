// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bw.utils.enums.deprecated.enumOptionalType;
import bw.utils.enums.deprecated.enumTypeLevelDrive;
import bw.utils.enums.deprecated.enumTypeDrive;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsContainerPerceptions;

//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
//import pkgBrainDrive.clsDrive;
//import pkgBrainDrive.clsContainerDrive;


/**
 *
 * This is the class description ...
 *
 * $Revision: 768 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-19 23:12:25 +0200 (Do, 19 Jul 2007) $: Date of last commit
 *
 */
class clsLeafDrive extends clsRuleTreeLeaf
{
  public int meTypeDrive; //enumTypeDrive.TDRIVE_UNDEFINED;
  public int meTypeLevelDrive = enumTypeLevelDrive.TLEVELDRIVE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafDrive oResult = new clsLeafDrive();
    if( oResult != null )
    {
      oResult.meTypeDrive      = enumTypeDrive.getInteger(clsXMLAbstractImageReader.getTagStringValue(poNode, "drive"));
      NamedNodeMap oAttribs = poNode.getAttributes();
      if( oAttribs.getNamedItem("level") != null )
      {
        String oValue  = oAttribs.getNamedItem("level").getNodeValue();
        oResult.meTypeLevelDrive = enumTypeLevelDrive.getInteger(oValue);
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
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
    //TODO langr: no drives imported 
//    if( compare( poImage.moDriveList ) )
//    {
//      //Engine.log.println( "Match with leaf: " + this.toString() );
//      poCompareResult[0]++;
//      nResult = true;
//    }
    return nResult;
  }
  
  //---------------------------------------------------------------------------
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //---------------------------------------------------------------------------
  {
    //return E_NOTIMPL; ;-)
  }
  
//  //---------------------------------------------------------------------------
//  public boolean compare(clsContainerDrive poDriveList)
//  //---------------------------------------------------------------------------
//  {
//    //leafBubblesVisible info:
//    boolean nResult = false;
//    for( int i=0; i<poDriveList.size(); i++)
//    {
//      clsDrive oDrive = (clsDrive)poDriveList.get(i);
//      if( meTypeDrive != oDrive.getType() )
//        continue;
//
//      if(  !meCompareOperator.compareInteger(oDrive.getLevel(), meTypeLevelDrive) )
//        continue;
//      nResult = true;
//      break;  //not weighted now, if more than one perception exists!
//    }
//    if( mnNegated ) nResult = !nResult;
//    return nResult;
//  }

  //---------------------------------------------------------------------------
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafDrive: ";
    oRetValue += super.toString();
    oRetValue += " typeDrive:"+enumTypeDrive.getString(meTypeDrive);
    oRetValue += " levelDrive:"+enumTypeLevelDrive.getString(meTypeLevelDrive);
    return oRetValue;
  }
};

