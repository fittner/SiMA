package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;



public class clsSymbolPositionChange extends du.itf.sensors.clsPositionChange  implements itfIsContainer, itfGetSymbolName, itfSymbolPositionChange, itfGetDataAccessMethods {
	public clsSymbolPositionChange(du.itf.sensors.clsPositionChange poSensor) {
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
	
	public String getSymbolType() {
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

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 21.10.2009, 21:35:26
	 * 
	 * @see pa.symbolization.representationsymbol.itfIsContainer#getSymbolMeshContent()
	 */
	@Override
	public Object getSymbolMeshContent() {
		return "PositionChange";
	}
}
