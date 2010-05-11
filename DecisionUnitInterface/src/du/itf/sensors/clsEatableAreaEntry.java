/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package du.itf.sensors;

import java.util.ArrayList;

import du.enums.eEntityType;
import du.enums.eTriState;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsEatableAreaEntry extends clsSensorRingSegmentEntry{
	protected eTriState mnIsAlive = eTriState.UNDEFINED;
	protected eTriState mnIsConsumeable = eTriState.UNDEFINED;
	protected String moClassName;
	
	public eTriState getIsAlive() {
		return mnIsAlive;
	}
	public void setIsAlive(eTriState pnIsAlive) {
		mnIsAlive = pnIsAlive;
	}
	
	public eTriState getIsConsumeable() {
		return mnIsConsumeable;
	}
	public void setIsConsumeable(eTriState pnIsConsumeable) {
		mnIsConsumeable = pnIsConsumeable;
	}
	
	@Override
	public String getClassName() {
		return moClassName;
	}
	public void setClassName(String poClassName) {
		moClassName = poClassName;
	}
	
	
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
	
	@Override
	public String logXML(int pnId) {
		String logEntry = "<Entry>";
		
		logEntry += clsDataBase.addXMLTag("Type", mnEntityType.name()); 
		logEntry += clsDataBase.addXMLTag("Alive", mnIsAlive.name()); 
		logEntry += clsDataBase.addXMLTag("Consumeable", mnIsConsumeable.name()); 
		
		logEntry += "</Entry>";

		return logEntry;		
	}
	
	@Override
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

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:47:24
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
}
