package decisionunit.itf.sensors;

import enums.eEntityType;

public class clsEatableArea extends clsSensorExtern {
	public int mnNumEntitiesPresent = 0;
	public eEntityType mnTypeOfFirstEntity = eEntityType.UNDEFINED;
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("NumEntitiesPresent", new Integer(mnNumEntitiesPresent).toString()); 
		logEntry += addXMLTag("TypeOfFirstEntity", mnTypeOfFirstEntity.toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": entities present "+mnNumEntitiesPresent+" | type of first entity "+mnTypeOfFirstEntity.toString();
	}
}
