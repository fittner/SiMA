/**
 * clsLeafEatable.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author zeilinger
 * 22.09.2009, 12:17:02
 */
package pa.bfg.symbolization.ruletree;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import pa.bfg.symbolization.brainimages.clsIdentity;
import pa.bfg.symbolization.brainimages.clsImageAbstract;
import pa.bfg.symbolization.brainimages.clsImagePerception;
import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;

import bfg.utils.enums.eCount;
import bfg.utils.enums.eOptional;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsSensorRingSegment;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 12:17:02
 *  @deprecated
 */
public class clsLeafEatableAreaSegment extends clsRuleTreeLeaf {
	  public eCount meNumber = eCount.UNDEFINED;
	  public eSensorExtType meSensorType = eSensorExtType.UNDEFINED;
	  public eEntityType meTypeOfFirstEntity = eEntityType.UNDEFINED;
		 	  	 	
	public static clsRuleTreeElement create(Node poNode) {
	    clsLeafEatableAreaSegment oResult = new clsLeafEatableAreaSegment();
	 
	    if( oResult != null ){
	      NamedNodeMap oAttributes = poNode.getAttributes();
	     
	      if( oAttributes.getNamedItem("count") != null ) {
	    	  String value  = oAttributes.getNamedItem("count").getNodeValue();
	          oResult.meNumber = eCount.valueOf(value);
	      }
	      
	      oResult.meTypeOfFirstEntity = eEntityType.valueOf(clsXMLAbstractImageReader.getTagStringValue(poNode, "entitytype")); 
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
			ArrayList <clsSensorExtern> oEatableAreaEntry = ((clsSensorRingSegment)poData).getDataObjects();
						
			for (clsSensorExtern element : oEatableAreaEntry){
				if(  ((clsEatableAreaEntry)element).getEntityType() == meTypeOfFirstEntity ){
						//&& element. .mnNumEntitiesPresent == meNumber){
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
}
