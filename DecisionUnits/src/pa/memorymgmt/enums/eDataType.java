/**
 * eDataType.java: DecisionUnits - pa.memorymgmt.enums
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 */
package pa.memorymgmt.enums;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.06.2010, 20:02:42
 * 
 */
public enum eDataType {
	ACT,
	ASSOCIATIONTEMP,
	ASSCOCIATIONATTRIBUTE,
	ASSOCIATIONWP,
	ASSOCIATIONDM,
	DM,
	TI,
	TP,
	TPM,
	WP; 
	
	public static ArrayList<eDataType> returnInitValues(){
		eDataType [] oDataArray = {ACT,DM,TI,TP,TPM,WP}; 
		return new ArrayList<eDataType>(Arrays.asList(oDataArray));  
	}
}
