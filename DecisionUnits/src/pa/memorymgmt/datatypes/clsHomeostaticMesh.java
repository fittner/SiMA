/**
 * clsHomeostaticMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 * 
 */
public abstract class clsHomeostaticMesh extends clsHomeostaticRepresentation{
	clsDriveDemand moDriveDemand = null;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:21:21
	 *
	 * @param poHomeostaticSource
	 */
	public clsHomeostaticMesh(String poAssociationID, eDataType peAssociationType, 
			ArrayList<clsAssociation> poAssociatedDriveSource, double pnDriveDemandIntensity) {
		super(poAssociationID, peAssociationType);
		
		moDriveDemand = new clsDriveDemand(null, null, pnDriveDemandIntensity); 
		moContent = poAssociatedDriveSource;
		//FIXME HZ Is clsDrieDemand required? 
		//moDriveDemand = new clsDriveDemand(pnDriveDemandIntensity, null, null); 
	}
}
