package decisionunit.itf.sensors;


import enums.eEntityType;
import enums.eShapeType;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eSide;

public abstract class clsSensorRingSegmentEntries {
	public clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eShapeType mnShapeType = eShapeType.UNDEFINED;
	public String moEntityId = "";
	public boolean mnAlive = false;
	public eSide moObjectPosition = eSide.UNDEFINED; 
		
	protected String moClassName;
	
	public clsSensorRingSegmentEntries() {
		moClassName = "SegmentEntry";
	}
	
	public abstract String logXML(int pnId); 
	
	public abstract String logHTML(); 

	@Override
	public abstract String toString(); 
}
