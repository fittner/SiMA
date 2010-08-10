/**
 * clsLeafVision.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author zeilinger
 * 15.09.2009, 13:23:15
 */
package pa.bfg.symbolization.ruletree;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import pa.bfg.symbolization.brainimages.clsIdentity;
import pa.bfg.symbolization.brainimages.clsImageAbstract;
import pa.bfg.symbolization.brainimages.clsImagePerception;
import pa.bfg.tools.clsColorParse;
import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;

import bfg.utils.enums.eCount;
import bfg.utils.enums.eOptional;
import bfg.utils.enums.eSide;
import bfg.utils.enums.eTrippleState;
import du.enums.eAntennaPositions;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import du.enums.eShapeType;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.09.2009, 13:23:15
 *  @deprecated
 */
public class clsLeafVisionSegment extends clsRuleTreeLeaf {

	  public eCount meNumber = eCount.UNDEFINED;
	  public eSensorExtType meSensorType = eSensorExtType.UNDEFINED;
	  public eSide meLocation = eSide.UNDEFINED;
	  public eEntityType meEntityType = eEntityType.UNDEFINED;
	  public eShapeType meShapeType = eShapeType.UNDEFINED;
	  public eTrippleState moAlive = eTrippleState.UNDEFINED;
	  public java.awt.Color moColor = java.awt.Color.WHITE;
	  public eAntennaPositions meAntennaPos = eAntennaPositions.UNDEFINED;
	  
	  public String getMeshContent() {
		  return "meEntityType";
	  }
	  public boolean createMesh() {
		  return false;
	  }
	  
	  /*FIXME HZ: Actually there is no team ID defined for agents*/
	  public eTrippleState meOwnTeam = eTrippleState.UNDEFINED; 
	
	public static clsRuleTreeElement create(Node poNode) {
	    clsLeafVisionSegment oResult = new clsLeafVisionSegment();
	 
	    if( oResult != null ){
	      NamedNodeMap oAttributes = poNode.getAttributes();
	     
	      if( oAttributes.getNamedItem("count") != null ) {
	    	  String value  = oAttributes.getNamedItem("count").getNodeValue();
	          oResult.meNumber = eCount.valueOf(value);
	      }
	      
	      oResult.meEntityType = eEntityType.valueOf(clsXMLAbstractImageReader.getTagStringValue(poNode, "entitytype")); 
	      oResult.meSensorType = eSensorExtType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "sensortype") );
	      oResult.meLocation = eSide.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "location") );
	      oResult.meShapeType = eShapeType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "shapetype") );
	      oResult.moAlive = eTrippleState.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "alive") );
	      oResult.moColor = clsColorParse.parseHashHexa( clsXMLAbstractImageReader.getTagStringValue(poNode, "color") );
	      oResult.meAntennaPos = eAntennaPositions.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "antennapos") );
	      oResult.meOwnTeam = eTrippleState.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "ownteam") );
	    }
	    return oResult;
	  }
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 15.09.2009, 13:23:15
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
	    	if( compare((clsVision)poPerception.getSensorExt(meSensorType))){
	          poCompareResult[0]++;
		      oResult = true;
	    	}
	    }
	    return oResult;	
}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 15.09.2009, 13:23:15
	 * 
	 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#weight(bfg.symbolization.brainimages.clsImagePerception, bfg.symbolization.brainimages.clsImageAbstract, bfg.symbolization.ruletree.clsRuleCompareResult)
	 */
	@Override
	public void weight(clsImagePerception poImage,
			clsImageAbstract poAbstractImage, clsRuleCompareResult compareResult) {
		// TODO (zeilinger) - Auto-generated method stub
	}
		
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 15.09.2009, 13:35:53
	 * 
	 * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
	 */
	@Override
	public boolean compare(clsDataBase poData) {
			
	  	boolean nResult = false; 
		
		if(poData != null){
			ArrayList<clsSensorExtern>  oVisionEntries = ((clsVision)poData).getDataObjects();
		
			/*FIXME HZ Antenna positions have not been implemented yet, as the value is set to undefined
			 * 		The same is for the team ID*/			
			for (clsSensorExtern element : oVisionEntries){
				clsVisionEntry oElement = (clsVisionEntry)element; 
				if( oElement.getEntityType() == meEntityType
					&& oElement.getObjectPosition() == meLocation 
					&& oElement.getShapeType() == meShapeType
				    && oElement.getColor().equals(moColor)
				    && meCompareOperator.compare(oElement.getAlive(), moAlive)
				    && oElement.getNumEntitiesPresent() == meNumber){
					
					nResult = true;
					break; 
				}
			}
		}
		
		return nResult; 
	}
}
