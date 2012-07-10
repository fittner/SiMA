/**
 * CHANGELOG
 *
 * Jul 10, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jul 10, 2012, 1:19:27 PM
 * 
 */
public class clsDriveMeshNew extends clsPrimaryDataStructure implements itfAssociatedDataStructure{

	/**
	 * DOCUMENT (schaat) - insert description 
	 *
	 * @since Jul 10, 2012 1:21:34 PM
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsDriveMeshNew(
			clsTriple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getMoAssociatedContent()
	 */
	@Override
	public ArrayList<clsAssociation> getMoAssociatedContent() {
		// TODO (schaat) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#setMoAssociatedContent(java.util.ArrayList)
	 */
	@Override
	public void setMoAssociatedContent(
			ArrayList<clsAssociation> moAssociatedContent) {
		// TODO (schaat) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getNumbAssociations()
	 */
	@Override
	public double getNumbAssociations() {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#addAssociations(java.util.ArrayList)
	 */
	@Override
	public void addAssociations(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		// TODO (schaat) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (schaat) - Auto-generated method stub
		
	}

}
