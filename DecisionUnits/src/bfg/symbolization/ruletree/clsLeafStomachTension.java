/**
 * clsStomachTension.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author langr
 * 22.09.2009, 15:02:59
 */
package bfg.symbolization.ruletree;

import org.w3c.dom.Node;

import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.eOptional;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorData;
import enums.eSensorIntType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 22.09.2009, 15:02:59
 * 
 */
public class clsLeafStomachTension extends clsRuleTreeLeaf
{
	  public double mrTension = 0;

	  //---------------------------------------------------------------------------
	  public static clsRuleTreeElement create(Node poNode)
	  //---------------------------------------------------------------------------
	  {
		  clsLeafStomachTension oResult = new clsLeafStomachTension();
	    if( oResult != null )
	    {
	      oResult.mrTension = Double.parseDouble(clsXMLAbstractImageReader.getTagStringValue(poNode, "tension"));
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
	    if( compare( (decisionunit.itf.sensors.clsStomachTension)poPerception.getSensorInt(eSensorIntType.STOMACHTENSION) ) )
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
	    String oRetValue = "clsLeafStomachTension: ";
	    oRetValue += super.toString();
	    oRetValue += " tension="+mrTension;
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
		    double oTension = ((decisionunit.itf.sensors.clsStomachTension)poData).mrTension;
		    if( poData != null && meCompareOperator.compareDouble(oTension, mrTension) )
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