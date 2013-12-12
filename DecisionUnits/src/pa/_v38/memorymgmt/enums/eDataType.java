/**
 * eDataType.java: DecisionUnits - pa._v38.memorymgmt.enums
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 * 
 */
public enum eDataType {
	
	// comment AP, why is there a hex-number added to a define?????
	
	UNDEFINED				(0x000000), 
	ACT						(0x000001),
	AFFECT					(0x000000),
	ASSOCIATIONTEMP 		(0x000000),
	ASSOCIATIONATTRIBUTE	(0x000000),
	ASSOCIATIONWP			(0x000000),
	ASSOCIATIONDM			(0x000000),
	ASSOCIATIONPRI			(0x000000),
	ASSOCIATIONPRIDM		(0x000000),
	ASSOCIATIONSEC			(0x000000),
	ASSOCIATIONEMOTION		(0x000000),
	CONCEPT				    (0x000000),
	DOMAIN					(0x000000),
	DRIVEDEMAND				(0x000000),
	DM						(0x000010),
	TI						(0x000100),
	TP						(0x001000),
	TPM						(0x010000),
	WP						(0x100000),
	WPM						(0x100000),		//What should be done here
	PRIINSTANCEOBJECT       (0x000000), 	//This datatype is necessary in order to store actual experiences
	PRIINSTANCEACTION       (0x000000),     //This datatype is necessary in order to store actual experiences
	EMOTION					(0x000010);
	
	
	public int nBinaryValue; 
	
	eDataType(int pnBinaryValue){
		nBinaryValue = pnBinaryValue;
	}
}
