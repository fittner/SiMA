/**
 * clsMemberCopy.java: DecisionUnits - symbolization.representationsysmbol
 * 
 * @author langr
 * 01.10.2009, 13:15:58
 */
package symbolization.representationsysmbol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 01.10.2009, 13:15:58
 * 
 */
public class clsMemberTransfer {

	public static void transferMembers( Object poSource, Object poTarget ) {
		
		Field[] oFields = poTarget.getClass().getFields(); //get members of class
		for(Field oField : oFields) { //for each (public) member of the sensordata-class

			String oAttributeName = oField.getName();
			Object oAttributeValue;
			try {
				oAttributeValue = poSource.getClass().getField(oAttributeName).get(poSource);
				oField.set(poTarget, oAttributeValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static HashMap<eSensorExtType, clsSensorExtern> createSymbolData( HashMap<eSensorExtType, clsSensorExtern> poSensorDataExt ) {
		
		HashMap<eSensorExtType, clsSensorExtern> oRetVal = new HashMap<eSensorExtType, clsSensorExtern>();
		
//		for( clsSensorExtern oSensorExt : poSensorDataExt.values() ) {
	
	    Iterator it = poSensorDataExt.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<eSensorExtType, clsSensorExtern> pairs = (Map.Entry<eSensorExtType, clsSensorExtern>)it.next();

	        clsSensorExtern oSensorExt = pairs.getValue();
	        if(oSensorExt != null) {
				if(oSensorExt.isContainer()) {
					
					String oClassName = "symbolization.representationsysmbol" + oSensorExt.getClass().getName().substring(24);
					clsSensorExtern oTarget = null;
					try {
						oTarget = (clsSensorExtern)Class.forName(oClassName).newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					ArrayList<clsSensorExtern> oListEntries = oSensorExt.getDataObjects();
					oTarget.setDataObjects( createSymbolData(oListEntries) );
				}
				else {
					String oClassName = "symbolization.representationsysmbol" + oSensorExt.getClass().getName().substring(24);
					try {
						
						clsSensorExtern oTarget = (clsSensorExtern)Class.forName(oClassName).newInstance();
						transferMembers(oSensorExt, oTarget);
						oRetVal.put(pairs.getKey(), oTarget);
						
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
	        }
		}
		
		return oRetVal;
	}

	public static ArrayList<clsSensorExtern> createSymbolData( ArrayList<clsSensorExtern> poSensorDataExt ) {
	
		ArrayList<clsSensorExtern> oRetVal = new ArrayList<clsSensorExtern>();
		
		for( clsSensorExtern oSensorData : poSensorDataExt ) {
			
			String oClassName = "symbolization.representationsysmbol" + oSensorData.getClass().getName().substring(24);
			try {
				
				clsSensorExtern oTarget = (clsSensorExtern)Class.forName(oClassName).newInstance();
				transferMembers(oSensorData, oTarget);
				oRetVal.add(oTarget);
			
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return oRetVal;

	}
}
