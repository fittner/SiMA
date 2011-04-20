/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package pa._v19.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import du.enums.eDistance;



/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolManipulateAreaEntry extends du.itf.sensors.clsManipulateAreaEntry 
											implements itfIsContainer, itfGetSymbolName, itfSymbolManipulateAreaEntry, itfGetDataAccessMethods  {

	public clsSymbolManipulateAreaEntry(du.itf.sensors.clsManipulateAreaEntry poSensor) {
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
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 15:09:46
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymbolName#getSymbolType()
	 */
	@Override
	public String getSymbolType() {
		return "ENTITY";
	}
	@Override
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

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 21.10.2009, 21:35:01
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
	 * 30.10.2009, 12:31:49
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbolManipulateAreaEntry#getDistance()
	 */
	@Override
	public eDistance getDistance() {
		return eDistance.MANIPULATEABLE;
	}
}
