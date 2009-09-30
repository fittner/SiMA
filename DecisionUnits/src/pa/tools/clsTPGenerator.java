/**
 * clsTPGenerator.java: DecisionUnits - pa.tools
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 */
package pa.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsThingPresentationMesh;
import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorExtType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 * 
 */
public class clsTPGenerator {

	public static ArrayList<clsThingPresentationMesh> convertSensorToTP(HashMap<eSensorExtType, clsDataBase> poSensorDataExt) {
		
		ArrayList<clsThingPresentationMesh> oResult = new ArrayList<clsThingPresentationMesh>();
		
		for( clsDataBase oSensorData : poSensorDataExt.values() ) {
			
			if( oSensorData )
			
			Field[] oFields = oSensorData.getClass().getDeclaredFields(); //get members of class
			for(Field oField : oFields) { //for each member

				
				
			}
		}
		
		return oResult;
	}
}
