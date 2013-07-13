/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author MW
 * 27.02.2013, 11:23:41
 */
package du.itf.sensors;

import java.util.ArrayList;

import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eSide;

import du.enums.eEntityType;
import du.enums.eShapeType;
import du.itf.tools.clsAbstractSpeech;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 27.02.2013, 11:23:41
 * 
 */
public class clsAcousticEntry extends clsSensorExtern {
	protected clsAbstractSpeech moAbstractSpeech = null;
	
	protected clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	protected eEntityType mnEntityType = eEntityType.UNDEFINED;
	protected eShapeType mnShapeType = eShapeType.UNDEFINED;
	protected String moEntityId = "";
	protected eSide moObjectPosition = eSide.UNDEFINED; 
	
	protected double moExactDebugX;
	protected double moExactDebugY;
	protected double moDebugSensorArousal;
	
	public clsPolarcoordinate getPolarcoordinate() {
		return moPolarcoordinate;
	}
	public void setPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
		moPolarcoordinate = poPolarcoordinate;
	}
	
	public eSide getObjectPosition() {
		return moObjectPosition;
	}
	public void setObjectPosition(eSide poObjectPosition) {
		moObjectPosition = poObjectPosition;
	}
	public eEntityType getEntityType() {
		return mnEntityType;
	}
	public void setEntityType(eEntityType pnEntityType) {
		mnEntityType = pnEntityType;
	}
	
	public eShapeType getShapeType() {
		return mnShapeType;
	}
	public void setShapeType(eShapeType pnShapeType) {
		mnShapeType = pnShapeType;
	}
	
	public String getEntityId() {
		return moEntityId;
	}
	public void setEntityId(String pnEntityId) {
		moEntityId = pnEntityId;
	}
	
	public double getExactDebugX() {
		return moExactDebugX;
	}

	public double getExactDebugY() {
		return moExactDebugY;
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
	}
	
	public clsAcousticEntry() {
	}
	
	public clsAcousticEntry(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech = oAbstractSpeech;
	}
	
	public clsAbstractSpeech getEntry() {
		return moAbstractSpeech;
	}
	
	public void setEntry(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech = oAbstractSpeech;
	}
	
	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";	
		logEntry += clsDataBase.addXMLTag("EntityId", "AbstractSpeech");
		logEntry += "</Entry>";

		return logEntry;		
	}
	
	@Override
	public String logHTML() {
		String oResult = "";
		oResult += "AbstractSpeech";
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += this.getClass().getName()+": type AbstractSpeech";
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.02.2013 12:07:00
	 * 
	 * @see du.itf.sensors.clsSensorExtern#getDataObjects()
	 */
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		// TODO (Owner) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.02.2013 12:07:00
	 * 
	 * @see du.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		// TODO (Owner) - Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.02.2013 12:07:00
	 * 
	 * @see du.itf.sensors.clsDataBase#logXML()
	 */
	@Override
	public String logXML() {
		// TODO (Owner) - Auto-generated method stub
		return null;
	}
}
