/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package decisionunit.itf.sensors;

import java.awt.Color;
import java.util.Formatter;

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
		moClassName = "SegmentEntry";
	}
	
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("Polarcoordinate", moPolarcoordinate.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityId", new Integer(moEntityId).toString());
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

		
		//mnEntityType
		oResult += "x:"+oDoubleFormatter.format("%.2f",moPolarcoordinate.getVector().mrX);
		//TODO (langr): use the formatter in a better way!!!
		oDoubleFormatter = new Formatter();
		oResult += " y:"+oDoubleFormatter.format("%.2f",moPolarcoordinate.getVector().mrY);
		oDoubleFormatter = new Formatter();
		oResult += " deg:"+oDoubleFormatter.format("%.5f",moPolarcoordinate.moAzimuth.getDegree())+" ";
		oDoubleFormatter = new Formatter();
		oResult += mnEntityType.toString();
		oResult += mnAlive; 
		oResult += " - ID="+moEntityId+" : ";

		if (moColor != null) {		
			moColor.toString();
		}
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": type "+mnEntityType+" | id "+moEntityId+" | direction "+moPolarcoordinate+ "| alive " + mnAlive;
		if (moColor != null) {
		  oResult += " | color "+moColor;
		}
		return oResult;
	}
}
