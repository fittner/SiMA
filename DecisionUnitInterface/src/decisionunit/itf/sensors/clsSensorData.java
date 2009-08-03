package decisionunit.itf.sensors;

import java.util.Collection;
import java.util.HashMap;

import enums.eSensorExtType;
import enums.eSensorIntType;



public class clsSensorData {
	private HashMap<eSensorExtType, clsDataBase> moSensorDataExt;
	private HashMap<eSensorIntType, clsDataBase> moSensorDataInt;
	

	public clsSensorData() {	
		moSensorDataExt = new HashMap<eSensorExtType, clsDataBase>();
		moSensorDataInt = new HashMap<eSensorIntType, clsDataBase>();
	}
	
	public void addSensorExt(eSensorExtType pnType, clsDataBase poData) {
		moSensorDataExt.put(pnType, poData);
	}
	
	public clsDataBase getSensorExt(eSensorExtType pnType) {
		return moSensorDataExt.get(pnType);
	}
	
	public void addSensorInt(eSensorIntType pnType, clsDataBase poData) {
		moSensorDataInt.put(pnType, poData);
	}
	
	public clsDataBase getSensorInt(eSensorIntType pnType) {
		return moSensorDataInt.get(pnType);
	}
	
	private String logXMLSensors(Collection<clsDataBase> poSensorData) {
		String logEntry = "";
		
		for (clsDataBase moSensor:poSensorData) {
			logEntry += moSensor.logXML();
		}
		
		return logEntry;
	}
	public String logXML() {
		String logEntry = "";
		
		logEntry += clsDataBase.addXMLTag("External", logXMLSensors(moSensorDataExt.values()));
		logEntry += clsDataBase.addXMLTag("Internal", logXMLSensors(moSensorDataInt.values()));
		
		return clsDataBase.addXMLTag("SensorData", logEntry);		
	}
	
	public String logHTML() {

		String logEntry = "";
		
		logEntry+="<p color='#FF0000'>External Data</p><table><tr><td width='100'>Type</td><td>Value</td></tr>";
		for( clsDataBase oDataBase : moSensorDataExt.values() ) {
			logEntry += oDataBase.logHTML();
		}
		logEntry+="</table><p color='#FF0000'>Internal Data</p><table><tr><td width='100'>Type</td><td>Value</td></tr>";
		for( clsDataBase oDataBase : moSensorDataInt.values() ) {
			logEntry += oDataBase.logHTML();
		}
		logEntry+="</table>";
		
		return logEntry;
	}	

	private String toStringSensors(Collection<clsDataBase> poSensorData) {
		String logEntry = "";
		
		for (clsDataBase moSensor:poSensorData) {
			logEntry += moSensor+" ## ";
		}
		
		if (logEntry.length()>0) {
			logEntry = logEntry.substring(0, logEntry.length() - 4);
		}
		
		return logEntry;
	}	
	@Override
	public String toString() {
		return "SensorData: External "+toStringSensors(moSensorDataExt.values())+" $$ Internal "+toStringSensors(moSensorDataInt.values());
	}


}
