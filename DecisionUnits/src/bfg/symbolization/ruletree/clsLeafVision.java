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
import bfg.utils.enumsOld.enumTypeCompareOperator;
import bfg.utils.enumsOld.enumTypeCount;
//import bfg.utils.enumsOld.enumTypeSide;
import bfg.utils.enumsOld.enumTypeTrippleState;
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
public class clsLeafVision extends clsRuleTreeLeaf {

	  public int mnNumber = enumTypeCount.TCOUNT_NONE;
	  public eSensorExtType meSensorType = eSensorExtType.UNDEFINED;
	  public enumTypeCompareOperator meDistanceCompare;// = "==";
//	  public enumTypeSide meLocation = enumTypeSide.TSIDE_UNDEFINED;
	  public eEntityType meEntityType = eEntityType.UNDEFINED;
	  public eShapeType meShapeType = eShapeType.UNDEFINED;
	  public int moAlive = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
	  public java.awt.Color moColour = java.awt.Color.WHITE;
	  public eAntennaPositions moAntennaPos = eAntennaPositions.UNDEFINED; 
	  public int meOwnTeam = enumTypeTrippleState.TTRIPPLE_UNDEFINED; //
	

//	String oOrienation; 
	
	public static clsRuleTreeElement create(Node poNode) {
	    clsLeafVision oResult = new clsLeafVision();
	   
	    if( oResult != null ){
	      NamedNodeMap oAttributes = poNode.getAttributes();
	     
	      if( oAttributes.getNamedItem("value") != null )
	      {
	    

	      }
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
		// TODO (zeilinger) - Auto-generated method stub
		return false;
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
