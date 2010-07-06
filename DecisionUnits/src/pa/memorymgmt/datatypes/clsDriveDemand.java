/**
 * clsDriveDemand.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 * 
 */
public class clsDriveDemand extends clsDataStructurePA{
	public double mnDriveDemandIntensity; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:24:07
	 *
	 */
	public clsDriveDemand(double pnDriveDemandIntensity, String poAssociationID, eDataType peAssociationType) {
		super(poAssociationID, peAssociationType); 
		mnDriveDemandIntensity = pnDriveDemandIntensity; 
	}
}