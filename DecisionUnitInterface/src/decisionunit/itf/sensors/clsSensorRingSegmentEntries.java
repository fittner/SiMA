package decisionunit.itf.sensors;


import enums.eEntityType;
import enums.eShapeType;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eCount;

public abstract class clsSensorRingSegmentEntries {
	public clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eShapeType mnShapeType = eShapeType.UNDEFINED;
	public String moEntityId = "";
	public eCount mnNumEntitiesPresent = eCount.UNDEFINED; 
		
	public clsSensorRingSegmentEntries() {
	}
	
	public abstract String logXML(int pnId); 
	
	public abstract String logHTML(); 

	@Override
	public abstract String toString(); 
}
