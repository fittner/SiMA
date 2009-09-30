package decisionunit.itf.sensors;

import java.util.ArrayList;

import enums.eSensorExtType;

/**
 * @author langr
 *
 * Base class for all external sensors like vision, mouth area, nose, bump, ... 
 *
 */
abstract public class clsSensorExtern extends clsDataBase{

	public eSensorExtType moSensorType;
	
	public abstract boolean isContainer(); 
//	{
//		return false;
//	}

	public abstract ArrayList<clsSensorExtern> getDataObjects(); 
//	{
//		ArrayList<clsDataBase> oRetVal = new ArrayList<clsDataBase>();
//		oRetVal.add(this);
//		return oRetVal;
//	}

	public abstract String getMeshAttributeName() ;
//	{
//		
//	}
}
