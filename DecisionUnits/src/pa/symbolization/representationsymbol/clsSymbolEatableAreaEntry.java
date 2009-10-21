/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsSymbolEatableAreaEntry extends decisionunit.itf.sensors.clsEatableAreaEntry implements itfGetSymbolName, itfGetDataAccessMethods, itfSymbolEatableAreaEntry {

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
	public String getSymbolName() {
		return mnEntityType.name();
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolEatableAreaEntry.class.getMethods();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 12:34:45
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
	 */
	@Override
	public ArrayList<itfSymbol> getSymbolObjects() {
		ArrayList<itfSymbol> oRetVal = new ArrayList<itfSymbol>();
		oRetVal.add(this);
		return oRetVal;
	}
}
