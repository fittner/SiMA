package decisionunit.itf.sensors;


import java.awt.Color;
import java.util.ArrayList;

public class clsRadiationEntry extends clsSensorRingSegmentEntries{
	protected boolean mnAlive = false;
	protected Color moColor = null;
	protected String moClassName = "";
	
	public boolean getAlive() {
		return mnAlive;
	}
	public void setAlive(boolean pnAlive) {
		mnAlive = pnAlive;
	}
	
	public Color getColor() {
		return moColor;
	}
	public void setColor(Color poColor) {
		moColor = poColor;
	}
	
	@Override
	public String getClassName() {
		return moClassName;
	}
	public void setClassName(String poClassName) {
		moClassName = poClassName;
	}
	
	public clsRadiationEntry() {
		moClassName = "RadiationEntry";
	}
	
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("Polarcoordinate", moPolarcoordinate.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityType", mnEntityType.toString()); 
		logEntry += clsDataBase.addXMLTag("EntityId", moEntityId);
		
		if (moColor != null) {
		  logEntry += clsDataBase.addXMLTag("Color", moColor.toString());
		}
		
		logEntry += "</Entry>";

		return logEntry;		
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
	 * 01.10.2009, 14:47:42
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
}
