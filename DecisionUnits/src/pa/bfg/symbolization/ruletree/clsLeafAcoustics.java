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

import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
//import bfg.symbolization.brainimages.clsPerceptionAcoustic;
//import bfg.symbolization.brainimages.clsContainerPercAcoustics;
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeEntityMessages;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
class clsLeafAcoustics extends clsRuleTreeLeaf
{
  public int meEntityMessage = enumTypeEntityMessages.TENTITYMESSAGE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafAcoustics oResult = new clsLeafAcoustics();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("EntityMessage") != null )
      {
        oResult.meEntityMessage = enumTypeEntityMessages.getInteger( oAttributes.getNamedItem("EntityMessage").getNodeValue() );
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
    if( compare( null))//new clsContainerPercAcoustics() ) )
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
  * 15.09.2009, 13:40:23
  * 
  * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
  */
 @Override
 public boolean compare(clsDataBase poData) {
    //leafBubblesVisible info:
    boolean nResult = false;
//
////	    Engine.log.println("acoustics perception:" + poAcoustics.size() );
//
//    for( int i=0; i<poAcoustics.moAcoustics.size(); ++i )
//    {
//      clsPerceptionAcoustic oAcoustic = poAcoustics.moAcoustics.get(i);
////	      Engine.log.println(oAcoustic.getMessageType()+"==" +  meEntityMessage);
//      if( meCompareOperator.compareInteger(oAcoustic.getMessageType(), meEntityMessage) )
//      {
//        nResult = true;
////	        Engine.log.println("WE HAVE A MATCH");
//      }
//    }
//    if( mnNegated )
//    {
//      nResult = !nResult;;
//    }
    return nResult; 
  }


  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafAcoustic: ";
    oRetValue += super.toString();
    oRetValue += " typeAcoustic:"+enumTypeEntityMessages.getString(meEntityMessage);
    return oRetValue;
  }

};

