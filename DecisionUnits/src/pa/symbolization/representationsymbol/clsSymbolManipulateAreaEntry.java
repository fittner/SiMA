/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package pa.symbolization.representationsymbol;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolManipulateAreaEntry extends decisionunit.itf.sensors.clsManipulateAreaEntry 
											implements itfGetMeshAttributeName, itfSymbolManipulateAreaEntry, itfGetSymInterface  {

	public clsSymbolManipulateAreaEntry(decisionunit.itf.sensors.clsManipulateAreaEntry poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		moPolarcoordinate = poSensor.getPolarcoordinate();
		mnEntityType = poSensor.getEntityType();
		mnShapeType = poSensor.getShapeType();
		moEntityId = poSensor.getEntityId();
		mnNumEntitiesPresent = poSensor.getNumEntitiesPresent(); 
		
		mnAlive = poSensor.getAlive();
		moColor = poSensor.getColor();
		moObjectPosition = poSensor.getObjectPosition(); 
		moAntennaPositionLeft = poSensor.getAntennaPositionLeft(); 
		moAntennaPositionRight = poSensor.getAntennaPositionRight();			
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 20:16:15
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		return "mnEntityType";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 20:16:15
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymbolManipulateAreaEntry";
	}


}
