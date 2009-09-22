/**
 * clsLeafEatable.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author zeilinger
 * 22.09.2009, 12:17:02
 */
package bfg.symbolization.ruletree;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.eCount;
import bfg.utils.enums.eOptional;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEatableEntries;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsSensorRingSegmentEntries;
import enums.eEntityType;
import enums.eSensorExtType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 12:17:02
 * 
 */
public class clsLeafEatable extends clsRuleTreeLeaf {
	  public eCount meNumber = eCount.UNDEFINED;
	  public eSensorExtType meSensorType = eSensorExtType.UNDEFINED;
	  public eEntityType meEntityType = eEntityType.UNDEFINED;
		 	  	 	
	public static clsRuleTreeElement create(Node poNode) {
	    clsLeafEatable oResult = new clsLeafEatable();
	 
	    if( oResult != null ){
	      NamedNodeMap oAttributes = poNode.getAttributes();
	     
	      if( oAttributes.getNamedItem("count") != null ) {
	    	  String value  = oAttributes.getNamedItem("count").getNodeValue();
	          oResult.meNumber = eCount.valueOf(value);
	      }
	      
	      oResult.meEntityType = eEntityType.valueOf(clsXMLAbstractImageReader.getTagStringValue(poNode, "entitytype")); 
	      oResult.meSensorType = eSensorExtType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "sensortype") );
	    }
	    return oResult;
	  }
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 12:17:26
	 * 
	 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#evaluateTree(decisionunit.itf.sensors.clsSensorData, bfg.symbolization.brainimages.clsIdentity, int[])
	 */
	@Override
	public boolean evaluateTree(clsSensorData poPerception,
			clsIdentity poBrainsIdentity, int[] poCompareResult) {
		
		if( meOptionalType != eOptional.OPTIONAL )
	    {
	      // for optional leafs we get a counter in the match list, but not in the list of all entries -->
	      // compareResultValue increases if optional leafs match (can lead to more than 100% )
	      poCompareResult[1]++;
	    }
	    boolean oResult = false;
	    //TODO (Zeilinger) - implement the complex compare operator and listen some cool Hip-Hop (the music - not the Bewegung) or Rap...
	    {
	    	if( compare((clsEatableArea)poPerception.getSensorExt(meSensorType))){
	          poCompareResult[0]++;
		      oResult = true;
	    	}
	    }
		return oResult;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 12:17:26
	 * 
	 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
	 */
	@Override
	public boolean compare(clsDataBase poData) {
		boolean nResult = false; 
		
		if(poData != null){
			ArrayList <clsSensorRingSegmentEntries> oRingSegmentEntries = ((clsEatableArea)poData).getList();
			eCount oNumber = setMeNumber(oRingSegmentEntries); 
			
			for (clsSensorRingSegmentEntries element : oRingSegmentEntries){
				clsEatableEntries oElement = (clsEatableEntries)element; 
				if( oElement.mnEntityType == meEntityType
					&& oNumber == meNumber){
					
					nResult = true;
					break; 
				}
			}
		}
		
		return nResult; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.09.2009, 12:17:26
	 * 
	 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#weight(bfg.symbolization.brainimages.clsImagePerception, bfg.symbolization.brainimages.clsImageAbstract, bfg.symbolization.ruletree.clsRuleCompareResult)
	 */
	@Override
	public void weight(clsImagePerception poImage,
			clsImageAbstract poAbstractImage, clsRuleCompareResult compareResult) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.09.2009, 16:31:19
	 *
	 */
	private eCount setMeNumber(ArrayList <clsSensorRingSegmentEntries> poRingSegmentEntries) {
		if(poRingSegmentEntries == null){
			new java.lang.ArrayIndexOutOfBoundsException(" The ArrayList oRingSegmentEntries does not" +
													     " include any entries"); 
		}
		if(poRingSegmentEntries.size()==0){
			return eCount.NONE; 
		}
		else if (poRingSegmentEntries.size()==1){
			return eCount.ONE; 
		}
		else if (poRingSegmentEntries.size()==2){
			return eCount.TWO; 
		}
		else if (poRingSegmentEntries.size()> 2){
			return eCount.MANY; 
		}
		else{
			new java.lang.NullPointerException (" element number is undefined \n");
			return null; 
		}
	
	}

}
