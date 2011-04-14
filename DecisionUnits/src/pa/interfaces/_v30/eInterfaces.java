/**
 * eInterfaces.java: DecisionUnits - pa.interfaces._v30
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 */
package pa.interfaces._v30;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 * 
 */
public enum eInterfaces {
	D1_1,
	D1_2,
	D1_3,
	D1_4,
	D2_1,
	D2_2,
	D2_3,
	D2_4,
	I0_1,	
	I0_2,	
	I0_3,	
	I0_4,	
	I0_5,	
	I0_6,
	I1_1,
	I1_2,
	I1_3,
	I1_4,
	I1_5,
	I1_6,
	I1_7,
	I1_8,
	I1_9,
	I1_10,
	I2_1,
	I2_2,
	I2_3,
	I2_4,
	I2_5,
	I2_6,
	I2_7,
	I2_8,
	I2_9,
	I2_10,
	I2_11,
	I2_12,
	I2_13,
	I2_14,
	I2_15,
	I2_16,
	I2_17,
	I2_18,
	I2_19,
	I2_20,
	I3_1,
	I3_2,
	I3_3,
	I4_1,
	I4_2,
	I4_3,
	I5_1,
	I5_2,
	I5_3,
	I5_4,
	I5_5,
	I6_1,
	I6_2,
	I6_3,
	I7_1,
	I7_2,
	I7_3,
	I7_4,
	I7_5,
	I7_6,
	I7_7,
	I8_1,
	I8_2;

	public static eInterfaces getEnum(String poName) throws java.lang.IllegalArgumentException {
		String[] temp = poName.split("_");
		poName = temp[0]+"_"+temp[1];
		return eInterfaces.valueOf(poName);
	}
	
	public static ArrayList<eInterfaces> getInterfaces(@SuppressWarnings("rawtypes") Class[] poInterfaces) {
		ArrayList<eInterfaces> oResult = new ArrayList<eInterfaces>();
		
		for (@SuppressWarnings("rawtypes") Class i:poInterfaces) {
			try {
				eInterfaces t = getEnum(i.getSimpleName());
				oResult.add(t);
			} catch (java.lang.IllegalArgumentException e) {
				// do nothing
			}
		}
		
		return oResult;
	}
}
