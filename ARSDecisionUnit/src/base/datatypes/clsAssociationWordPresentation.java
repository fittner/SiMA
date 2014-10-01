/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package base.datatypes;

import java.util.List;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 * 
 */
public class clsAssociationWordPresentation extends clsAssociation{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:32:59
	 *
	 * @param clsDataStructurePA
	 * @param poWordPresentation
	 */
	public clsAssociationWordPresentation(
		clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			clsSecondaryDataStructure poAssociationElementA,
			clsDataStructurePA poAssociationElementB) {
			
			super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:24
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:07:20
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		return moAssociationElementA; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementB;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	/**
     * DOCUMENT - Return list of all external associations of type clsAssociationWordPresentation
     *
     * @author Kollmann
     * @since 15.07.2014 17:12:00
     *
     * @param poContainingDataStructure
     * @return List of all clsAssociationWordPresentation instances found in poContainingDataStructures extern associations
     */
    public static List<clsAssociationWordPresentation> getAllExternAssociationWordPresentation(itfExternalAssociatedDataStructure poContainingDataStructure) {
        return filterListByType(poContainingDataStructure.getExternalAssociatedContent(), clsAssociationWordPresentation.class);
    }
    
    /**
     * DOCUMENT - Return list of all internal associations of type clsAssociationWordPresentation
     *
     * @author Kollmann
     * @since 15.07.2014 17:12:00
     *
     * @param poContainingDataStructure
     * @return List of all clsAssociationWordPresentation instances found in poContainingDataStructures internal associations
     */
    public static List<clsAssociationWordPresentation> getAllInternalAssociationWordPresentation(itfInternalAssociatedDataStructure poContainingDataStructure) {
        return filterListByType(poContainingDataStructure.getInternalAssociatedContent(), clsAssociationWordPresentation.class);
    }
}
