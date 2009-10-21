/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolManipulateAreaEntry extends decisionunit.itf.sensors.clsManipulateAreaEntry 
											implements itfGetSymbolName, itfSymbolManipulateAreaEntry, itfGetDataAccessMethods  {

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
	public String getSymbolName() {
		return mnEntityType.name();
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolManipulateAreaEntry.class.getMethods();
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
