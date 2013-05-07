/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package du.itf.sensors;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Formatter;

import du.enums.eAntennaPositions;

import bfg.utils.enums.ePercievedActionType;
import bfg.utils.enums.eSide;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsVisionEntry extends clsSensorRingSegmentEntry {
	protected boolean mnAlive = false;
	protected Color moColor = new Color(0,0,0);
	protected eSide moObjectPosition = eSide.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionLeft = eAntennaPositions.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionRight = eAntennaPositions.UNDEFINED;
	protected double moExactDebugX;
	protected double moExactDebugY;
	protected double moExactDebugAngle;
	protected double moDebugSensorArousal;
	protected double moObjectBodyIntegrity;
	
	protected ArrayList<ePercievedActionType> moActions = new ArrayList<ePercievedActionType>();

	public void setActions(ArrayList<ePercievedActionType> poAction){
		moActions = poAction;
	}
	
	public ArrayList<ePercievedActionType> getActions(){
		return moActions;
	}
	
	public double getObjectBodyIntegrity() {
		return moObjectBodyIntegrity;
	}

	public void setObjectBodyIntegrity(double poObjectBodyIntegrity) {
		this.moObjectBodyIntegrity = poObjectBodyIntegrity;
	}

	public double getExactDebugX() {
		return moExactDebugX;
	}

	public double getExactDebugY() {
		return moExactDebugY;
	}

	public double getExactDebugAngle() {
		return moExactDebugAngle;
	}
	
	public double getDebugSensorArousal() {
		return moDebugSensorArousal;
	}
	
	public void setDebugSensorArousal(double poDebugSensorArousal) {
		moDebugSensorArousal = poDebugSensorArousal;
	}
	
	public void setExactDebugPosition(double poExactX, double poExactY, double poExactAngle) {
		moExactDebugX = poExactX;
		moExactDebugY = poExactY;
		moExactDebugAngle = poExactAngle;
	}
	
	public boolean getAlive() {
		return mnAlive;
	}
	public void setAlive(boolean pnAlive) {
		mnAlive=pnAlive;
	}
	
	public Color getColor() {
		return moColor;
	}
	public void setColor(Color poColor) {
		moColor = poColor;
	}
	
	public eSide getObjectPosition() {
		return moObjectPosition;
	}
	public void setObjectPosition(eSide poObjectPosition) {
		moObjectPosition = poObjectPosition;
	}
	
	public eAntennaPositions getAntennaPositionLeft() {
		return moAntennaPositionLeft;
	}
	public void setAntennaPositionLeft(eAntennaPositions poAntennaPositionLeft) {
		moAntennaPositionLeft = poAntennaPositionLeft;
	}
	
	public eAntennaPositions getAntennaPositionRight() {
		return moAntennaPositionRight;
	}
	public void setAntennaPositionRight(eAntennaPositions poAntennaPositionRight) {
		moAntennaPositionRight = poAntennaPositionRight;
	}	
	
	public clsVisionEntry() {

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
		oResult += this.getClass().getName()+": type "+mnEntityType+" | id "+moEntityId+" | direction "+moPolarcoordinate+ "| alive " + mnAlive;
		if (moColor != null) {
		  oResult += " | color "+moColor;
		}
		oResult += "Actions: ";
		for (ePercievedActionType oAction : moActions){
			oResult	+= oAction.toString() + "|";
		}
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:48:03
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
}
