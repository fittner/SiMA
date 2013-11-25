/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author MW
 * 27.02.2013, 11:23:41
 */
package du.itf.sensors;

import java.awt.Color;
import java.util.ArrayList;

import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;

import du.enums.eAntennaPositions;
import du.enums.eEntityType;
import du.enums.eSaliency;
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
	public static Object oEntry;

	protected clsAbstractSpeech moAbstractSpeech = null;
	
	protected clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
	protected eEntityType mnEntityType = eEntityType.UNDEFINED;
	protected eShapeType mnShapeType = eShapeType.UNDEFINED;
	protected String moEntityId = "";	
	protected double moExactDebugX;
	protected double moExactDebugY;
	protected double moDebugSensorArousal;
	protected boolean mnAlive = false;
	protected Color moColor = new Color(0,0,0);
	protected eSaliency moBrightness = eSaliency.UNDEFINED;
	protected eSide moObjectPosition = eSide.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionLeft = eAntennaPositions.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionRight = eAntennaPositions.UNDEFINED;
	protected double moExactDebugAngle;
	protected double moObjectBodyIntegrity;

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
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 17.08.2013 22:47:30
	 *
	 * @return
	 */
	public eCount getNumEntitiesPresent() {
		// TODO (hinterleitner) - Auto-generated method stub
		return null;
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 21.08.2013 21:29:20
	 *
	 * @param b
	 */
	public void setAlive(boolean b) {
		// TODO (hinterleitner) - Auto-generated method stub
		return;
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 21.08.2013 21:29:51
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		// TODO (hinterleitner) - Auto-generated method stub
		return;
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 09:41:23
	 *
	 * @param setMeNumber
	 */
	public void setNumEntitiesPresent(eCount setMeNumber) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 10:13:57
	 *
	 * @return
	 */
	public double getExactDebugAngle() {
		// TODO (hinterleitner) - Auto-generated method stub
		return 0;
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 10:14:03
	 *
	 * @return
	 */
	public eSaliency getBrightness() {
		// TODO (hinterleitner) - Auto-generated method stub
		return null;
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 19:15:16
	 *
	 * @param entityBrightness
	 */
	public void setBrightness(eSaliency entityBrightness) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 19:15:25
	 *
	 * @param bodyIntegrity
	 */
	public void setObjectBodyIntegrity(double bodyIntegrity) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 19:15:34
	 *
	 * @param undefined
	 */
	public void setAntennaPositionLeft(eAntennaPositions undefined) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 25.08.2013 19:15:57
	 *
	 * @param undefined
	 */
	public void setAntennaPositionRight(eAntennaPositions undefined) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
}
