/**
 * clsEatableEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 */
package complexbody.io.sensors.datatypes;

import java.lang.reflect.Field;
import java.util.ArrayList;

import complexbody.io.sensors.datatypes.enums.eEntityType;
import complexbody.io.sensors.datatypes.enums.eTriState;



/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:45
 * 
 */
public class clsEatableAreaEntry extends clsVisionEntry {
	protected eTriState mnIsAlive = eTriState.UNDEFINED;
	protected eTriState mnIsConsumeable = eTriState.UNDEFINED;
	protected String moClassName = "UNDEFINED";
	
	
	public clsEatableAreaEntry(clsVisionEntry poEntry) {
		super();
		
		//ATTENTION: getDeclaredFields ONLY returns the member of the given class - NOT THE SUPERCLASSES!!!!
		Field[] oFields1 = clsVisionEntry.class.getDeclaredFields();
		Field[] oFields2 = clsSensorRingSegmentEntry.class.getDeclaredFields();
		
		Field[] oFields = new Field[oFields1.length+oFields2.length];
		for(int i=0; i<oFields1.length; i++) { oFields[i]=oFields1[i]; }
		for(int i=0; i<oFields2.length; i++) { oFields[i+oFields1.length]=oFields2[i]; }
		
		for (Field oF:oFields) {
			Object value;
			try {
				value = oF.get(poEntry);
				oF.set(this, value);
			} catch (IllegalArgumentException e) {
				// TODO (deutsch) - Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO (deutsch) - Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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
