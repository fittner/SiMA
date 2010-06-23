/**
 * clsDriveSource.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:12
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.enums.eHomeostaticSources;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:12
 * 
 */
public class clsDriveSource extends clsHomeostaticRepresentation{
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:58:02
	 *
	 * @param poAttributeAssociations
	 */
	public clsDriveSource(
			eHomeostaticSources poHomeoStaticSource, 
			ArrayList<clsAssociation> poAssociatedWordPresentations,
			ArrayList<clsAssociation> poAssociatedDataStructures, 
			String poDataStructureName,
			eDataType peDataStructureType) {
		
		super(poHomeoStaticSource, poDataStructureName, peDataStructureType);
		
		applyAssociations(eDataType.ASSOCIATIONWP, poAssociatedWordPresentations);
		applyAssociations(eDataType.ASSCOCIATIONATTRIBUTE, poAssociatedDataStructures); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 19:59:30
	 * 
	 * @see pa.memorymgmt.datatypes.clsPrimaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void assignDataStructure(clsDataStructurePA poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
}
