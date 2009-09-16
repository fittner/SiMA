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
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeDrive;
import bfg.utils.enumsOld.enumTypeLevelDrive;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;

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
  @Override
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //---------------------------------------------------------------------------
  {
    //return E_NOTIMPL; ;-)
  }
  
  /* (non-Javadoc)
  *
  * @author langr
  * 15.09.2009, 13:42:43
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
 	// TODO (langr) - Auto-generated method stub
 	return false;
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
  @Override
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

