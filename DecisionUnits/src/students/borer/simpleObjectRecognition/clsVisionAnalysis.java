/**
 * @author borer
 */
package students.borer.simpleObjectRecognition;


import students.borer.LocalizationOrientation.clsPerceivedObj;


import decisionunit.itf.sensors.clsSensorExtern;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import enums.eEntityType;

/**
 * performs a simple object recognition
 * @author  borer
 */
public class clsVisionAnalysis {
	
	public clsObjectsSemanticMemory moSemanticMemory;
	private double similarObjectsTolerance=0.01;
	
	public clsVisionAnalysis(){
		moSemanticMemory = new clsObjectsSemanticMemory();
	}
	
	/**
	 *transfers the vision data from the vision interface into a clsPerceivedObject container. 
	 *Performs a object recognition and calculates the location relative to the agent location
	 *
	 * @param oVision: the information from the agents Vision system
	 *
	 * @return a clsPerceivedObject container containing the environmental data.
	 */
	public clsPerceivedObj calc_PerceivedObj(clsVision oVision){
		clsPerceivedObj mePerceiveObj = new clsPerceivedObj(oVision.getDataObjects().size());
		
		for( clsSensorExtern oVisionObj : oVision.getDataObjects() ) {
			//no walls or bubbles at the moment
			if (((clsVisionEntry)oVisionObj).getEntityType() == eEntityType.BUBBLE)
				continue;
			
			//remember object in semantic memory
			clsObject newObject = new clsObject(oVisionObj);
			newObject.setid(moSemanticMemory.rememberObject(newObject, similarObjectsTolerance));
			mePerceiveObj.addObject(newObject, ((clsVisionEntry)oVisionObj).getPolarcoordinate().mrLength, ((clsVisionEntry)oVisionObj).getPolarcoordinate().moAzimuth.getDegree());
		}
		
		//mePerceiveObj.String();
		return mePerceiveObj;
	}
}
