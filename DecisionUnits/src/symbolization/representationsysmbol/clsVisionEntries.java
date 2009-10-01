/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package symbolization.representationsysmbol;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Formatter;

import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorExtern;

import bfg.utils.enums.eSide;

import enums.eAntennaPositions;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsVisionEntries extends clsSensorRingSegmentEntries {
	
	public boolean mnAlive = false;
	public Color moColor = null;
	public eSide moObjectPosition = eSide.UNDEFINED; 
	public eAntennaPositions moAntennaPosition = eAntennaPositions.UNDEFINED; 
			

	public clsVisionEntries() {

	}
	
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		logEntry += clsDataBase.addXMLTag("Alive", new Boolean(mnAlive).toString());
		
		if (moColor != null) {
		  logEntry += clsDataBase.addXMLTag("Color", moColor.toString());
		}
		
		logEntry += "</Entry>";

		return logEntry;		
	}
	
	@Override
	public String logHTML() {
		String oResult = "";
		Formatter oDoubleFormatter = new Formatter();

		
		oResult += mnEntityType.toString();
		oResult += mnAlive; 

		if (moColor != null) {		
			moColor.toString();
		}
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += this.getClass().getName()+": type "+mnEntityType+" | alive " + mnAlive;
		if (moColor != null) {
		  oResult += " | color "+moColor;
		}
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:46:54
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
}
