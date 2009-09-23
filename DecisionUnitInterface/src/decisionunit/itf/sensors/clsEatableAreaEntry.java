/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package decisionunit.itf.sensors;

import enums.eEntityType;
import enums.eTriState;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsEatableAreaEntry {
	public eEntityType mnEntityType = eEntityType.UNDEFINED;
	public eTriState mnIsAlive = eTriState.UNDEFINED;
	public eTriState mnIsConsumeable = eTriState.UNDEFINED;
	
	public clsEatableAreaEntry(eEntityType pnEntityType, eTriState pnIsAlive, eTriState pnIsConsumeable) {
		mnEntityType = pnEntityType;
		mnIsAlive = pnIsAlive;
		mnIsConsumeable = pnIsConsumeable;
		
		moClassName="EatableAreaEntry";
	}
	
	public clsEatableAreaEntry(eEntityType pnEntityType) {
		mnEntityType = pnEntityType;

		moClassName="EatableAreaEntry";
	}
	
	protected String moClassName;
	
	public String logXML(int pnId) {
		String logEntry = "<Entry>";
		
		logEntry += clsDataBase.addXMLTag("Type", mnEntityType.name()); 
		logEntry += clsDataBase.addXMLTag("Alive", mnIsAlive.name()); 
		logEntry += clsDataBase.addXMLTag("Consumeable", mnIsConsumeable.name()); 
		
		logEntry += "</Entry>";

		return logEntry;		
	}
	
	public String logHTML() {
		String oResult = "";

		oResult +=" type "+mnEntityType.name()+" | alive "+mnIsAlive.name()+" | consumeable "+mnIsConsumeable.name();
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": type "+mnEntityType.name()+" | alive "+mnIsAlive.name()+" | consumeable "+mnIsConsumeable.name();
		return oResult;
	}

}