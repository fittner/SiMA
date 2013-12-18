/**
 * eDataType.java: DecisionUnits - pa._v38.memorymgmt.enums
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 */
package memorymgmt.enums;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 * 
 */
public enum eSupportDataType {
	
	UNDEFINED				(0x000000), 
	PRICONTAINER			(0x000000),
	SECCONTAINER			(0x000000),
	CONTAINERPAIR			(0x000000),
	PREDICTION				(0x000000),
	MOMENT					(0x000000),
	INTENTION				(0x000000),
	EXPECTATION				(0x000000);
	
	public int nBinaryValue; 
	
	eSupportDataType(int pnBinaryValue){
		nBinaryValue = pnBinaryValue;
	}
}