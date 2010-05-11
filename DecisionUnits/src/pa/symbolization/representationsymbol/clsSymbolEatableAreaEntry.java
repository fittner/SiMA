/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import du.enums.eDistance;



/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsSymbolEatableAreaEntry extends du.itf.sensors.clsEatableAreaEntry implements itfIsContainer, itfGetSymbolName, itfGetDataAccessMethods, itfSymbolEatableAreaEntry {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.10.2009, 19:51:02
	 *
	 * @param pnEntityType
	 */
	public clsSymbolEatableAreaEntry(du.itf.sensors.clsEatableAreaEntry poSensor) {
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 15:09:46
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymbolName#getSymbolType()
	 */
	@Override
	public String getSymbolType() {
		return "Entity";
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 21.10.2009, 21:34:32
	 * 
	 * @see pa.symbolization.representationsymbol.itfIsContainer#getSymbolMeshContent()
	 */
	@Override
	public Object getSymbolMeshContent() {
		return mnEntityType;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 30.10.2009, 12:30:41
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbolEatableAreaEntry#getDistance()
	 */
	@Override
	public eDistance getDistance() {
		return eDistance.EATABLE;
	}
}
