package pa.symbolization.representationsymbol;


import java.awt.Color;
import java.util.ArrayList;

import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorExtern;

import enums.eEntityType;
import enums.eShapeType;

public class clsRadiationEntry extends clsSensorRingSegmentEntries{
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eShapeType mnShapeType = eShapeType.UNDEFINED;
	
	public boolean mnAlive = false;
	public Color moColor = null;
	
//	public eShapeType moShapeType = eShapeType.UNDEFINED;
	//	public double mrWidth = -1;
	//	public double mrLength = -1;
	//	public HashMap<Integer, Object> moVisibleAttributes = new HashMap<Integer, Object>();
	
	protected String moClassName;
	
	public clsRadiationEntry() {
		moClassName = "RadiationEntry";
	}
	
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		
		if (moColor != null) {
		  logEntry += clsDataBase.addXMLTag("Color", moColor.toString());
		}
		
		logEntry += "</Entry>";

		return logEntry;		
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": type "+mnEntityType;
		if (moColor != null) {
		  oResult += " | color "+moColor;
		}
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 14:31:56
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#logHTML()
	 */
	@Override
	public String logHTML() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:46:34
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
}
