/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author MW
 * 27.02.2013, 11:23:41
 */
package complexbody.io.sensors.datatypes;

import java.util.ArrayList;

import physical2d.physicalObject.datatypes.eCount;
import physical2d.physicalObject.datatypes.eSide;

import complexbody.io.sensors.datatypes.enums.eAntennaPositions;
import complexbody.io.sensors.datatypes.enums.eEntityType;
import complexbody.io.sensors.datatypes.enums.eSaliency;
import complexbody.io.sensors.datatypes.enums.eShapeType;
import complexbody.io.speech.clsAbstractSpeech;

import datatypes.clsPolarcoordinate;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 27.02.2013, 11:23:41
 * 
 */
public class clsAcousticEntry extends clsVisionEntry {
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
	protected eSaliency moBrightness = eSaliency.UNDEFINED;
	protected eSide moObjectPosition = eSide.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionLeft = eAntennaPositions.UNDEFINED; 
	protected eAntennaPositions moAntennaPositionRight = eAntennaPositions.UNDEFINED;
	protected double moExactDebugAngle;
	protected double moObjectBodyIntegrity;
	//clsAcousticEntry  
	protected clsVisionEntryAction moAction;
	
	/**
	 * @since 19.11.2013 13:38:03
	 * 
	 * @return the moAction
	 */
	@Override
	public clsVisionEntryAction getAction() {
		return moAction;
	}

	@Override
	public clsPolarcoordinate getPolarcoordinate() {
		return moPolarcoordinate;
	}
	@Override
	public void setPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
		moPolarcoordinate = poPolarcoordinate;
	}
	
	@Override
	public eSide getObjectPosition() {
		return moObjectPosition;
	}
	@Override
	public void setObjectPosition(eSide poObjectPosition) {
		moObjectPosition = poObjectPosition;
	}
	@Override
	public eEntityType getEntityType() {
		return mnEntityType;
	}
	@Override
	public void setEntityType(eEntityType pnEntityType) {
		mnEntityType = pnEntityType;
	}
	
	@Override
	public eShapeType getShapeType() {
		return mnShapeType;
	}
	@Override
	public void setShapeType(eShapeType pnShapeType) {
		mnShapeType = pnShapeType;
	}
	
	@Override
	public String getEntityId() {
		return moEntityId;
	}
	@Override
	public void setEntityId(String pnEntityId) {
		moEntityId = pnEntityId;
	}
	
	@Override
	public double getExactDebugX() {
		return moExactDebugX;
	}

	@Override
	public double getExactDebugY() {
		return moExactDebugY;
	}
	
	@Override
	public double getDebugSensorArousal() {
		return moDebugSensorArousal;
	}
	
	@Override
	public void setDebugSensorArousal(double poDebugSensorArousal) {
		moDebugSensorArousal = poDebugSensorArousal;
	}
	
	@Override
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
	
	@Override
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
	@Override
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
	@Override
	public void setAlive(boolean b) {
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public void setAntennaPositionRight(eAntennaPositions undefined) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
}
