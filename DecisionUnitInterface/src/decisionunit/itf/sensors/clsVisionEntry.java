package decisionunit.itf.sensors;


import java.awt.Color;
import java.util.Formatter;

import enums.eEntityType;
import enums.eShapeType;
import bfg.tools.shapes.clsPolarcoordinate;

public class clsVisionEntry {
	public clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eShapeType mnShapeType = eShapeType.UNDEFINED;
	public String moEntityId = "";
	public boolean mnAlive = false;
	public Color moColor = null;
	
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
		
		if (moColor != null) {
		  logEntry += clsDataBase.addXMLTag("Color", moColor.toString());
		}
		
		logEntry += "</Entry>";

		return logEntry;		
	}
	
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
		oResult += " - ID="+moEntityId+" : ";

		
		moColor.toString();
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": type "+mnEntityType+" | id "+moEntityId+" | direction "+moPolarcoordinate;
		if (moColor != null) {
		  oResult += " | color "+moColor;
		}
		return oResult;
	}
}
