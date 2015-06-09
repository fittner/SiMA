/**
 * clsAssociationAttribute.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 */
package base.datatypes;

import java.util.HashMap;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 * 
 */
public class clsAssociationAttribute extends clsAssociation{
	/* (non-Javadoc)
     *
     * @since 08.06.2015 11:12:49
     * 
     * @see base.datatypes.clsAssociation#clone(java.lang.Object, java.lang.Object, java.util.ArrayList)
     */
    @Override
    public Object clone(Object poOriginalObject, Object poClonedObject, HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap)
            throws CloneNotSupportedException {
        
        return super.clone(poOriginalObject, poClonedObject, poClonedNodeMap);
    }

    /**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:53:46
	 *
	 */
	public clsAssociationAttribute(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			clsPrimaryDataStructure poAssociationElementA, 
			clsPrimaryDataStructure poAssociationElementB) {
		super(poDataStructureIdentifier,poAssociationElementA, poAssociationElementB);
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:01
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:10:35
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsPrimaryDataStructure getLeafElement() {
		//TPMs have one element that form the root for associated Attribute Associations
		//This element is always moAssociationElementA; Hence the B element is returned
		return (clsPrimaryDataStructure) moAssociationElementB;
	}
	
	@Override
	public clsPrimaryDataStructure getRootElement() {
		return (clsPrimaryDataStructure) moAssociationElementA; 
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		//The leaf element is always the TP
		moAssociationElementB = poDS;
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
		moAssociationElementA = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	
}
