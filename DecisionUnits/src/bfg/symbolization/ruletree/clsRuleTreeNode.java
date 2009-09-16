// File clsRuleTreeNode.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import decisionunit.itf.sensors.clsSensorData;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enumsOld.enumBooleanOperator;
import bfg.symbolization.brainimages.clsIdentity;

//no emotions - prohibitet in import from langr
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
public class clsRuleTreeNode extends clsRuleTreeElement {

  public Vector moSubList = new Vector();
  public int meBooleanOperator = enumBooleanOperator.TBOOLOP_AND;

  //---------------------------------------------------------------------------
  public static clsRuleTreeNode createRuleTreeNode(Node poNode)
  //---------------------------------------------------------------------------
  {
      clsRuleTreeNode oResult = new clsRuleTreeNode();
      Vector oSubNodes = clsXMLAbstractImageReader.getSubNodes(poNode);
      for(int i=0; i<oSubNodes.size();i++)
      {
        oResult.moSubList.add( clsRuleTreeElement.createRuleTreeElement( (Node)oSubNodes.get(i)) );
      }

      NamedNodeMap oAttributes = poNode.getAttributes();
      String oBooleanOperator  = oAttributes.getNamedItem("booleanOperator").getNodeValue();
      oResult.meBooleanOperator = enumBooleanOperator.getInteger( oBooleanOperator );
      return oResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public boolean evaluateTree(clsSensorData poPerception, 
		  					  clsIdentity poBrainsIdentity, 
		  					  int[] poCompareResult )
  //---------------------------------------------------------------------------
  {
    boolean nAnyTrue = false;
    boolean nAllTrue = true;
    for( int i=0; i<moSubList.size();i++)
    {
      boolean nMatch = ((clsRuleTreeElement)moSubList.elementAt(i)).evaluateTree(poPerception, poBrainsIdentity, poCompareResult);
      if( nMatch ) 
        nAnyTrue = true;
      if( !nMatch)
        nAllTrue = false;
      if( checkBooleanOperator(nAnyTrue, nAllTrue, false) ) //aborts if OPERATOR_OR is fulfilled the first time
        break;                                            //no further loop necessary
    }
    return checkBooleanOperator(nAnyTrue, nAllTrue, true);
  }

  //---------------------------------------------------------------------------
  private boolean checkBooleanOperator(boolean pnAnyTrue, boolean pnAllTrue, boolean pnFinished)
  //---------------------------------------------------------------------------
  {
    boolean nResult = false;
    switch( meBooleanOperator )
    {
    case enumBooleanOperator.TBOOLOP_UNDEFINED:
      break;
    case enumBooleanOperator.TBOOLOP_AND:
      if( pnFinished && pnAllTrue)
        nResult = true;        
      break;
    case enumBooleanOperator.TBOOLOP_OR:
      if( !pnFinished && pnAnyTrue )
        nResult = true;
      if( pnFinished && pnAnyTrue)
        nResult = true;
      break;
      }
      return nResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsRuleTreeNode: ";
    oRetValue += super.toString();
    oRetValue += " subObjectCount:"+moSubList.size();
    oRetValue += " boolOperator:"+enumBooleanOperator.getString(meBooleanOperator);

    oRetValue += " sub object list:\n";
    
    for( int i=0; i<moSubList.size();++i)
    {
      oRetValue += ((clsRuleTreeElement)moSubList.get(i)).toString() + "\n";
    }
    return oRetValue;
  }
};