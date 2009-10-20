/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package pa.symbolization.representationsymbol;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsSymbolEatableAreaEntry extends decisionunit.itf.sensors.clsEatableAreaEntry implements itfGetMeshAttributeName, itfGetSymInterface, itfSymbolEatableAreaEntry {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.10.2009, 19:51:02
	 *
	 * @param pnEntityType
	 */
	public clsSymbolEatableAreaEntry(decisionunit.itf.sensors.clsEatableAreaEntry poSensor) {
		super(poSensor.getEntityType());
		
		moSensorType = poSensor.getSensorType();
		
		moPolarcoordinate = poSensor.getPolarcoordinate();
		mnEntityType = poSensor.getEntityType();
		mnShapeType = poSensor.getShapeType();
		moEntityId = poSensor.getEntityId();
		mnNumEntitiesPresent = poSensor.getNumEntitiesPresent(); 
		
		mnIsAlive = poSensor.getIsAlive();
		mnIsConsumeable = poSensor.getIsConsumeable();
		moClassName = poSensor.getClassName();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:51:09
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
	 * 19.10.2009, 19:51:09
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymEatableAreaEntry";
	}

	
}
