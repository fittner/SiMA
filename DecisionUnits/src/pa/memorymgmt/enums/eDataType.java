/**
 * eDataType.java: DecisionUnits - pa.memorymgmt.enums
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 */
package pa.memorymgmt.enums;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 * 
 */
public enum eDataType {
	ACT						(0x000001),
	ASSOCIATIONTEMP 		(0x000000),
	ASSOCIATIONATTRIBUTE	(0x000000),
	ASSOCIATIONWP			(0x000000),
	ASSOCIATIONDM			(0x000000),
	DM						(0x000010),
	TI						(0x000100),
	TP						(0x001000),
	TPM						(0x010000),
	WP						(0x100000); 
	
	
	public int nBinaryValue; 
	
	public static eDataType[] initValues(){
		eDataType [] oRetVal = {ASSOCIATIONWP, ASSOCIATIONDM, ACT,DM,TI,TP,TPM,WP};
		return oRetVal;
	}
	
	eDataType(int pnBinaryValue){
		nBinaryValue = pnBinaryValue;
	}
}
