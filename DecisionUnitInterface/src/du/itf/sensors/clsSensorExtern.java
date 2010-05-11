package du.itf.sensors;

import java.util.ArrayList;

import du.enums.eSensorExtType;

/**
 * @author langr
 *
 * Base class for all external sensors like vision, mouth area, nose, bump, ... 
 *
 */
abstract public class clsSensorExtern extends clsDataBase implements Cloneable {
	protected eSensorExtType moSensorType;

	public abstract ArrayList<clsSensorExtern> getDataObjects(); 
	public abstract boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData); 

	public eSensorExtType getSensorType() {
		return moSensorType;
	}
	public void setSensorType(eSensorExtType poSensorType) {
		moSensorType = poSensorType;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSensorExtern oClone = (clsSensorExtern)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}
