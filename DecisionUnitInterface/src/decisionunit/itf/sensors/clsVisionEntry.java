package decisionunit.itf.sensors;

import enums.eEntityType;
import bfg.tools.shapes.clsPolarcoordinate;

public class clsVisionEntry {
	public clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public int moEntityId = -1;
//	public eShapeType moShapeType = eShapeType.UNDEFINED;
	//	public double mrWidth = -1;
	//	public double mrLength = -1;
	//	public HashMap<Integer, Object> moVisibleAttributes = new HashMap<Integer, Object>();
	
	protected String moClassName;
	
	public clsVisionEntry() {
		moClassName = clsDataBase.stripClassPrefix(this.getClass().getName());
	}
	
	public String logXML() {
		String logEntry = "";
		
		logEntry += clsDataBase.addXMLTag("Polarcoordinate", moPolarcoordinate.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityId", new Integer(moEntityId).toString()); 

		return clsDataBase.addXMLTag(moClassName, logEntry);		
	}

	public String toString() {
		return moClassName+": type "+mnEntityType+" | id "+moEntityId+" | direction "+moPolarcoordinate;
	}
}
