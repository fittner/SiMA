package complexbody.io.sensors.datatypes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import complexbody.io.sensors.datatypes.enums.eSensorExtType;
import complexbody.io.sensors.datatypes.enums.eSensorIntType;





public class clsSensorData implements Cloneable {
	private HashMap<eSensorExtType, clsDataBase> moSensorDataExt;
	private HashMap<eSensorIntType, clsDataBase> moSensorDataInt;
	public static final String newline = System.getProperty("line.separator");

	public clsSensorData() {	
		moSensorDataExt = new HashMap<eSensorExtType, clsDataBase>();
		moSensorDataInt = new HashMap<eSensorIntType, clsDataBase>();
	}
	
	public void addSensorExt(eSensorExtType pnType, clsDataBase poData) {
		moSensorDataExt.put(pnType, poData);
	}
	
	public clsSensorExtern getSensorExt(eSensorExtType pnType) {
		return (clsSensorExtern)moSensorDataExt.get(pnType);
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
		
		logEntry+="<p color='#FF0000'>External Data</p><table><tr><th width='160'>Type</th><th align='left'>Value</th></tr>";
		for( clsDataBase oDataBase : moSensorDataExt.values() ) {
			logEntry += oDataBase.logHTML();
		}
		logEntry+="</table><p color='#FF0000'>Internal Data</p><table><tr><th width='160'>Type</th><th align='left'>Value</th></tr>";
		for( clsDataBase oDataBase : moSensorDataInt.values() ) {
			logEntry += oDataBase.logHTML();
		}
		logEntry+="</table>";
		
		return logEntry;
	}
	
	public String logText() {
		String logEntry = "";
		
		logEntry += "** External Data **"+newline;
		for( clsDataBase oDataBase : moSensorDataExt.values() ) {
			logEntry += " - "+oDataBase+newline;
		}
		logEntry += newline;
		
		logEntry += "** Internal Data **"+newline;
		for( clsDataBase oDataBase : moSensorDataInt.values() ) {
			logEntry += " - "+oDataBase+newline;
		}
		logEntry += newline;		
		
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

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSensorData oClone = (clsSensorData)super.clone();

        	//private HashMap<eSensorExtType, clsDataBase> moSensorDataExt;
        	if (moSensorDataExt != null) {
        		oClone.moSensorDataExt = new HashMap<eSensorExtType, clsDataBase>();
        		
        		for (Map.Entry<eSensorExtType, clsDataBase> entry:moSensorDataExt.entrySet()) {
        			oClone.moSensorDataExt.put(entry.getKey(), (clsDataBase)entry.getValue().clone());
        		}
        	}
        	
        	//private HashMap<eSensorIntType, clsDataBase> moSensorDataInt;
        	if (moSensorDataInt != null) {
        		oClone.moSensorDataInt = new HashMap<eSensorIntType, clsDataBase>();
        		
        		for (Map.Entry<eSensorIntType, clsDataBase> entry:moSensorDataInt.entrySet()) {
        			oClone.moSensorDataInt.put(entry.getKey(), (clsDataBase)entry.getValue().clone());
        		}
        	}        	
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
}
