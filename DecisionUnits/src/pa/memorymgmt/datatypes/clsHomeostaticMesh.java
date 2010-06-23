/**
 * clsHomeostaticMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.enums.eHomeostaticSources;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 * 
 */
public class clsHomeostaticMesh extends clsHomeostaticRepresentation{
	clsDriveDemand moDriveDemand; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:21:21
	 *
	 * @param poHomeostaticSource
	 */
	public clsHomeostaticMesh(eHomeostaticSources poHomeostaticSource, double pnDriveDemandIntensity, 
							  String poAssociationID, eDataType peAssociationType) {
		super(poHomeostaticSource, poAssociationID, peAssociationType);
		//moDriveDemand = new clsDriveDemand(pnDriveDemandIntensity); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 20:02:38
	 * 
	 * @see pa.memorymgmt.datatypes.clsPrimaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void assignDataStructure(clsDataStructurePA poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

}
