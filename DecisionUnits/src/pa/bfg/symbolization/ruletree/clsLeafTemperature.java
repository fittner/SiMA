/**
 * clsLeafTemperature.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author langr
 * 22.09.2009, 16:13:20
 */
package pa.bfg.symbolization.ruletree;

import org.w3c.dom.Node;

import pa.bfg.symbolization.brainimages.clsIdentity;
import pa.bfg.symbolization.brainimages.clsImageAbstract;
import pa.bfg.symbolization.brainimages.clsImagePerception;
import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;

import bfg.utils.enums.eOptional;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 22.09.2009, 16:13:20
 *  @deprecated
 */
public class clsLeafTemperature  extends clsRuleTreeLeaf
{
	  public double mrTension = 0;

	  //---------------------------------------------------------------------------
	  public static clsRuleTreeElement create(Node poNode)
	  //---------------------------------------------------------------------------
	  {
		  clsLeafTemperature oResult = new clsLeafTemperature();
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
	    if( compare( (du.itf.sensors.clsTemperatureSystem)poPerception.getSensorInt(eSensorIntType.TEMPERATURE) ) )
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
	    String oRetValue = "clsLeafTemperatir: ";
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
		    double oTension = ((du.itf.sensors.clsStomachTension)poData).getTension();
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