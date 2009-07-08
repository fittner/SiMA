package decisionunit.itf.sensors;

import enums.eEntityType;
import bfg.tools.shapes.clsPolarcoordinate;

public class clsVisionEntry {
	public clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public int moEntityId = -1;
	public boolean mnAlive = false;
//	public eShapeType moShapeType = eShapeType.UNDEFINED;
	//	public double mrWidth = -1;
	//	public double mrLength = -1;
	//	public HashMap<Integer, Object> moVisibleAttributes = new HashMap<Integer, Object>();
	
	protected String moClassName;
	
	public clsVisionEntry() {
		moClassName = "VisionEntry";
	}
	
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("Polarcoordinate", moPolarcoordinate.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityId", new Integer(moEntityId).toString());
		
		logEntry += "</Entry>";

		return logEntry;		
	}

	public String toString() {
		return moClassName+": type "+mnEntityType+" | id "+moEntityId+" | direction "+moPolarcoordinate;
	}
}
