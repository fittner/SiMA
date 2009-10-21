package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;



public class clsSymbolPositionChange extends decisionunit.itf.sensors.clsPositionChange  implements itfGetSymbolName, itfSymbolPositionChange, itfGetDataAccessMethods {
	public clsSymbolPositionChange(decisionunit.itf.sensors.clsPositionChange poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		a = poSensor.getA();
		x = poSensor.getX();
		y = poSensor.getY();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:59:33
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
	 */
	@Override
	public String getSymbolName() {
		return "PositionChange";
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolPositionChange.class.getMethods();
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
