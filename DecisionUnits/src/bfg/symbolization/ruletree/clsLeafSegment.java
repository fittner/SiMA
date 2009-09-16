/**
 * clsLeafVision.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author zeilinger
 * 15.09.2009, 13:23:15
 */
package bfg.symbolization.ruletree;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.tools.clsColorParse;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.clsTypeCompareOperator;
import bfg.utils.enums.eCount;
import bfg.utils.enums.eOptional;
import bfg.utils.enums.eSide;
import bfg.utils.enums.eTrippleState;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorData;
import enums.eAntennaPositions;
import enums.eEntityType;
import enums.eSensorExtType;
import enums.eShapeType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.09.2009, 13:23:15
 * 
 */
public class clsLeafSegment extends clsRuleTreeLeaf {

	  public eCount mnNumber = eCount.UNDEFINED;
	  public eSensorExtType meSensorType = eSensorExtType.UNDEFINED;
	  public clsTypeCompareOperator meDistanceCompare;// = "==";
	  public eSide meLocation = eSide.UNDEFINED;
	  public eEntityType meEntityType = eEntityType.UNDEFINED;
	  public eShapeType meShapeType = eShapeType.UNDEFINED;
	  public eTrippleState moAlive = eTrippleState.UNDEFINED;
	  public java.awt.Color moColour = java.awt.Color.WHITE;
	  public eAntennaPositions moAntennaPos = eAntennaPositions.UNDEFINED; 
	  public eTrippleState meOwnTeam = eTrippleState.UNDEFINED; //
	
	public static clsRuleTreeElement create(Node poNode) {
	    clsLeafSegment oResult = new clsLeafSegment();
	   
	    if( oResult != null ){
	      NamedNodeMap oAttributes = poNode.getAttributes();
	     
	      if( oAttributes.getNamedItem("number") != null ) {
	    	  String value  = oAttributes.getNamedItem("number").getNodeValue();
	          oResult.mnNumber = eCount.valueOf(value);
	      }

	      oResult.meSensorType = eSensorExtType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "sensortype") );
	      oResult.meDistanceCompare = new clsTypeCompareOperator( clsXMLAbstractImageReader.getTagStringValue(poNode, "distancecompare") );
	      oResult.meLocation = eSide.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "location") );
	      oResult.meShapeType = eShapeType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "shapetype") );
	      oResult.moAlive = eTrippleState.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "alive") );
	      oResult.moColour = clsColorParse.parseHashHexa( clsXMLAbstractImageReader.getTagStringValue(poNode, "color") );
	      oResult.moAntennaPos = eAntennaPositions.valueOf( clsXMLAbstractImageReader.getTagStringValue(poNode, "antennapos") );
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
	    if( false )//compare( (clsVision)poPerception.getSensorExt(eSensorExtType.VISION) ) )
	    {
	      //Engine.log.println( "Match with leaf: " + this.toString() );
	      poCompareResult[0]++;
	      oResult = true;
	    }
	    return oResult;	}

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
			
//		Color oColor; 
//		eShapeType nShapeType; 
//		eEntityType nEntityType; 
//		String oOrienation; 
//		ArrayList <clsSensorRingSegmentEntries> oRingSegementEntries = ((clsVision)poData).getList();
//		
//		for (clsSensorRingSegmentEntries element : oRingSegementEntries){
//			nEntityType = element.mnEntityType; 
//			nShapeType = element.mnShapeType; 
//			oColor = element.moColor; 
//			
//		}
		
	
//	    boolean nResult = false;
//	
//	    int nIsBumped = 0; 
//	    if( ((clsBump)poData).mnBumped ) nIsBumped = 1;
//	
//	    if( meCompareOperator.compareInteger(nIsBumped, meBumped) )
//	    {
//	      nResult = true;
//	    }
//	    if( mnNegated )
//	    {
//	      nResult = !nResult;
//	    }
		return false;
	}
}
